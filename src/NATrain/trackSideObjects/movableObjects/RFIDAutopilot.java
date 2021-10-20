package NATrain.trackSideObjects.movableObjects;

import NATrain.UI.workPlace.Blinker;
import NATrain.UI.workPlace.LocomotiveController;
import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.routes.*;
import NATrain.trackSideObjects.signals.GlobalSignalState;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalState;
import NATrain.trackSideObjects.signals.SignalType;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ConcurrentLinkedDeque;

import static NATrain.trackSideObjects.trackSections.TrackSectionState.OCCUPIED;

public class RFIDAutopilot implements Autopilot {

    private AutopilotMode mode;
    private Route route;
    private Locomotive locomotive;
    private LocomotiveController locomotiveController;

    private final PropertyChangeListener frontTagListener = new FrontTagListener();
    private final PropertyChangeListener nextSignalListener = new NextSignalListener();
    private Signal nextSignal;

    private boolean stoppedBeforeSignal = false;

    public static final int FULL_SPEED = 800; //max value is 1024
    public static final int RESTRICTED_SPEED = 550;
    public static final int SUPER_RESTRICTED_SPEED = 300; // impossible to start))

    public RFIDAutopilot(Locomotive locomotive, LocomotiveController locomotiveController) {
        this.locomotive = locomotive;
        this.locomotiveController = locomotiveController;
        this.locomotive.setAutopilot(this);
        locomotiveController.getLocationLabel().setText(locomotive.getFrontTagLocation().getId());
        locomotive.getFrontTag().addPropertyChangeSupport();
        locomotive.getFrontTag().addPropertyChangeListener(frontTagListener);
    }

    @Override
    public AutopilotMode getMode() {
        return mode;
    }

    @Override
    public void disable() {
        deactivateListeners();
    }

    public Signal getNextSignal() {
        return nextSignal;
    }


    public void deactivateListeners() {
        if (nextSignal != null) {
            nextSignal.removePropertyChangeListener(nextSignalListener); // remove listeners if autopilot deactivated accidentally
        }
        locomotive.getFrontTag().removePropertyChangeListener(frontTagListener);
        nextSignal = Signal.EMPTY_SIGNAL;
        locomotiveController.getPreview().refresh();
        Blinker.unregisterQuad(locomotiveController.getPreview());
    }


    protected class FrontTagListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            boolean signalChanged = false;
            TrackSection occupiedSection = (TrackSection) evt.getNewValue();
            boolean passed = occupiedSection == locomotive.getRearTagLocation();
            //SECTION PASSED LOGIC
            if (passed && nextSignal.getGlobalStatus() == GlobalSignalState.CLOSED) { //SECTION PASSED!!! GOOD TRICK ALWAYS WORKS))
                if (occupiedSection instanceof TrackBlockSection || occupiedSection == route.getDestinationTrackSection()) {
                    locomotive.stop();
                    locomotive.setActualState(LocomotiveState.NOT_MOVING);
                    stoppedBeforeSignal = true;
                }
            }

            //SECTION OCCUPIED LOGIC
            if (!passed) {
                if (route != null && occupiedSection == route.getOccupationalOrder().getFirst()) {
                    signalChanged = true;
                    nextSignal.removePropertyChangeListener(nextSignalListener);
                    switch (route.getRouteType()) {
                        case DEPARTURE:
                            Track track = route.getDestinationTrackLine();
                            if (route.getRouteDirection() == RouteDirection.EVEN) {
                                nextSignal = track.getFirstSignalInEvenDirection();
                            } else {
                                nextSignal = track.getFirstSignalInOddDirection();
                            }
                            break;
                        case ARRIVAL:
                            if (route.getRouteDirection() == RouteDirection.EVEN) {
                                nextSignal = route.getDestinationTrack().getEvenSignal();
                            } else {
                                nextSignal = route.getDestinationTrack().getOddSignal();
                            }
                            break;
                    }
                } else if (occupiedSection instanceof TrackBlockSection) {
                    signalChanged = true;
                    nextSignal.removePropertyChangeListener(nextSignalListener);
                    TrackBlockSection blockSection = (TrackBlockSection) occupiedSection;
                    Track track = blockSection.getTrack();
                    if (blockSection != track.getFirstSectionInActualDirection()) {
                        if (blockSection != track.getLastSectionInActualDirection()) {
                            TrackBlockSection nextBlockSection;
                            if (track.getTrackDirection() == TrackDirection.NORMAL) {
                                nextBlockSection = track.getBlockSections().get(track.getBlockSections().indexOf(occupiedSection) + 1); //TODO add index in block section class!!
                            } else {
                                nextBlockSection = track.getBlockSections().get(track.getBlockSections().indexOf(occupiedSection) - 1);
                            }
                            if (blockSection.getTrack().getTrackDirection() == TrackDirection.NORMAL) {
                                nextSignal = nextBlockSection.getNormalDirectionSignal();
                            } else {
                                nextSignal = nextBlockSection.getReversedDirectionSignal();
                            }
                        } else { //last in track!!!!
                            if (track.getTrackDirection() == TrackDirection.NORMAL) {
                                nextSignal = track.getNormalDirectionArrivalSignal();
                            } else {
                                nextSignal = track.getReversedDirectionArrivalSignal();
                            }
                        }
                    }
                }
            }

            if (route != null && !route.getOccupationalOrder().contains(occupiedSection)) { // don't change speed while route isn't completed
                locomotiveSpeedAutoselect(nextSignal.getSignalState());
            }

            if (signalChanged) {
                locomotiveController.getPreview().setAssociatedSignal(nextSignal);
                locomotiveController.getPreview().getDescriptionLabel().setText(nextSignal.getId());
                locomotiveController.getPreview().refresh();
                nextSignal.addPropertyChangeListener(nextSignalListener);
            }
        }
    }


    protected class NextSignalListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            SignalState signalState = (SignalState) evt.getNewValue();
            if (signalState.isBlinking()) {
                Blinker.registerQuad(locomotiveController.getPreview());
            } else {
                Blinker.unregisterQuad(locomotiveController.getPreview());
            }
            locomotiveController.getPreview().refresh();
            locomotiveSpeedAutoselect(signalState);
        }
    }

    private void locomotiveSpeedAutoselect(SignalState signalState) {
        switch (Signal.getGlobalStatusForState(signalState)) {
            case CLOSED:
                if (stoppedBeforeSignal) {
                    break;
                } else {
                    locomotive.setSpeed(SUPER_RESTRICTED_SPEED);
                    locomotive.run();
                }
            case OPENED_ON_RESTRICTED_SPEED:
                stoppedBeforeSignal = false;
                if (locomotive.getActualState() == LocomotiveState.NOT_MOVING) {
                    locomotive.setMovingDirection(MovingDirection.FORWARD);
                }
                locomotive.setSpeed(RESTRICTED_SPEED);
                locomotive.setActualState(LocomotiveState.MOVING_FORWARD);
                locomotive.run();
                break;
            case OPENED:
                if (locomotive.getActualState() == LocomotiveState.NOT_MOVING) {
                    locomotive.setMovingDirection(MovingDirection.FORWARD);
                }
                stoppedBeforeSignal = false;
                locomotive.setSpeed(FULL_SPEED);
                locomotive.setActualState(LocomotiveState.MOVING_FORWARD);
                locomotive.run();
                break;
        }
    }

    @Override
    public void executeRoute(Route route) {
        this.route = route;
        nextSignal = route.getSignal();
        switch (route.getSignal().getGlobalStatus()) {
            case OPENED_ON_RESTRICTED_SPEED:
                locomotive.setSpeed(RESTRICTED_SPEED);
                locomotive.run();
                break;
            case OPENED:
                locomotive.setSpeed(FULL_SPEED);
                locomotive.run();
                break;
            case CLOSED: // something wrong (for correct route signal always opened
                return;
        }
        locomotiveController.getPreview().setAssociatedSignal(nextSignal);
        locomotiveController.getPreview().getDescriptionLabel().setText(nextSignal.getId());
        if (nextSignal.getSignalState().isBlinking()) {
            Blinker.registerQuad(locomotiveController.getPreview());
        }
        locomotiveController.getPreview().refresh();
        nextSignal.addPropertyChangeListener(nextSignalListener);
    }
}

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

public class RFIDAutopilot implements Autopilot{

    private AutopilotMode mode;
    private Route route;
    private Locomotive locomotive;
    private LocomotiveController locomotiveController;

    private final PropertyChangeListener frontTagListener = new FrontTagListener();
    private final PropertyChangeListener nextSignalListener = new NextSignalListener();
    private Signal nextSignal;

    public static final int FULL_SPEED = 640; //max value is 1024
    public static final int RESTRICTED_SPEED = 400;

    public RFIDAutopilot(Locomotive locomotive, LocomotiveController locomotiveController) {
        this.locomotive = locomotive;
        this.locomotiveController = locomotiveController;
        this.locomotive.setAutopilot(this);
        locomotiveController.getLocationLabel().setText(locomotive.getFrontTagLocation().getId());
        locomotive.getFrontTag().addPropertyChangeSupport();
        locomotive.getFrontTag().addPropertyChangeListener(new FrontTagListener());
    }

    @Override
    public AutopilotMode getMode() {
        return mode;
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
            TrackSection occupiedSection = (TrackSection) evt.getNewValue();

            //SECTION PASSED LOGIC
            if (occupiedSection == locomotive.getRearTagLocation() && nextSignal.getGlobalStatus() == GlobalSignalState.CLOSED) { //SECTION PASSED!!! GOOD TRICK ALWAYS WORKS))
                if (occupiedSection instanceof TrackBlockSection || occupiedSection == route.getDestinationTrackSection()) {
                    locomotive.stop();
                }
            }

            //SECTION OCCUPIED LOGIC
            if (route.getOccupationalOrder().contains(occupiedSection)) {
                nextSignal.removePropertyChangeListener(nextSignalListener);
                switch (route.getRouteType()) {
                    case DEPARTURE:
                        Track track = route.getDestinationTrackLine();
                        if (locomotive.getForwardDirection() == RouteDirection.EVEN) {
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
            } else if (occupiedSection instanceof TrackBlockSection){
                    Track track = ((TrackBlockSection) occupiedSection).getTrack();
                    if ((nextSignal != track.getFirstSignalInEvenDirection() ||
                         nextSignal != track.getFirstSignalInOddDirection())) {
                    TrackBlockSection blockSection = (TrackBlockSection) occupiedSection;
                    if (blockSection.getTrack().getTrackDirection() == TrackDirection.NORMAL) {
                        nextSignal = blockSection.getNormalDirectionSignal();
                    } else {
                        nextSignal = blockSection.getReversedDirectionSignal();
                    }
                }
            }

            locomotiveController.getPreview().setAssociatedSignal(nextSignal);
            locomotiveController.getPreview().getDescriptionLabel().setText(nextSignal.getId());
            locomotiveController.getPreview().refresh();
            nextSignal.addPropertyChangeListener(nextSignalListener);
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
            switch (Signal.getGlobalStatusForState(signalState)) {
                case CLOSED:
                case OPENED_ON_RESTRICTED_SPEED:
                    locomotive.setSpeed(RESTRICTED_SPEED);
                    break;
                case OPENED:
                    locomotive.setSpeed(FULL_SPEED);
                    break;
            }
        }
    }

    @Override
    public void executeRoute(Route route) {
        nextSignal = route.getSignal();
        switch (route.getSignal().getGlobalStatus()) {
            case OPENED_ON_RESTRICTED_SPEED:
                locomotive.setSpeed(RESTRICTED_SPEED);
                break;
            case OPENED:
                locomotive.setSpeed(FULL_SPEED);
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

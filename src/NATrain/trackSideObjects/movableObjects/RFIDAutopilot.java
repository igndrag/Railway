package NATrain.trackSideObjects.movableObjects;

import NATrain.UI.workPlace.Blinker;
import NATrain.UI.workPlace.LocomotiveController;
import NATrain.UI.workPlace.executors.ActionExecutor;
import NATrain.UI.workPlace.executors.RouteExecutor;
import NATrain.UI.workPlace.executors.RouteStatus;
import NATrain.routes.*;
import NATrain.trackSideObjects.RFIDTag;
import NATrain.trackSideObjects.signals.GlobalSignalState;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalState;
import NATrain.trackSideObjects.trackSections.TrackSection;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class RFIDAutopilot implements Autopilot {

    private AutopilotMode mode;
    private Route route;
    private final Locomotive locomotive;
    private final LocomotiveController locomotiveController;

    private final PropertyChangeListener frontTagListener = new FrontTagListener();
    private final PropertyChangeListener nextSignalListener = new NextSignalListener();
    private Signal nextSignal;
    private AutopilotStatus status;



    public RFIDAutopilot(Locomotive locomotive, LocomotiveController locomotiveController) {
        this.locomotive = locomotive;
        this.locomotiveController = locomotiveController;
        this.locomotive.setAutopilot(this);
        locomotiveController.getLocationLabel().setText(locomotive.getFrontTagLocation().getId());
        locomotive.getFrontTag().addPropertyChangeSupport();
        this.status = AutopilotStatus.CREATED;
    }

    @Override
    public AutopilotMode getMode() {
        return mode;
    }

    @Override
    public void enable() {
        locomotiveController.getLocationLabel().setText(locomotive.getFrontTagLocation().getId());
        locomotive.getFrontTag().addPropertyChangeListener(frontTagListener);
        this.status = AutopilotStatus.WAITING;
        checkPreparedRoute();
    }

    @Override
    public void disable() {
        route = null;
        deactivateListeners();
        status = AutopilotStatus.DEACTIVATED;
    }

    @Override
    public void checkPreparedRoute() {
        if (locomotive.getFrontTag() != null && locomotive.getFrontTag().getTagLocation() != null) {
            ActionExecutor.getActiveRoutes().stream()
                    .filter(routeExecutor -> {
                        return routeExecutor.getRouteStatus() != RouteStatus.COMPLETED;
                    })
                    .map(RouteExecutor::getRoute)
                    .filter(activeRoute -> activeRoute.getDepartureTrackSection() == locomotive.getFrontTagLocation())
                    .findFirst().ifPresent(this::executeRoute);
        }
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
            boolean passed = occupiedSection == locomotive.getRearTagLocation() && nextSignal != route.getSignal(); //avoid pass checking in start section
            //SECTION PASSED LOGIC
            if (passed) { //SECTION PASSED!!! GOOD TRICK ALWAYS WORKS))
                if ((occupiedSection instanceof TracklineBlockSection &&
                        occupiedSection == route.getDestinationTrackLine().getLastSectionInActualDirection())
                        || occupiedSection == route.getDestinationTrackSection()) {
                    if (nextSignal.getGlobalStatus() == GlobalSignalState.CLOSED) {
                        locomotive.stop();
                        locomotive.setActualState(LocomotiveState.NOT_MOVING);
                        nextSignal.removePropertyChangeListener(nextSignalListener);
                        status = AutopilotStatus.WAITING;
                    }
                    checkPreparedRoute();
                }
            }

            //SECTION OCCUPIED LOGIC
            if (!passed) {
                if (route != null && occupiedSection == route.getOccupationalOrder().getFirst()) {
                    signalChanged = true;
                    nextSignal.removePropertyChangeListener(nextSignalListener);
                    switch (route.getRouteType()) {
                        case DEPARTURE:
                            Trackline trackline = route.getDestinationTrackLine();
                            if (route.getRouteDirection() == RouteDirection.EVEN) {
                                nextSignal = trackline.getFirstSignalInEvenDirection();
                            } else {
                                nextSignal = trackline.getFirstSignalInOddDirection();
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
                } else if (occupiedSection instanceof TracklineBlockSection) {
                    signalChanged = true;
                    status = AutopilotStatus.MOVING_ON_TRACKLINE;
                    nextSignal.removePropertyChangeListener(nextSignalListener);
                    TracklineBlockSection blockSection = (TracklineBlockSection) occupiedSection;
                    Trackline trackline = blockSection.getTrack();
                    if (blockSection != trackline.getFirstSectionInActualDirection()) {
                        if (blockSection != trackline.getLastSectionInActualDirection()) {
                            TracklineBlockSection nextBlockSection;
                            if (trackline.getTrackDirection() == TrackDirection.NORMAL) {
                                nextBlockSection = trackline.getBlockSections().get(trackline.getBlockSections().indexOf(occupiedSection) + 1); //TODO add index in block section class!!
                            } else {
                                nextBlockSection = trackline.getBlockSections().get(trackline.getBlockSections().indexOf(occupiedSection) - 1);
                            }
                            if (blockSection.getTrack().getTrackDirection() == TrackDirection.NORMAL) {
                                nextSignal = nextBlockSection.getNormalDirectionSignal();
                            } else {
                                nextSignal = nextBlockSection.getReversedDirectionSignal();
                            }
                        } else { //last in trackline!!!!
                            if (trackline.getTrackDirection() == TrackDirection.NORMAL) {
                                nextSignal = trackline.getNormalDirectionArrivalSignal();
                            } else {
                                nextSignal = trackline.getReversedDirectionArrivalSignal();
                            }
                        }
                    }
                }

                if ((status == AutopilotStatus.MOVING_ON_TRACKLINE) ||
                   (status == AutopilotStatus.ARRIVAL_ROUTE_EXECUTION && occupiedSection instanceof StationTrack)) {
                        // don't change speed while route isn't completed or loco has started a trackline moving
                        assert nextSignal != null;
                        locomotiveSpeedAutoselect(nextSignal.getSignalState());
                }

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
                if (status == AutopilotStatus.WAITING) {
                    break;
                } else if (nextSignal != route.getSignal()) {
                    locomotive.setSpeed(SUPER_RESTRICTED_SPEED);
                    System.out.printf("Signal: %s superrestricted!", nextSignal.getId());
                    locomotive.run();
                }
                break;
            case OPENED_ON_RESTRICTED_SPEED:
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
                locomotive.setSpeed(FULL_SPEED);
                locomotive.setActualState(LocomotiveState.MOVING_FORWARD);
                locomotive.run();
                break;
        }
    }

    @Override
    public void executeRoute(Route route) {
        this.route = route;
        if (nextSignal != null) {
            nextSignal.removePropertyChangeListener(nextSignalListener);
        }

        nextSignal = route.getSignal();

        switch (route.getRouteType()) {
            case ARRIVAL:
                status = AutopilotStatus.ARRIVAL_ROUTE_EXECUTION;
                break;
            case DEPARTURE:
                status = AutopilotStatus.DEPARTURE_ROUTE_EXECUTION;
                break;
        }

        locomotive.setMovingDirection(MovingDirection.FORWARD);

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

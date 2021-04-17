package NATrain.trackSideObjects.locomotives;

import NATrain.UI.UIUtils;
import NATrain.UI.workPlace.Blinker;
import NATrain.UI.workPlace.LocomotiveController;
import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.quads.BlockingTrackQuad;
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

public class Autopilot {

    private AutopilotMode mode;
    private Route route;
    private Locomotive locomotive;
    private LocomotiveController locomotiveController;
    //  private TrackBlockSection blockSection;
    private Track track;

    private TrackSection nextLocation;
    private final PropertyChangeListener nextLocationListener = new NextLocationListener();
    private final PropertyChangeListener nextSignalListener = new NextSignalListener();
    private final PropertyChangeListener nextSignalChooser = new NextSignalChooser();
    private final PropertyChangeListener nextBlockSectionListener = new NextBlockSectionListener();
    private TrackSection lastSectionInRoute;
    private Signal nextSignal;
    private ConcurrentLinkedDeque<TrackSection> movementPlan;
    private Timeline odometer;
    private double odometerValue = 0;

    public static final int FULL_SPEED = 1024;
    public static final int RESTRICTED_SPEED = 700;

    public Autopilot(Locomotive locomotive, LocomotiveController locomotiveController) {
        this.locomotive = locomotive;
        this.locomotiveController = locomotiveController;
        this.locomotive.setAutopilot(this);
        this.odometer = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            switch (locomotive.getSpeed()) {
                case FULL_SPEED:
                    odometerValue += 0.1 * locomotive.fullSpeed;
                    break;
                case RESTRICTED_SPEED :
                    odometerValue += 0.1 * locomotive.restrictedSpeed;
                    break;
                default:
                    WorkPlaceController.getActiveController().log("Wrong speed value fir odometer!");
            }
        }));
        odometer.setCycleCount(Animation.INDEFINITE);
        locomotiveController.getLocationLabel().setText(locomotive.getLocation().getId());
    }

    public Timeline getOdometer() {
        return odometer;
    }

    public Signal getNextSignal() {
        return nextSignal;
    }

    public void setBlockSection(TrackBlockSection blockSection) {
        //       this.blockSection = blockSection;
        route = null;
        this.track = blockSection.getTrack();
        int blockSectionIndex = track.getBlockSections().indexOf(blockSection);
        locomotive.setLocation(blockSection);
        if (blockSectionIndex == 0 && track.getTrackDirection() == TrackDirection.REVERSED) {
            nextSignal = track.getReversedDirectionArrivalSignal();
            locomotiveController.getPreview().setAssociatedSignal(nextSignal);
            locomotiveController.getPreview().getDescriptionLabel().setText(nextSignal.getId());
            locomotiveController.getPreview().refresh();
        } else if (blockSectionIndex == (track.getBlockSections().size() - 1) && track.getTrackDirection() == TrackDirection.NORMAL) {
            nextSignal = track.getNormalDirectionArrivalSignal();
            locomotiveController.getPreview().setAssociatedSignal(nextSignal);
            locomotiveController.getPreview().getDescriptionLabel().setText(nextSignal.getId());
            locomotiveController.getPreview().refresh();
        } else if (track.getTrackDirection() == TrackDirection.NORMAL) {
            nextLocation = track.getBlockSections().get(blockSectionIndex + 1);
            nextSignal = ((TrackBlockSection) nextLocation).getNormalDirectionSignal();
        } else {
            nextLocation = track.getBlockSections().get(blockSectionIndex - 1);
            nextSignal = ((TrackBlockSection) nextLocation).getReversedDirectionSignal();
        }
        locomotiveController.getPreview().setAssociatedSignal(nextSignal);
        locomotiveController.getPreview().getDescriptionLabel().setText(nextSignal.getId());
        locomotiveController.getPreview().refresh();
        nextSignal.addPropertyChangeListener(nextSignalListener);
        switch (nextSignal.getGlobalStatus()) {
            case OPENED:
                locomotive.setSpeed(FULL_SPEED);
                break;
            case OPENED_ON_RESTRICTED_SPEED:
                locomotive.setSpeed(RESTRICTED_SPEED);
                break;
            case CLOSED:
            case NOT_ACTIVE:
                //do nothing, just waiting next signal state change in NextSignalListener
        }
        if (nextLocation != null) { // if you allocate loco in last block section of trackline just waiting for set arrival route
            nextLocation.addPropertyChangeListener(nextBlockSectionListener);
        }
    }

    public void deactivate() {
        nextSignal.removePropertyChangeListener(nextSignalListener); // remove listeners if autopilot deactivated accidentally
        nextLocation.removePropertyChangeListener(nextLocationListener);
        nextLocation.removePropertyChangeListener(nextSignalChooser);
        nextSignal = Signal.EMPTY_SIGNAL;
        locomotiveController.getPreview().refresh();
        Blinker.unregisterQuad(locomotiveController.getPreview());
    }

    protected class NextSignalChooser implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getNewValue() == OCCUPIED) {
                route.getOccupationalOrder().getFirst().removePropertyChangeListener(this);//delete listener from first section in route
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
                }
                locomotiveController.getPreview().setAssociatedSignal(nextSignal);
                locomotiveController.getPreview().getDescriptionLabel().setText(nextSignal.getId());
                locomotiveController.getPreview().refresh();
                nextSignal.addPropertyChangeListener(nextSignalListener);
            }
        }
    }

    protected class NextLocationListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getNewValue() == OCCUPIED) {
                odometerValue = 0;
                nextLocation.removePropertyChangeListener(this);//delete listener from previous section
                locomotive.setLocation(nextLocation);
                locomotiveController.getLocationLabel().setText(locomotive.getLocation().getId());
                if (locomotive.getLocation() == lastSectionInRoute) {
                    nextLocation = route.getDestinationTrackSection();
                } else {
                    nextLocation = movementPlan.poll();
                }
                if (locomotive.getLocation() == route.getDestinationTrackSection()) {
                    switch (route.getRouteType()) {
                        case ARRIVAL:    //in arrival routes
                            if (!locomotiveController.checkRoutesInLocation()) { //check routes for next movement
                                stopTimerStart();
                            }//if don't find - start stop timer
                            break;
                        case DEPARTURE:
                            setBlockSection(route.getTVDS1());
                            break;
                    }
                } else {
                    nextLocation.addPropertyChangeListener(nextLocationListener);
                }
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
            switch (Signal.getGlobalStatusForState(signalState)) {
                case CLOSED: // stop timer will start when loco occupy the last section of route
                    if (locomotive.getLocation() instanceof TrackBlockSection) {
                        stopTimerStart();//and right now if locomotive is moving on trackline
                    }
                case OPENED_ON_RESTRICTED_SPEED:
                    locomotive.setSpeed(RESTRICTED_SPEED);
                    break;
                case OPENED:
                    locomotive.setSpeed(FULL_SPEED);
                    break;
            }
        }
    }

    protected class NextBlockSectionListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getNewValue() == OCCUPIED) {
                odometerValue = 0;
                nextSignal.removePropertyChangeListener(nextSignalListener);
                nextLocation.removePropertyChangeListener(nextBlockSectionListener);
                int blockSectionIndex = track.getBlockSections().indexOf(nextLocation);
                locomotive.setLocation(nextLocation);
                locomotiveController.getLocationLabel().setText(locomotive.getLocation().getId());

                if (blockSectionIndex == 0 && track.getTrackDirection() == TrackDirection.REVERSED) {
                    nextSignal = track.getReversedDirectionArrivalSignal();
                    locomotiveController.getPreview().setAssociatedSignal(nextSignal);
                    locomotiveController.getPreview().getDescriptionLabel().setText(nextSignal.getId());
                    locomotiveController.getPreview().refresh();
                    locomotiveController.checkRoutesInLocation();
                } else if (blockSectionIndex == (track.getBlockSections().size() - 1) && track.getTrackDirection() == TrackDirection.NORMAL) {
                    nextSignal = track.getNormalDirectionArrivalSignal();
                    locomotiveController.getPreview().setAssociatedSignal(nextSignal);
                    locomotiveController.getPreview().getDescriptionLabel().setText(nextSignal.getId());
                    locomotiveController.getPreview().refresh();
                    locomotiveController.checkRoutesInLocation();
                } else if (track.getTrackDirection() == TrackDirection.NORMAL) {
                    nextLocation = track.getBlockSections().get(blockSectionIndex + 1);
                    nextSignal = ((TrackBlockSection) nextLocation).getNormalDirectionSignal();
                } else {
                    nextLocation = track.getBlockSections().get(blockSectionIndex - 1);
                    nextSignal = ((TrackBlockSection) nextLocation).getReversedDirectionSignal();
                }
                if (nextSignal.getSignalType() != SignalType.STATION) {
                    nextLocation.addPropertyChangeListener(nextBlockSectionListener);
                }
                nextSignal.addPropertyChangeListener(nextSignalListener);
                locomotiveController.getPreview().setAssociatedSignal(nextSignal);
                locomotiveController.getPreview().getDescriptionLabel().setText(nextSignal.getId());
                if (nextSignal.getSignalState().isBlinking()) {
                    Blinker.registerQuad(locomotiveController.getPreview());
                } else {
                    Blinker.unregisterQuad(locomotiveController.getPreview());
                }
                locomotiveController.getPreview().refresh();
            }
        }
    }


    public void setRoute(Route route) {
        this.lastSectionInRoute = route.getDestinationTrackSection();
        this.movementPlan = new ConcurrentLinkedDeque<>(route.getOccupationalOrder()); //create local copy of occupational order
        this.nextLocation = movementPlan.poll();
        this.lastSectionInRoute = movementPlan.getLast();

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
        if (route.getRouteDirection() == locomotive.getForwardDirection()) { //move only forward
            this.route = route;
            this.mode = AutopilotMode.STATION;
            nextLocation.addPropertyChangeListener(nextLocationListener);
            nextLocation.addPropertyChangeListener(nextSignalChooser);
            switch (route.getRouteType()) {
                case DEPARTURE:
                    StationTrack departureTrack = (StationTrack) route.getDepartureTrackSection();
                    if (route.getRouteDirection() == RouteDirection.EVEN) {
                        nextSignal = departureTrack.getEvenSignal();
                    } else {
                        nextSignal = departureTrack.getOddSignal();
                    }
                    break;
                case ARRIVAL:
                    nextSignal = route.getSignal();
                    break;
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

    private void stopTimerStart() {
        double length = locomotive.getLocation().getLength() - odometerValue;
        double time = 0;
        switch (locomotive.getSpeed()) {
            case FULL_SPEED :
                time = length / locomotive.fullSpeed;
                break;
            case RESTRICTED_SPEED :
                time = length / locomotive.restrictedSpeed;
                break;
            }

        Timeline stopTimer = new Timeline(
                new KeyFrame(Duration.seconds(time),
                        event -> {
                            if (nextSignal.getGlobalStatus() == GlobalSignalState.CLOSED) { // check that signal still closed
                                locomotive.stop();
                            } else {
                                locomotiveController.checkRoutesInLocation(); //if signal opened, set route to autopilot
                            }
                            // nextSignal.removePropertyChangeListener(nextSignalListener);
                        }
                ));
        Timeline occupationalChecker = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if (nextSignal.getGlobalStatus() == GlobalSignalState.CLOSED) { //check, that next signal is really closed (increase delay for guarantied change of loco position and next signal in autopilot controller);
                if (locomotive.getLocation() instanceof TrackBlockSection || (route != null && locomotive.getLocation() == route.getDestinationTrackSection())) { //and run stop timer if loco somewhere in trackline or in destination track section
                    stopTimer.setCycleCount(1);
                    stopTimer.play();
                }
            }
        }));
        occupationalChecker.setCycleCount(1);
        occupationalChecker.play();
    }
}

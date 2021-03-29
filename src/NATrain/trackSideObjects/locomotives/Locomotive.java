package NATrain.trackSideObjects.locomotives;

import NATrain.routes.RouteDirection;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.сontrolModules.ControlModule;

public class Locomotive extends TracksideObject {

    private ControlModule controlModule;
    private LocomotiveState actualState = LocomotiveState.NOT_MOVING;
    int speed = 0;
    public boolean mainLight = false;
    public boolean rearLight = false;

    private RouteDirection forwardDirection = RouteDirection.EVEN;  //for autopilot usage
    private transient Signal nextSignal;                            //for autopilot usage
    private boolean autoPilotOn = false;                            //for autopilot usage

    public static final String INITIAL_LOCOMOTIVE_NAME = "New Locomotive";

    private TrackSection location = TrackSection.EMPTY_TRACK_SECTION;

    public Locomotive(String id) {
        super(id);
    }

    public LocomotiveState getActualState() {
        return actualState;
    }

    public void setActualState(LocomotiveState actualState) {
        this.actualState = actualState;
    }

    public TrackSection getLocation() {
        return location;
    }

    public void setLocation(TrackSection location) {
        this.location = location;
    }

    public void setModule(ControlModule module) {
        this.controlModule = module;
    }

    public ControlModule getModule() {
        return controlModule;
    }

    @Override
    public String getModules() {
        return getModule().getId();
    }
}

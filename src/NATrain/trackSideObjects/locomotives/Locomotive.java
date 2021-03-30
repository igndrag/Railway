package NATrain.trackSideObjects.locomotives;

import NATrain.routes.Autopilot;
import NATrain.routes.RouteDirection;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.сontrolModules.ControlModule;

public class Locomotive extends TracksideObject {

    private ControlModule controlModule;
    private LocomotiveState actualState = LocomotiveState.NOT_MOVING;
    private Autopilot autopilot;
    int speed = 0;
    public boolean mainLight = false;
    public boolean rearLight = false;

    private TrackSection location;
    private RouteDirection forwardDirection = RouteDirection.EVEN;
    private boolean autoPilotOn = false;

    public static final String INITIAL_LOCOMOTIVE_NAME = "New Locomotive";

    public void initAutopilot() {
        this.autopilot = new Autopilot(this);
    }

    public RouteDirection getForwardDirection() {
        return forwardDirection;
    }

    public void setForwardDirection(RouteDirection forwardDirection) {
        this.forwardDirection = forwardDirection;
    }

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

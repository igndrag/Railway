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
    private transient Autopilot autopilot;
    private int speed = 0;
    public boolean mainLight = false;
    public boolean rearLight = false;

    private TrackSection location;
    private RouteDirection forwardDirection = RouteDirection.EVEN;
    private boolean autoPilotOn = false;

    public static final String INITIAL_LOCOMOTIVE_NAME = "New Locomotive";

    public void createAutopilot() {
        this.autopilot = new Autopilot(this);
    }

    public RouteDirection getForwardDirection() {
        return forwardDirection;
    }

    public void setForwardDirection(RouteDirection forwardDirection) {
        this.forwardDirection = forwardDirection;
        propertyChangeSupport.firePropertyChange("Forward Direction", null, forwardDirection);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        //propertyChangeSupport.firePropertyChange("Speed", null, speed);
    }

    public Locomotive(String id) {
        super(id);
    }

    public LocomotiveState getActualState() {
        return actualState;
    }

    public void setActualState(LocomotiveState actualState) {
        this.actualState = actualState;
        propertyChangeSupport.firePropertyChange("Actual State", null, actualState);
    }

    public TrackSection getLocation() {
        return location;
    }

    public void setLocation(TrackSection location) {
        this.location = location;
       // propertyChangeSupport.firePropertyChange("Location", null, location);
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

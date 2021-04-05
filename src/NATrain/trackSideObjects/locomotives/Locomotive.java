package NATrain.trackSideObjects.locomotives;

import NATrain.routes.RouteDirection;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.сontrolModules.AbstractLocomotiveModule;
import NATrain.сontrolModules.ControlModule;
import NATrain.сontrolModules.MQTTLocomotiveModule;

import java.io.Serializable;

public class Locomotive extends TracksideObject implements Serializable {
    static final long serialVersionUID = 1L;

    private ControlModule controlModule;
    private LocomotiveState actualState = LocomotiveState.NOT_MOVING;
    private transient Autopilot autopilot;
    private int speed = 0;
    public boolean mainLight = false;
    public boolean rearLight = false;

    private MovingDirection movingDirection;
    private TrackSection location;
    private RouteDirection forwardDirection = RouteDirection.EVEN;

    public static final String INITIAL_LOCOMOTIVE_NAME = "New Locomotive";

    public Autopilot getAutopilot() {
        return autopilot;
    }

    public void setAutopilot(Autopilot autopilot) {
        this.autopilot = autopilot;
    }

    public RouteDirection getForwardDirection() {
        return forwardDirection;
    }

    public void setForwardDirection(RouteDirection forwardDirection) {
        this.forwardDirection = forwardDirection;
        //propertyChangeSupport.firePropertyChange("Forward Direction", null, forwardDirection);
    }

    public int getSpeed() {
        return speed;
    }


    public void setSpeed(int speed) {
        this.speed = speed;
        controlModule.sendCommand(MQTTLocomotiveModule.SET_SPEED_CHANNEL, String.format("%04d", speed));
    }

    public Locomotive(String id) {
        super(id);
    }

    public LocomotiveState getActualState() {
        return actualState;
    }

    public void setActualState(LocomotiveState actualState) {
        this.actualState = actualState;
        propertyChangeSupport.firePropertyChange("Actual State", null, actualState); //for listening from UI controller
    }

    public TrackSection getLocation() {
        return location;
    }

    public void setLocation(TrackSection location) {
        this.location = location;
       // propertyChangeSupport.firePropertyChange("Location", null, location);
    }

    public MovingDirection getMovingDirection() {
        return movingDirection;
    }

    public void setMovingDirection(MovingDirection movingDirection) {
        this.movingDirection = movingDirection;
        if (movingDirection == MovingDirection.FORWARD) {
            controlModule.sendCommand(AbstractLocomotiveModule.SET_MOVE_DIRECTION_CHANNEL, AbstractLocomotiveModule.FORWARD_COMMAND_CODE + "");
        } else {
            controlModule.sendCommand(AbstractLocomotiveModule.SET_MOVE_DIRECTION_CHANNEL, AbstractLocomotiveModule.BACKWARD_COMMAND_CODE + "");
        }
    }

    public void setModule(ControlModule module) {
        this.controlModule = module;
    }

    public ControlModule getModule() {
        return controlModule;
    }

    public void stop() {
        speed = 0;
        controlModule.sendCommand(MQTTLocomotiveModule.STOP_CHANNEL, "0" ); //command code doesn't matter on STOP channel
        setActualState(LocomotiveState.NOT_MOVING);
    }

    @Override
    public String getModules() {
        return getModule().getId();
    }
}

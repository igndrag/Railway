package NATrain.trackSideObjects.locomotives;

import NATrain.routes.RouteDirection;
import NATrain.trackSideObjects.AbstractMovableObject;
import NATrain.trackSideObjects.Movable;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.сontrolModules.AbstractLocomotiveModule;
import NATrain.сontrolModules.ControlModule;
import NATrain.сontrolModules.MQTTLocomotiveModule;

import java.io.Serializable;

import static NATrain.сontrolModules.AbstractLocomotiveModule.*;
import static javafx.animation.Animation.Status.PAUSED;

public class Locomotive extends AbstractMovableObject implements Serializable{
    static final long serialVersionUID = 1L;

    private ControlModule controlModule;
    private LocomotiveState actualState = LocomotiveState.NOT_MOVING;
    protected int restrictedSpeed = 0; // mm/sec
    protected int fullSpeed = 0; // mm/sec

    private transient Autopilot autopilot;
    private int speed = 0;
    public boolean frontLight = false;
    public boolean rearLight = false;

    private MovingDirection movingDirection;

    private RouteDirection forwardDirection = RouteDirection.EVEN;

    public static final String INITIAL_LOCOMOTIVE_NAME = "New Locomotive";

    public Autopilot getAutopilot() {
        return autopilot;
    }

    public ControlModule getControlModule() {
        return controlModule;
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
        if (autopilot != null && speed > 0 && autopilot.getOdometer().getStatus() == PAUSED) {
            autopilot.getOdometer().play();
        }
        if (actualState == LocomotiveState.MOVING_BACKWARD || actualState == LocomotiveState.MOVING_FORWARD) {
            controlModule.sendCommand(SET_SPEED_CHANNEL, String.format("%04d", speed) );
        }
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

    public void run() {
        if (speed > 0) {
            if (movingDirection == MovingDirection.FORWARD) {
                setActualState(LocomotiveState.MOVING_FORWARD);
            } else {
                setActualState(LocomotiveState.MOVING_BACKWARD);
            }
            controlModule.sendCommand(SET_SPEED_CHANNEL, String.format("%04d", speed) );
        }
    }

    public void stop() {
        speed = 0;
        controlModule.sendCommand(MQTTLocomotiveModule.STOP_CHANNEL, "0" ); //command code doesn't matter on STOP channel
        setActualState(LocomotiveState.NOT_MOVING);
        if (autopilot != null) {
            autopilot.getOdometer().pause();
        }
    }

    public void setFrontLightOn() {
        frontLight = true;
        controlModule.sendCommand(SPECIAL_COMMAND_CHANNEL, MAIN_LIGHT_ON_COMMAND_CODE + "");
    }

    public void setFrontLightOff() {
        frontLight = false;
        controlModule.sendCommand(SPECIAL_COMMAND_CHANNEL, MAIN_LIGHT_OFF_COMMAND_CODE + "");
    }

    public void setRearLightOn() {
        rearLight = true;
        controlModule.sendCommand(SPECIAL_COMMAND_CHANNEL, REAR_LIGHT_ON_COMMAND_CODE + "");
    }

    public void setRearLightOff() {
        rearLight = false;
        controlModule.sendCommand(SPECIAL_COMMAND_CHANNEL, REAR_LIGHT_OFF_COMMAND_CODE + "");
    }

    public int getRestrictedSpeed() {
        return restrictedSpeed;
    }

    public void setRestrictedSpeed(int restrictedSpeed) {
        this.restrictedSpeed = restrictedSpeed;
    }

    public int getFullSpeed() {
        return fullSpeed;
    }

    public void setFullSpeed(int fullSpeed) {
        this.fullSpeed = fullSpeed;
    }

    @Override
    public String getModules() {
        return getModule().getId();
    }
}

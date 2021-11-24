package NATrain.trackSideObjects.movableObjects;

import NATrain.routes.RouteDirection;
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

    public void setAutopilot(Autopilot autopilot) {
        this.autopilot = autopilot;
    }

    public ControlModule getControlModule() {
        return controlModule;
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
        if (autopilot != null && speed > 0 ){
                //&& railCircuitAutopilot.getOdometer().getStatus() == PAUSED) { //TODO use it if you use rail circuits
            //railCircuitAutopilot.getOdometer().play();
        }
       // if (actualState == LocomotiveState.MOVING_BACKWARD || actualState == LocomotiveState.MOVING_FORWARD) {
            controlModule.sendCommandToChannel(SET_SPEED_CHANNEL, String.format("%04d", speed) );
       // }
    }

    public Locomotive(String id) {
        super(id);
        movableObjectType = MovableObjectType.LOCOMOTIVE;
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
/*
        if (movingDirection == MovingDirection.FORWARD) {
            controlModule.sendCommandToChannel(AbstractLocomotiveModule.SET_MOVE_DIRECTION_CHANNEL, String.format("%04d", AbstractLocomotiveModule.FORWARD_COMMAND_CODE));
        } else {
            controlModule.sendCommandToChannel(AbstractLocomotiveModule.SET_MOVE_DIRECTION_CHANNEL, String.format("%04d", AbstractLocomotiveModule.BACKWARD_COMMAND_CODE ));
        }
*/
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
            controlModule.sendCommandToChannel(SET_SPEED_CHANNEL, String.format("%04d", speed) );

            if (movingDirection == MovingDirection.FORWARD) {
                controlModule.sendCommandToChannel(AbstractLocomotiveModule.SET_MOVE_DIRECTION_CHANNEL, String.format("%04d", AbstractLocomotiveModule.FORWARD_COMMAND_CODE));
            } else {
                controlModule.sendCommandToChannel(AbstractLocomotiveModule.SET_MOVE_DIRECTION_CHANNEL, String.format("%04d", AbstractLocomotiveModule.BACKWARD_COMMAND_CODE ));
            }
        }
    }

    public void stop() {
        speed = 0;
        controlModule.sendCommandToChannel(MQTTLocomotiveModule.STOP_CHANNEL, "0" ); //command code doesn't matter on STOP channel
        controlModule.sendCommandToChannel(SET_SPEED_CHANNEL, String.format("%04d", speed) );
        setActualState(LocomotiveState.NOT_MOVING);
        if (autopilot != null) {
            //railCircuitAutopilot.getOdometer().pause();
        }
    }

    public void setFrontLightOn() {
        frontLight = true;
        controlModule.sendCommandToChannel(SPECIAL_COMMAND_CHANNEL, String.format("%04d",MAIN_LIGHT_ON_COMMAND_CODE));
    }

    public void setFrontLightOff() {
        frontLight = false;
        controlModule.sendCommandToChannel(SPECIAL_COMMAND_CHANNEL, String.format("%04d",MAIN_LIGHT_OFF_COMMAND_CODE));
    }

    public void setRearLightOn() {
        rearLight = true;
        controlModule.sendCommandToChannel(SPECIAL_COMMAND_CHANNEL, String.format("%04d", REAR_LIGHT_ON_COMMAND_CODE));
    }

    public void setRearLightOff() {
        rearLight = false;
        controlModule.sendCommandToChannel(SPECIAL_COMMAND_CHANNEL, String.format("%04d", REAR_LIGHT_OFF_COMMAND_CODE));
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

    public boolean isMoving() {
        return (actualState == LocomotiveState.MOVING_FORWARD || actualState == LocomotiveState.MOVING_BACKWARD) && speed > 0;
    }

    @Override
    public String getModules() {
        return getModule().getId();
    }
}

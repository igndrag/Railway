package NATrain.сontrolModules;

import NATrain.trackSideObjects.locomotives.Locomotive;

import java.io.Serializable;

public abstract class AbstractLocomotiveModule extends AbstractModule implements Serializable {
    static final long serialVersionUID = 1L;

    protected final Locomotive locomotive;

    public static final int SET_SPEED_CHANNEL = 0;
    public static final int SET_MOVE_DIRECTION_CHANNEL = 1;
    public static final int STOP_CHANNEL = 2;
    public static final int SPECIAL_COMMAND_CHANNEL = 3;

    public static final  int FORWARD_COMMAND_CODE = 1;
    public static final  int BACKWARD_COMMAND_CODE = 2;

    // special commands codes

    public static final int HORN_COMMAND_CODE = 1;
    public static final int MAIN_LIGHT_ON_COMMAND_CODE = 2;
    public static final int MAIN_LIGHT_OFF_COMMAND_CODE = 3;
    public static final int REAR_LIGHT_ON_COMMAND_CODE = 4;
    public static final int REAR_LIGHT_OFF_COMMAND_CODE = 5;

    public AbstractLocomotiveModule(String id, Locomotive locomotive) {
        super(id);
        this.locomotive = locomotive;
    }
}

package NATrain.сontrolModules;

import NATrain.trackSideObjects.locomotives.Locomotive;

import java.util.HashMap;

public class MQTTLocomotiveModule extends AbstractMQTTModule {
    private Locomotive locomotive;

    public static final int SET_SPEED_CHANNEL = 0;
    public static final int SET_MOVE_DIRECTION_CHANNEL = 1;
    public static final int STOP_CHANNEL = 2;
    public static final int SPECIAL_COMMAND_CHANNEL = 3;

    // special commands codes

    public static final int HORN_COMMAND_CODE = 1;
    public static final int MAIN_LIGHT_ON_COMMAND_CODE = 2;
    public static final int MAIN_LIGHT_OFF_COMMAND_CODE = 3;
    public static final int REAR_LIGHT_ON_COMMAND_CODE = 4;
    public static final int REAR_LIGHT_OFF_COMMAND_CODE = 5;

    public MQTTLocomotiveModule(String id, Locomotive locomotive) {
        super(id);
        this.locomotive = locomotive;
        outputChannels = new HashMap<>();
        outputChannels.put(0, locomotive.getOutputChannel());
    }

    @Override
    public int getInputsCount() {
        return 0;
    }

    @Override
    public int getOutputsCount() {
        return 1;
    }

    @Override
    public String getObjectNames() {
        return locomotive.getId();
    }
}

package NATrain.сontrolModules;

import NATrain.connectionService.MQTTConnectionService;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.locomotives.Locomotive;

import java.util.ArrayList;
import java.util.HashMap;

public class MQTTLocomotiveModule extends AbstractLocomotiveModule {
    static final long serialVersionUID = 1L;

    public MQTTLocomotiveModule(String id, Locomotive locomotive) {
        super(id, locomotive);
        this.inputChannels = new HashMap<Integer, InputChannel>();
        inputChannels.put(0, new InputChannel(InputChannelType.LOCOMOTIVE, locomotive));
    }

    static final String commandTopicRoot = "NATrain/commands/locomotives";

    @Override
    public void sendCommand(int channelNumber, String command) {
        MQTTConnectionService.publish(commandTopicRoot + "/" + locomotive.getId(), String.format("%s:%s", channelNumber, command));
    }

    @Override
    public void globalRequest() {
        MQTTConnectionService.publish(commandTopicRoot + id, GLOBAL_REQUEST_COMMAND_CODE);
    }

    @Override
    public int getInputsCount() {
        return 1;
    }

    @Override
    public int getOutputsCount() {
        return 1;
    }

    @Override
    public String getObjectNames() {
        return locomotive.getId();
    }

    @Override
    public String getConfiguredChannels(TracksideObject tracksideObject) {
        return locomotive.getId();
    }

    public static String getCommandTopicRoot() {
        return commandTopicRoot;
    }
}

package NATrain.сontrolModules;

import NATrain.connectionService.MQTTConnectionService;

public abstract class AbstractMQTTModule extends AbstractModule{
    public AbstractMQTTModule(String id) {
        super(id);
    }
    protected static final String commandTopicRoot = "NATrain/commands/";

    @Override
    public void sendCommand(int channelNumber, String command) {
        MQTTConnectionService.publish(commandTopicRoot + id + "/ch" + channelNumber, command);
    }

    @Override
    public void globalRequest() {
        MQTTConnectionService.publish(commandTopicRoot + id, GLOBAL_REQUEST_COMMAND_CODE);
    }
}

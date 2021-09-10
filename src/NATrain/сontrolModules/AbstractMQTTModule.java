package NATrain.сontrolModules;

import NATrain.connectionService.MQTTConnectionService;

public abstract class AbstractMQTTModule extends AbstractModule{
    public AbstractMQTTModule(String id, int inputsCount, int outputsCount) {
        super(id, inputsCount, outputsCount);
    }

    protected static final String commandTopicRoot = "NATrain/controlModules/commands/";



    @Override
    public void sendCommandToChannel(int channelNumber, String command) {
        MQTTConnectionService.publish(commandTopicRoot + id, command);
    }

    @Override
    public void sendMultipleCommand(String command) {
        MQTTConnectionService.publish(commandTopicRoot + id, command);
    }

    @Override
    public void globalRequest() {
        MQTTConnectionService.publish(commandTopicRoot + id, GLOBAL_REQUEST_COMMAND_CODE);
    }

}

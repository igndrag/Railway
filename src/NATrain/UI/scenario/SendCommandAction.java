package NATrain.UI.scenario;

import NATrain.сontrolModules.OutputChannel;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SendCommandAction extends ScenarioAction{
    private Integer commandCode = 0;
    private Integer commandValue = 0;
    private OutputChannel outputChannel;
    private boolean random = false;
    private Random randomGenerator;

    public SendCommandAction(OutputChannel outputChannel, int commandCode, int commandValue) {
        super();
        this.outputChannel = outputChannel;
        this.commandCode = commandCode;
        this.commandValue = commandValue;
    }

    @Override
    void execute() {
        outputChannel.sendCommandWithValue(commandCode, commandValue);
    }
}

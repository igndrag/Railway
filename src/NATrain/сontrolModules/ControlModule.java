package NATrain.сontrolModules;

import java.util.List;

public interface ControlModule {

    public static final int LIGHT_OF_COMMAND_CODE = 10;
    public static final int LIGHT_ON_COMMAND_CODE = 20;
    public static final int BLINKING_ON_COMMAND_CODE = 20;

    String getId();
    List<InputChannel> getInputChannels();
    List<OutputChannel> getOutputChannels();
    void sendCommand (int channelNumber, String command);
    void globalRequest ();
}

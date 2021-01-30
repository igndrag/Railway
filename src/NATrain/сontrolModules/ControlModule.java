package NATrain.сontrolModules;

public interface ControlModule {

    public static final int LIGHT_OF_COMMAND_CODE = 10;
    public static final int LIGHT_ON_COMMAND_CODE = 20;
    public static final int BLINKING_ON_COMMAND_CODE = 20;

    String getId();
    InputChannel[] getInputChannels();
    OutputChannel[] getOutputChannels();
    void setChannelStatus (int ch, int statusCode);
    void sendChannelStatusResponse (int ch);
    void globalResponse ();
}

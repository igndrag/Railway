package NATrain.trackSideObjects.customObjects;


import NATrain.quads.custom.OnOffState;
import NATrain.сontrolModules.AbstractModule;
import NATrain.сontrolModules.OutputChannel;
import NATrain.сontrolModules.OutputChannelType;

public class OnOffObject extends AbstractCustomObject{
    public static final String INITIAL_ON_OFF_OBJECT_NAME = "New ON/OFF object";
    static final long serialVersionUID = 1L;

    private final OutputChannel outputChannel;
    private OnOffState state;

    public static final OnOffObject EMPTY_ON_OFF_OBJECT = new OnOffObject("None");

    public OnOffObject(String id) {
        super(id);
        this.state = OnOffState.OFF;
        this.type = CustomObjectType.ON_OFF_OBJECT;
        this.outputChannel = new OutputChannel(OutputChannelType.ON_OFF,this, null);
    }

    public OnOffState getState() {
        return state;
    }

    public void on() {
        this.state = OnOffState.ON;
        outputChannel.sendCommandCode(AbstractModule.ON_COMMAND_CODE);
    }

    public void off() {
        this.state = OnOffState.OFF;
        outputChannel.sendCommandCode(AbstractModule.OFF_COMMAND_CODE);
    }
}

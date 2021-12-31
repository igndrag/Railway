package NATrain.trackSideObjects.customObjects;

import NATrain.сontrolModules.OutputChannel;
import NATrain.сontrolModules.OutputChannelType;

public class ToggleButton extends AbstractCustomObject{

    private OutputChannel outputChannel = new OutputChannel(OutputChannelType.ON_OFF, this, null);

    public ToggleButton(String id) {
        super(id);
    }

    public OutputChannel getOutputChannel() {
        return outputChannel;
    }
}

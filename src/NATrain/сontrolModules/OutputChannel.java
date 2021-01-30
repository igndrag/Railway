package NATrain.сontrolModules;

import java.io.Serializable;

public class OutputChannel implements Serializable {
    static final long serialVersionUID = 1L;

    private int chNumber;
    private OutputChannelType channelType;
    private ControlModule module;

    public OutputChannel(int chNumber, ControlModule module) {
        this.chNumber = chNumber;
        this.module = module;
    }

    public int getChNumber() {
        return chNumber;
    }

    public OutputChannelType getChannelType() {
        return channelType;
    }
}

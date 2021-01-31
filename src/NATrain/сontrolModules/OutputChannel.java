package NATrain.сontrolModules;

import java.io.Serializable;

public class OutputChannel implements Serializable {
    static final long serialVersionUID = 1L;

    private int chNumber;
    private OutputChannelType channelType;
    private ControlModule module;
    private int actualStatusCode;

    public OutputChannel(OutputChannelType channelType) {
        this.channelType = channelType;
    }

    public int getChNumber() {
        return chNumber;
    }

    public void setChNumber(int chNumber) {
        this.chNumber = chNumber;
    }

    public ControlModule getModule() {
        return module;
    }

    public void setModule(ControlModule module) {
        this.module = module;
    }

    public OutputChannelType getChannelType() {
        return channelType;
    }

    public void sendCommand (int commandCode) {
        if (module != null) {
            module.sendCommand(chNumber, String.format("%02d:%d", chNumber, commandCode));
        }
    }
}
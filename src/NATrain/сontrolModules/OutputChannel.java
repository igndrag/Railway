package NATrain.сontrolModules;

import NATrain.trackSideObjects.TracksideObject;

import java.io.Serializable;

public class OutputChannel implements Serializable {
    static final long serialVersionUID = 1L;

    private int chNumber;
    private OutputChannelType channelType;
    private ControlModule module;
    private TracksideObject tracksideObject;
    private int actualStatusCode;

    public OutputChannel(OutputChannelType channelType, TracksideObject tracksideObject) {
        this.channelType = channelType;
        this.tracksideObject = tracksideObject;
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

    public TracksideObject getTracksideObject() {
        return tracksideObject;
    }

    public void sendCommand (int commandCode) {
        if (module != null) {
            module.sendCommand(chNumber, String.format("%02d:%d", chNumber, commandCode));
        }
    }
}
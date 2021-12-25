package NATrain.сontrolModules;

import NATrain.trackSideObjects.signals.SignalLampType;
import NATrain.trackSideObjects.TracksideObject;

import java.io.Serializable;

public class OutputChannel implements Serializable {
    static final long serialVersionUID = 1L;

    private int chNumber = -1;
    private OutputChannelType channelType;
    private ControlModule module;
    private TracksideObject tracksideObject;
    private SignalLampType lampType;
    private int lastCommandCode;

    public OutputChannel(OutputChannelType channelType, TracksideObject tracksideObject, SignalLampType lampType) {
        this.channelType = channelType;
        this.tracksideObject = tracksideObject;
        this.lampType = lampType;
    }

    public void setLastCommandCode(int lastCommandCode) {
        this.lastCommandCode = lastCommandCode;
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

    public SignalLampType getLampType() {
        return lampType;
    }

    public void sendCommandCode(int commandCode) {
        if (module != null) {
            // JSONObject command = new JSONObject();
           // command.put("chNumber", chNumber);
           // command.put("commandCode", commandCode);
            lastCommandCode = commandCode;
            module.sendCommandToChannel(chNumber, String.format("%02d:%02d:000_", chNumber, commandCode));
        }
    }

    public void sendCommandWithValue(int commandCode, int commandValue) {
        if (module != null) {
            // JSONObject command = new JSONObject();
            // command.put("chNumber", chNumber);
            // command.put("commandCode", commandCode);
            lastCommandCode = commandCode;
            module.sendCommandToChannel(chNumber, String.format("%02d:%02d:%03d_", chNumber, commandCode, commandValue));
        }
    }



    public void sendLastCommandAgain() {
        module.sendCommandToChannel(chNumber, String.format("%02d:%03d_", chNumber, lastCommandCode));
    }
}
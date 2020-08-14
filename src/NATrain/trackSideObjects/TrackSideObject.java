package NATrain.trackSideObjects;

import NATrain.quads.Quad;
import NATrain.remoteControlDevice.ControlModule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class TrackSideObject implements Serializable {

    ControlModule controlModule;
    Integer channel;
    private String id;

    public TrackSideObject(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ControlModule getControlModule() {
        return controlModule;
    }
    public void setControlModule(ControlModule controlModule) {
        this.controlModule = controlModule;
    }

    public Integer getChannel() {
        return channel;
    }
    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getControlModuleAddress() {
        if (controlModule != null)
            return controlModule.getAddress();
        else
            return null;
    }
}

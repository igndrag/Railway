package NATrain.trackSideObjects;

import NATrain.remoteControlModules.ControlModule;

import java.io.Serializable;

public abstract class TracksideObject implements Serializable {

    ControlModule controlModule;
    Integer channel;
    private String id;

    static final long SerialVersionUID = 1;

    public TracksideObject(String id) {
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
        if (controlModule == null)
            channel = null;
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
        return null;
    }
}

package NATrain.trackSideObjects;

import NATrain.remoteControlModules.ControlModule;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Arrays;

public abstract class TracksideObject implements Serializable {
    static final long serialVersionUID = 1L;

    protected transient PropertyChangeSupport propertyChangeSupport;
    ControlModule controlModule;
    Integer channel;
    private String id;

    public TracksideObject(String id) {
        this.id = id;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeSupport() {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
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

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }


    @Override
    public String toString() {
        return id;
    }
}


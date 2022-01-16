package NATrain.trackSideObjects;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Arrays;

public abstract class TracksideObject implements Serializable, Listenable {
    static final long serialVersionUID = 1L;

    protected transient PropertyChangeSupport propertyChangeSupport;
    protected String id;

    public TracksideObject(String id) {
        this.id = id;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeSupport() {
        if (this.propertyChangeSupport == null) {
            this.propertyChangeSupport = new PropertyChangeSupport(this);
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (propertyChangeSupport == null) {
            addPropertyChangeSupport();
        }
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (propertyChangeSupport != null) {
            propertyChangeSupport.removePropertyChangeListener(listener);
        }
    }

    @Override
    public void deactivateListeners() {
        PropertyChangeListener[] listeners = propertyChangeSupport.getPropertyChangeListeners();
        Arrays.stream(listeners).forEach(listener -> propertyChangeSupport.removePropertyChangeListener(listener));
    //TODO create special logic for all TSO and add it to MOSAIC mode initialize
    }


    @Override
    public String toString() {
        return id;
    }

}


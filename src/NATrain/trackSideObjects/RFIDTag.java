package NATrain.trackSideObjects;

import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.utils.UtilFunctions;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class RFIDTag implements Serializable, Listenable {
    static final long serialVersionUID = 1L;

    protected transient PropertyChangeSupport propertyChangeSupport;

    public void addPropertyChangeSupport() {
        if (this.propertyChangeSupport == null) {
            this.propertyChangeSupport = new PropertyChangeSupport(this);
        }
    }

    private String[] uid;
    private String id;
    private long decUid;
    private TrackSection tagLocation;
    private TagType tagType;

    public static RFIDTag EMPTY_TAG = new RFIDTag("ff", "ff", "ff", "ff", TagType.EMPTY_TAG);


    public RFIDTag(String b1, String b2, String b3, String b4, TagType tagType) {
        this.uid = new String[4];
        uid[0] = b1;
        uid[1] = b2;
        uid[2] = b3;
        uid[3] = b4;
        decUid = UtilFunctions.convertUidToLong(b1, b2, b3, b4);
        this.tagType = tagType;
        addPropertyChangeSupport();
    }

    public TrackSection getTagLocation() {
        return tagLocation;
    }

    public void setTagLocation(TrackSection tagLocation) {
        this.tagLocation = tagLocation;
        if (this.propertyChangeSupport != null) { //for RFID autopilot
            propertyChangeSupport.firePropertyChange("location", null, tagLocation);
        }
    }

    public long getDecUid() {
        return decUid;
    }

    public String[] getUid() {
        return uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TagType getTagType() {
        return tagType;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (propertyChangeSupport != null) {
            propertyChangeSupport.addPropertyChangeListener(listener);
        }
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", uid[0], uid[1], uid[2], uid[3]);
    }

    @Override
    public void deactivateListeners() {
        ArrayList<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>(Arrays.asList(propertyChangeSupport.getPropertyChangeListeners()));
        listeners.forEach(this::removePropertyChangeListener);
    }
}

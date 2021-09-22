package NATrain.trackSideObjects.trackSections;


import NATrain.trackSideObjects.RFIDTag;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.switches.Switch;

import java.util.ArrayList;
import java.util.HashSet;

public class TrackSection extends TracksideObject {

    static final long serialVersionUID = 1L;

    public static final TrackSection EMPTY_TRACK_SECTION = new TrackSection("none");

    static {
        EMPTY_TRACK_SECTION.setInterlocked(false);
        EMPTY_TRACK_SECTION.setVacancyState(TrackSectionState.FREE);
        EMPTY_TRACK_SECTION.occupationFixed = true;
        EMPTY_TRACK_SECTION.deallocationFixed = true;
    }

    public static final String INITIAL_TRACK_SECTION_NAME = "New Track Section";

    private final ArrayList<Switch> switches = new ArrayList<>();
    private final HashSet<RFIDTag> tags = new HashSet<>();
    private int length = 0;

    private transient TrackSectionState vacancyState = TrackSectionState.UNDEFINED;


    private transient boolean interlocked = false;
    private transient boolean occupationFixed = false;
    private transient boolean deallocationFixed = false;

    public ArrayList<Switch> getSwitches() {
        return switches;
    }

    public HashSet<RFIDTag> getTags() {
        return tags;
    }

    public boolean isOccupationFixed() {
        return occupationFixed;
    }

    public boolean isDeallocationFixed() {
        return deallocationFixed;
    }

    public void fixOccupation() {
        this.occupationFixed = true;
    }

    public void fixDeallocation() {
        this.deallocationFixed = true;
    }

    public TrackSection(String id) {
        super(id);
    }

    public boolean isInterlocked() {
        return interlocked;
    }

    public boolean notInterlocked() {
        return !interlocked;
    }

    public void setInterlocked(boolean interlocked) {
        this.interlocked = interlocked;
        propertyChangeSupport.firePropertyChange("interlockProperty", null, interlocked);
        if (!interlocked) {
            occupationFixed = false;
            deallocationFixed = false;
        }
        if (switches.size() > 0) {                                                          // if some switches located on track section
            switches.forEach(aSwitch -> aSwitch.setSwitchState(aSwitch.getSwitchState())); // set them to the same position for right view in control module
        }
    }

    public TrackSectionState getVacancyState() {
        return vacancyState;
    }

    public void setVacancyState(TrackSectionState vacancyState) {
        TrackSectionState oldState = this.vacancyState;
        this.vacancyState = vacancyState;
        propertyChangeSupport.firePropertyChange("occupationalProperty", oldState, vacancyState);
        if (switches.size() > 0) {                                                          // if some switches located on track section
            switches.forEach(aSwitch -> aSwitch.setSwitchState(aSwitch.getSwitchState())); // set them to the same position for right view in control module
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void updateVacancyState() {
        if (tags.size() == 0) {
            setVacancyState(TrackSectionState.FREE);
        } else {
            setVacancyState(TrackSectionState.OCCUPIED);
        }
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public String getModules() {
        return null;
    }
}

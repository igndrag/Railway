package NATrain.trackSideObjects.trackSections;


import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.сontrolModules.InputChannel;

import java.util.ArrayList;

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

    private InputChannel evenBorder;
    private final ArrayList<Switch> switches = new ArrayList<>();
    private final ArrayList<InputChannel> subsections = new ArrayList<>();
    private InputChannel oddBorder;
    private int length = 0;

    private transient TrackSectionState vacancyState = TrackSectionState.UNDEFINED;


    private transient boolean interlocked = false;
    private transient boolean occupationFixed = false;
    private transient boolean deallocationFixed = false;

    public ArrayList<Switch> getSwitches() {
        return switches;
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
        propertyChangeSupport.firePropertyChange("interlockProperty",null, interlocked);
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

    public InputChannel getEvenBorder() {
        return evenBorder;
    }

    public ArrayList<InputChannel> getSubsections() {
        return subsections;
    }

    public InputChannel getOddBorder() {
        return oddBorder;
    }

    public void setVacancyState(TrackSectionState vacancyState) {
        TrackSectionState oldState = this.vacancyState;
        this. vacancyState = vacancyState;
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

    public void setEvenBorder(InputChannel evenBorder) {
        this.evenBorder = evenBorder;
    }

    public void setOddBorder(InputChannel oddBorder) {
        this.oddBorder = oddBorder;
    }

    public void updateVacancyState() {
        for (InputChannel channel : subsections) {
            if (channel.getActualState() == TrackSectionState.OCCUPIED.getCode() || channel.getActualState() == TrackSectionState.UNDEFINED.getCode()){
                setVacancyState(TrackSectionState.OCCUPIED);
                return;
            }
        }
        setVacancyState(TrackSectionState.FREE);
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

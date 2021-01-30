package NATrain.trackSideObjects;


import NATrain.сontrolModules.InputChannel;
import NATrain.сontrolModules.InputChannelType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
    private boolean arrivalDepartureTrack = false;
    private final InputChannel inputChannel = new InputChannel(InputChannelType.TRACK_SECTION, this);

    private transient TrackSectionState vacancyState = TrackSectionState.UNDEFINED;

    private transient boolean interlocked = false;
    private transient boolean occupationFixed = false;
    private transient boolean deallocationFixed = false;

    public boolean isArrivalDepartureTrack() {
        return arrivalDepartureTrack;
    }

    public void setArrivalDepartureTrack(boolean arrivalDepartureTrack) {
        this.arrivalDepartureTrack = arrivalDepartureTrack;
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
    }

    public TrackSectionState getVacancyState() {
        return vacancyState;
    }

    public void setVacancyState(TrackSectionState vacancyState) {
        TrackSectionState oldState = this.vacancyState;
        this. vacancyState = vacancyState;
        propertyChangeSupport.firePropertyChange("occupationalProperty", oldState, vacancyState);
    }

    public InputChannel getInputChannel() {
        return inputChannel;
    }

    @Override
    public String toString() {
        return id;
    }
}

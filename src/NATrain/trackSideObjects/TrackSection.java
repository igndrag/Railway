package NATrain.trackSideObjects;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TrackSection extends TracksideObject {
    static final long serialVersionUID = 1L;

    public static final TrackSection EMPTY_TRACK_SECTION = new TrackSection("");
    public static final String INITIAL_TRACK_SECTION_NAME = "New Track Section";

    private transient TrackSectionState vacancyState = TrackSectionState.UNDEFINED;

    private boolean interlocked = false;
    private boolean occupationFixed = false;
    private boolean deallocationFixed = false;

    public void fixOccupation() {
        occupationFixed = true;
    }

    public void fixDeallocation() {
        deallocationFixed = true;
    }

    public TrackSection(String id) {
        super(id);
    }

    public boolean isInterlocked() {
        return interlocked;
    }

    public void setInterlocked(boolean interlocked) {
        this.interlocked = interlocked;
        propertyChangeSupport.firePropertyChange("interlockProperty",null, interlocked);
        if (interlocked = false) {
            occupationFixed = false;
            deallocationFixed = false;
        }
    }

    public TrackSectionState getVacancyState() {
        return vacancyState;
    }

    public void setVacancyState(TrackSectionState vacancyState) {
        //TrackSectionState oldState = this.vacancyState; //doesn't matter yet
        this. vacancyState = vacancyState;
        propertyChangeSupport.firePropertyChange("occupationalProperty",null, vacancyState);
    }

 }

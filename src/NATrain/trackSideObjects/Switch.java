package NATrain.trackSideObjects;

import java.io.Serializable;


public class Switch extends TracksideObject implements Serializable {
    static final long serialVersionUID = 1L;

    public static final Switch EMPTY_SWITCH = new Switch("");
    public static final String INITIAL_SWITCH_NAME = "New Switch";

    private SwitchState normalState = SwitchState.PLUS;
    transient private SwitchState switchState = SwitchState.UNDEFINED;
    private Switch paredSwitch;
    private boolean pared = false;
    private TrackSection trackSection = TrackSection.EMPTY_TRACK_SECTION;

    public Switch(String id) {
        super(id);
    }

    public void setParedSwitch(Switch anotherSwitch) {
        if (anotherSwitch == null) {
            pared = false;
            paredSwitch = null;
        } else {
            pared = true;
            this.paredSwitch = anotherSwitch;
        }
    }


    public void deleteParedSwitch(Switch paredSwitch) {
        pared = false;
        this.paredSwitch = null;
    }

    public Switch getParedSwitch() {
        return paredSwitch;
    }

    public SwitchState getNormalState() {
        return normalState;
    }

    public void setNormalState(SwitchState normalState) {
        this.normalState = normalState;
    }

    public SwitchState getSwitchState() {
        return switchState;
    }

    public void setSwitchState(SwitchState switchState) {
        this.switchState = switchState;
        propertyChangeSupport.firePropertyChange("switchStateProperty",null, switchState);
    }

    public void setTrackSection(TrackSection trackSection) {
        this.trackSection = trackSection;
    }

    public TrackSection getTrackSection() {
        return trackSection;
    }

    public boolean isPared() {
        return pared;
    }

    public void changePosition() {
        if (isChangePositionAvailable()) {
            if (switchState == SwitchState.PLUS)
                switchState = SwitchState.MINUS;
            else if (switchState == SwitchState.MINUS)
                switchState = SwitchState.PLUS;
        }
    }

    public boolean isChangePositionAvailable() {
        return trackSection.getVacancyState() == TrackSectionState.FREE && !trackSection.isInterlocked();
    }
}

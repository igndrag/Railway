package NATrain.trackSideObjects;

import NATrain.controller.SwitchState;


public class Switch extends TrackSideObject {

    private SwitchState normalState = SwitchState.PLUS;
    private SwitchState switchState = SwitchState.UNDEFINED;
    private TrackSection trackSection;
    private Switch paredSwitch;
    private boolean pared = false;
    private boolean interlocked = false;

    public Switch(String id, SwitchState normalState) {
        super(id);
        this.normalState = normalState;
    }

    public void setParedSwitch(Switch anotherSwitch) {
        pared = true;
        this.paredSwitch = anotherSwitch;
    }

    public void deleteParedSwitch(Switch paredSwitch) {
        pared = false;
        this.paredSwitch = null;
    }

    public TrackSection getTrackSection() {
        return trackSection;
    }

    public void setTrackSection(TrackSection trackSection) {
        this.trackSection = trackSection;
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
    }

    public boolean isPared() {
        return pared;
    }

    public void changePosition() {
        if (switchState == SwitchState.PLUS)
            switchState = SwitchState.MINUS;
        else if (switchState == SwitchState.MINUS)
            switchState = SwitchState.PLUS;
        if (pared) paredSwitch.changePosition();
    }

    public SwitchState getState() {
        return switchState;
    }

}

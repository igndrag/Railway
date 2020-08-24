package NATrain.trackSideObjects;

import NATrain.controller.SwitchState;


public class Switch extends TracksideObject {

    public static final Switch EMPTY_SWITCH = new Switch("");
    public static final String INITIAL_SWITCH_NAME = "New Switch";

    private SwitchState normalState = SwitchState.PLUS;
    transient private SwitchState switchState = SwitchState.UNDEFINED;
    private Switch paredSwitch;
    private boolean pared = false;
    private boolean interlocked = false;

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

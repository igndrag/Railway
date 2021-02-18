package NATrain.trackSideObjects.switches;

import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.trackSideObjects.trackSections.TrackSectionState;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.сontrolModules.InputChannel;
import NATrain.сontrolModules.InputChannelType;
import NATrain.сontrolModules.OutputChannel;
import NATrain.сontrolModules.OutputChannelType;

import java.io.Serializable;
public class Switch extends TracksideObject implements Serializable {
    static final long serialVersionUID = 1L;

    public static final int CONTROL_IMPULSE_COMMAND_CODE = 8;
    public static final Switch EMPTY_SWITCH = new Switch("");
    public static final String INITIAL_SWITCH_NAME = "New Switch";

    private SwitchState normalState = SwitchState.PLUS;
    transient private SwitchState switchState = SwitchState.UNDEFINED;
    private Switch paredSwitch;
    private boolean pared = false;
    private TrackSection trackSection = TrackSection.EMPTY_TRACK_SECTION;

    private final InputChannel plusInputChannel = new InputChannel(InputChannelType.SWITCH_PLUS, this);
    private final InputChannel minusInputChannel = new InputChannel(InputChannelType.SWITCH_MINUS, this);
    private final OutputChannel plusOutputChannel = new OutputChannel(OutputChannelType.SWITCH_TO_PLUS, this, null);
    private final OutputChannel minusOutputChannel = new OutputChannel(OutputChannelType.SWITCH_TO_MINUS, this, null);

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
            propertyChangeSupport.firePropertyChange("switchStateProperty", null, switchState);
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

    public InputChannel getPlusInputChannel() {
        return plusInputChannel;
    }

    public InputChannel getMinusInputChannel() {
        return minusInputChannel;
    }

    public OutputChannel getPlusOutputChannel() {
        return plusOutputChannel;
    }

    public OutputChannel getMinusOutputChannel() {
        return minusOutputChannel;
    }

    public void changePosition() {
        if (isChangePositionAvailable()) {
            if (switchState == SwitchState.PLUS)
                sendOutputCommand(SwitchState.MINUS);
            else if (switchState == SwitchState.MINUS)
                sendOutputCommand(SwitchState.PLUS);
        } else {
            WorkPlaceController.getActiveController().log("Position change isn't available!");
        }
    }

    public boolean isChangePositionAvailable() {
        boolean paredSwitchCheck = true;
        if (paredSwitch != null) {
            paredSwitchCheck = paredSwitch.getTrackSection().getVacancyState() != TrackSectionState.FREE
                    || paredSwitch.getTrackSection().isInterlocked();
        }
        return trackSection.getVacancyState() == TrackSectionState.FREE
                && !trackSection.isInterlocked() && paredSwitchCheck;
    }

    public void sendOutputCommand(SwitchState switchState) {
        switch (switchState) {
            case PLUS:
                plusOutputChannel.sendCommand(CONTROL_IMPULSE_COMMAND_CODE);
                break;
            case MINUS:
                minusOutputChannel.sendCommand(CONTROL_IMPULSE_COMMAND_CODE);
                break;
        }
    }

    @Override
    public String getModules() {
        return null;
    }
}

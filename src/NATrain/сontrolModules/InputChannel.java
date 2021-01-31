package NATrain.сontrolModules;
import NATrain.trackSideObjects.*;
import java.io.Serializable;

public class InputChannel implements Serializable {
    static final long serialVersionUID = 1L;

    public int chNumber;
    private final InputChannelType channelType;
    private final TracksideObject tracksideObject;
    private ControlModule module;
    private int actualState = 0;


    public void setActualState(int statusCode) {
        actualState = statusCode;
        switch (channelType) {
            case TRACK_SECTION:
                TrackSection trackSection = (TrackSection) tracksideObject;
                if (statusCode == TrackSectionState.FREE.getCode()) {
                    trackSection.setVacancyState(TrackSectionState.FREE);
                } else if (statusCode == TrackSectionState.OCCUPIED.getCode()) {
                    trackSection.setVacancyState(TrackSectionState.OCCUPIED);
                }
                break;
            case SWITCH_PLUS:
                Switch aSwitch = (Switch) tracksideObject;
                if (statusCode == 0) {
                    if (aSwitch.getMinusInputChannel().actualState == 0) {
                        aSwitch.setSwitchState(SwitchState.UNDEFINED);
                    }
                } else if (statusCode == 1 && aSwitch.getMinusInputChannel().actualState == 0) {
                        aSwitch.setSwitchState(SwitchState.PLUS);
                } else aSwitch.setSwitchState(SwitchState.UNDEFINED);
                break;
            case SWITCH_MINUS:
                aSwitch = (Switch) tracksideObject;
                if (statusCode == 0) {
                    if (aSwitch.getPlusInputChannel().actualState == 0) {
                        aSwitch.setSwitchState(SwitchState.UNDEFINED);
                    }
                } else if (statusCode == 1 && aSwitch.getPlusInputChannel().actualState == 0) {
                    aSwitch.setSwitchState(SwitchState.MINUS);
                } else {
                    aSwitch.setSwitchState(SwitchState.UNDEFINED);
                }
                break;
        }
    }

    public InputChannel(InputChannelType inputChannelType, TracksideObject tracksideObject) {
        this.channelType = inputChannelType;
        this.tracksideObject = tracksideObject;
    }

    public int getChNumber() {
        return chNumber;
    }

    public void setChNumber(int chNumber) {
        this.chNumber = chNumber;
    }

    public TracksideObject getTracksideObject() {
        return tracksideObject;
    }

    public InputChannelType getChannelType() {
        return channelType;
    }

    public ControlModule getModule() {
        return module;
    }

    public void setModule(ControlModule module) {
        this.module = module;
    }
}

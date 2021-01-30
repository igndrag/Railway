package NATrain.сontrolModules;

import NATrain.trackSideObjects.*;

import java.io.Serializable;

public class InputChannel implements Serializable {
    static final long serialVersionUID = 1L;

    private InputChannelType channelType;
    private TracksideObject tracksideObject;
    private ControlModule module;

    public void setActualState(int statusCode) {
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
                aSwitch.setSwitchState(SwitchState.PLUS);
                break;
            case SWITCH_MINUS:
                aSwitch = (Switch) tracksideObject;
                aSwitch.setSwitchState(SwitchState.MINUS);
                break;
        }
    }

    public InputChannel(InputChannelType inputChannelType, TracksideObject tracksideObject) {
        this.channelType = inputChannelType;
        this.tracksideObject = tracksideObject;
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

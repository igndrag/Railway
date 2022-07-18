package NATrain.сontrolModules;
import NATrain.UI.workPlace.LocatorController;
import NATrain.model.Model;
import NATrain.quads.custom.OnOffState;
import NATrain.trackSideObjects.*;
import NATrain.trackSideObjects.customObjects.OnOffObject;
import NATrain.trackSideObjects.movableObjects.Locomotive;
import NATrain.trackSideObjects.movableObjects.LocomotiveState;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.switches.SwitchState;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.trackSideObjects.trackSections.TrackSectionState;

import java.io.Serializable;

public class InputChannel implements Serializable {
    static final long serialVersionUID = 1L;

    private int chNumber = -1;
    private final InputChannelType channelType;
    private final TracksideObject tracksideObject;
    private ControlModule module;
    private int actualState = 0;

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

    public int getActualState() {
        return actualState;
    }

    public void moveTag(long decUid) {
        RFIDTag tag = Model.getTags().get(decUid);
        TrackSection trackSection = (TrackSection) tracksideObject;

        if (tag != null && !trackSection.getTags().contains(tag)) { //do nothing for already located tag
            trackSection.getTags().add(tag);
            trackSection.updateVacancyState();
            if (tag.getTagLocation() != null) {
                tag.getTagLocation().getTags().remove(tag);
                tag.getTagLocation().updateVacancyState();
            }
            LocatorController.refreshTable();
        }
        tag.setTagLocation(trackSection); //even if tag in section set tag location for handling of passing
    }


    public void setActualState(int statusCode) {
        actualState = statusCode;
        switch (channelType) {
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
            case LOCOMOTIVE:
                Locomotive locomotive = (Locomotive) tracksideObject;
                if (statusCode == LocomotiveState.MOVING_FORWARD.getResponseCode()) {
                    locomotive.setActualState(LocomotiveState.MOVING_FORWARD);
                } else if (statusCode == LocomotiveState.MOVING_BACKWARD.getResponseCode()) {
                    locomotive.setActualState(LocomotiveState.MOVING_BACKWARD);
                } else if (statusCode == LocomotiveState.NOT_MOVING.getResponseCode()) {
                    locomotive.setActualState(LocomotiveState.NOT_MOVING);
                }
                break;
            case TRACK_SECTION:
            case BLOCK_SECTION:
                TrackSection trackSection = (TrackSection) tracksideObject;
                if (statusCode == TrackSectionState.FREE.getCode()) {
                    trackSection.setVacancyState(TrackSectionState.FREE);
                } else if (statusCode == TrackSectionState.OCCUPIED.getCode()) {
                    trackSection.setVacancyState(TrackSectionState.OCCUPIED);
                }
                break;
            case ON_OFF_SELF_CHECK:
                OnOffObject onOffObject = (OnOffObject) tracksideObject;
                if (statusCode == 1) {
                    onOffObject.setState(OnOffState.ON);
                } else {
                    onOffObject.setState(OnOffState.OFF);
                }
        }
    }
}

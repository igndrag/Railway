package NATrain.trackSideObjects;

import NATrain.controller.TrackSectionState;
import NATrain.remoteControlDevice.TrackControlModule;


public class TrackSection extends TrackSideObject {

    TrackControlModule controlModule;

    Integer channel;

    public int getControlModuleAddress(){
        if (controlModule != null)
            return controlModule.getAddress();
        else
            return 0;
    }

    public static final TrackSection EMPTY_TRACK_SECTION = new TrackSection("");

    private TrackSectionState vacancyState = TrackSectionState.UNDEFINED;

    private boolean interlocked = false;

    public TrackSection(String id) {
        super(id);
    }

    public boolean isInterlocked() {
        return interlocked;
    }

    public void setInterlocked(boolean interlocked) {
        this.interlocked = interlocked;
    }

    public TrackSectionState getVacancyState() {
        return vacancyState;
    }

    public void setVacancyState(TrackSectionState vacancyState) {
        this. vacancyState = vacancyState;
    }

    public TrackControlModule getControlModule() {
        return controlModule;
    }

    public void setControlModule(TrackControlModule controlModule) {
        this.controlModule = controlModule;
    }

    public int getChannel() {
        if (channel != null)
            return channel;
        else return 0;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}

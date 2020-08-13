package NATrain.remoteControlDevice;

import NATrain.trackSideObjects.TrackSideObject;

public interface ControlModule {

    Integer getAddress();

    void setTrackSideObjectOnChannel (TrackSideObject trackSideObject, int channel);

    void refreshObjectState(int channel, int responseCode);

    void sendCommand(int channel, int commandCode);

}


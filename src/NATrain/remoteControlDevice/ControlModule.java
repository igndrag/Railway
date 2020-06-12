package NATrain.remoteControlDevice;

import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.TrackSideObject;

public interface ControlModule {

    void setTrackSideObjectOnChannel (TrackSideObject trackSideObject, int channel);

    int refreshObjectState (int channel, int responseCode);

    void sendCommand(int channel, int commandCode);

}


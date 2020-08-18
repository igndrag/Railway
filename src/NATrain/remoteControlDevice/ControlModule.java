package NATrain.remoteControlDevice;

import NATrain.trackSideObjects.TrackSideObject;

import java.util.List;

public interface ControlModule {

    Integer getAddress();

    void setTrackSideObjectOnChannel (TrackSideObject trackSideObject, int channel);

    void deleteTrackSideObjectFromChannel(Integer channel);

    void refreshObjectState(int channel, int responseCode);

    void sendCommand(int channel, int commandCode);

    boolean hasNotConfiguredChannels();

    public List<Integer> getNotConfiguredChannels();
}


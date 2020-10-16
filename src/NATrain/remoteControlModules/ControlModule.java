package NATrain.remoteControlModules;

import NATrain.trackSideObjects.TracksideObject;

import java.util.List;

public interface ControlModule {

    String getType();

    Integer getAddress();

    TracksideObject[] getChannels();

    void setTrackSideObjectOnChannel (TracksideObject trackSideObject, int channel);

    void deleteTrackSideObjectFromChannel(Integer channel);

    void refreshObjectState(int channel, int responseCode);

    void sendCommand(int channel, Command command);

    boolean hasNotConfiguredChannels();

    public List<Integer> getNotConfiguredChannels();
}


package NATrain.remoteControlModules;

import NATrain.trackSideObjects.TracksideObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModule implements ControlModule, Serializable {

    protected Integer address;
    protected TracksideObject[] channels;

    public AbstractModule(){};

    public TracksideObject[] getChannels() {
        return channels;
    }

    @Override
    public Integer getAddress() {
        return address;
    }

    @Override
    public void setTrackSideObjectOnChannel(TracksideObject trackSideObject, int channel) {
        try {
            channels[channel] = trackSideObject;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTrackSideObjectFromChannel(Integer channel) {
        if (channel != null)
        channels[channel] = null;
    }

    public AbstractModule(int address) {
        this.address = address;
    }

    public boolean hasNotConfiguredChannels() {
        for (TracksideObject channel : channels) {
            if (channel == null) return true;
        }
        return false;
    }

    public List<Integer> getNotConfiguredChannels() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < channels.length; i++) {
            if (channels[i] == null)
                list.add(i);
        }
        return list;
    }
}

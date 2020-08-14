package NATrain.remoteControlDevice;

import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.TrackSideObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModule implements ControlModule, Serializable {

    protected Integer address;
    protected TrackSideObject [] channels;

    public AbstractModule(){};

    public TrackSideObject[] getChannels() {
        return channels;
    }

    @Override
    public Integer getAddress() {
        return address;
    }

    @Override
    public void setTrackSideObjectOnChannel(TrackSideObject trackSideObject, int channel) {
        try {
            channels[channel] = trackSideObject;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public AbstractModule(int address) {
        this.address = address;
    }

    public boolean hasNotConfiguredChannels() {
        for (TrackSideObject channel : channels) {
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

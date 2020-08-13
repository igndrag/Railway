package NATrain.remoteControlDevice;

import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.TrackSideObject;

import java.io.Serializable;

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
    public void setTrackSideObjectOnChannel (TrackSideObject trackSideObject, int channel) {
        if (trackSideObject instanceof Signal) {
            try {
                channels[channel] = trackSideObject;
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    public AbstractModule(int address) {
        this.address = address;
    }
}

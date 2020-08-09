package NATrain.remoteControlDevice;

import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.TrackSideObject;

import java.util.HashMap;

public class SignalControlModule extends AbstractModule {

    private HashMap<Integer, Signal> channels = new HashMap<>();

    public HashMap<Integer, Signal> getChannels() {
        return channels;
    }

    public SignalControlModule(int address) {
        super(address);
    }

    @Override
    public void setTrackSideObjectOnChannel (TrackSideObject trackSideObject, int channel) {
        if (trackSideObject instanceof Signal) {
            if (0 >= channel || channel > 4) {
                System.out.println(channel + " is wrong value. Please type channel number from 1 to 4");
            } else {
                channels.put(channel, (Signal) trackSideObject);
            }
        } else {
            System.out.println("Impossible to add this object to signal control module");
        }
    }

    @Override
    public int refreshObjectState(int channel, int responseCode) {
        return 0;
    }

    @Override
    public void sendCommand(int channel, int commandCode) {

    }

}

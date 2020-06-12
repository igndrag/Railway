package NATrain.remoteControlDevice;

import NATrain.connectionService.RequestExecutor;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.TrackSection;
import NATrain.trackSideObjects.TrackSideObject;

import java.util.HashMap;

public class TrackControlModule implements ControlModule{

    private static final int TRACK_SECTION_FREE_STATUS_CODE = 1;
    private static final int TRACK_SECTION_OCCUPIED_STATUS_CODE = 2;
    private static final int TRACK_SECTION_UNDEFINED_STATUS_CODE = 3;

    private HashMap<Integer, TrackSection> channels = new HashMap<>();

    public HashMap<Integer,TrackSection> getChannels() {
        return channels;
    }

    @Override
    public void setTrackSideObjectOnChannel(TrackSideObject trackSideObject, int channel) {
        if (trackSideObject instanceof TrackSection) {
            if (0 >= channel || channel > 8) {
                System.out.println(channel + " is wrong value. Please type channel number from 1 to 8");
            } else {
                channels.put(channel, (TrackSection) trackSideObject);
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
        RequestExecutor.getRequestPool().add("test_command");
    }
}

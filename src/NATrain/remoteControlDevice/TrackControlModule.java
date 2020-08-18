package NATrain.remoteControlDevice;

import NATrain.connectionService.RequestExecutor;
import NATrain.trackSideObjects.TrackSection;

public class TrackControlModule extends AbstractModule implements ControlModule{

    private static final int TRACK_SECTION_FREE_STATUS_CODE = 1;
    private static final int TRACK_SECTION_OCCUPIED_STATUS_CODE = 2;
    private static final int TRACK_SECTION_UNDEFINED_STATUS_CODE = 3;

    public TrackControlModule(int address) {
        super(address);
        channels = new TrackSection[3];
    }

    @Override
    public Integer getAddress() {
        return address;
    }

    @Override
    public void refreshObjectState(int channel, int responseCode) {

    }

    @Override
    public void sendCommand(int channel, int commandCode) {
        RequestExecutor.getRequestPool().add("test_command");
    }
}

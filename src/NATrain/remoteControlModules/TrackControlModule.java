package NATrain.remoteControlModules;

import NATrain.connectionService.RequestExecutor;
import NATrain.trackSideObjects.TrackSection;
import NATrain.trackSideObjects.TrackSectionState;

public class TrackControlModule extends AbstractModule implements ControlModule {
    static final long serialVersionUID = 1L;

    private static final int FREE_RESPONSE_STATUS_CODE = 1;
    private static final int OCCUPIED_RESPONSE_STATUS_CODE = 2;

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
        TrackSection trackSection = (TrackSection)channels[channel];
        switch (responseCode) {
            case UNDEFINED_RESPONSE_STATUS_CODE:
                trackSection.setVacancyState(TrackSectionState.UNDEFINED);
                break;
            case FREE_RESPONSE_STATUS_CODE:
                trackSection.setVacancyState(TrackSectionState.FREE);
                break;
            case OCCUPIED_RESPONSE_STATUS_CODE:
                trackSection.setVacancyState(TrackSectionState.OCCUPIED);
                break;
        }
    }

    @Override
    public void sendCommand(int channel, Command command)
    {
        RequestExecutor.getRequestPool().add("test_command");
    }

    @Override
    public String getType() {
        return "Track Control Module";
    }
}

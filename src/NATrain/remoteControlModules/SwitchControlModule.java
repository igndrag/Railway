package NATrain.remoteControlModules;

import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.SwitchState;

public class SwitchControlModule extends AbstractModule {
    static final long serialVersionUID = 1L;

    private static final int PLUS_RESPONSE_STATUS_CODE = 2;
    private static final int MINUS_RESPONSE_STATUS_CODE = 3;

    public SwitchControlModule(int address) {
        super(address);
        channels = new Switch[4];
    }

    @Override
    public void refreshObjectState(int channel, int responseCode) {
        Switch aSwitch = (Switch)channels[channel];
        switch (responseCode) {
            case UNDEFINED_RESPONSE_STATUS_CODE:
                aSwitch.setSwitchState(SwitchState.UNDEFINED);
                break;
            case PLUS_RESPONSE_STATUS_CODE:
                aSwitch.setSwitchState(SwitchState.PLUS);
                break;
            case MINUS_RESPONSE_STATUS_CODE:
                aSwitch.setSwitchState(SwitchState.MINUS);
                break;
        }
    }

    @Override
    public void sendCommand(int channel, Command command) {

    }

    @Override
    public String getType() {
        return "Switch Control Module";
    }
}

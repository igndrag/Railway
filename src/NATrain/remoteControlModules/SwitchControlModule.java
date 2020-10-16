package NATrain.remoteControlModules;

import NATrain.trackSideObjects.Switch;

public class SwitchControlModule extends AbstractModule {
    static final long serialVersionUID = 1L;

    public static final Integer setToPlusCommandCode = 2;
    public static final Integer setToMinusCommandCode = 3;

    public static final Integer switchInPlusResponseCode = 2;
    public static final Integer switchInMinusResponseCode = 3;

    public SwitchControlModule(int address) {
        super(address);
        channels = new Switch[4];
    }

    @Override
    public void refreshObjectState(int channel, int responseCode) {

    }

    @Override
    public void sendCommand(int channel, Command command) {

    }

    @Override
    public String getType() {
        return "Switch Control Module";
    }
}

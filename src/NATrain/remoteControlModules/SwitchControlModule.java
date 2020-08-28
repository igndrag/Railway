package NATrain.remoteControlModules;

public class SwitchControlModule extends AbstractModule {

    public SwitchControlModule(int address) {
        super(address);
    }

    @Override
    public void refreshObjectState(int channel, int responseCode) {

    }

    @Override
    public void sendCommand(int channel, int commandCode) {

    }

    @Override
    public String getType() {
        return "Switch Control Module";
    }
}

package NATrain.remoteControlModules;
import NATrain.trackSideObjects.Signal;

public class SignalControlModule extends AbstractModule {
    static final long serialVersionUID = 1L;

    public SignalControlModule(int address) {
        super(address);
        channels = new Signal[4];
    }

    @Override
    public void refreshObjectState(int channel, int responseCode) {
        //from response pool
    }

    @Override
    public void sendCommand(int channel, Command command) {
        //to request pool
    }

    @Override
    public String getType() {
        return "Signal Control Module";
    }
}

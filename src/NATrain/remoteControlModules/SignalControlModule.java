package NATrain.remoteControlModules;
import NATrain.trackSideObjects.Signal;

public class SignalControlModule extends AbstractModule {

    public SignalControlModule(int address, String id) {
        super(address, id);
        channels = new Signal[4];
    }

    @Override
    public void refreshObjectState(int channel, int responseCode) {
        //from response pool
    }

    @Override
    public void sendCommand(int channel, int commandCode) {
        //to request pool
    }

    @Override
    public String getType() {
        return "Signal Control Module";
    }

    @Override
    public String getId() {
        return id;
    }
}

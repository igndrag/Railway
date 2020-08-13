package NATrain.remoteControlDevice;

import NATrain.trackSideObjects.Signal;

public class SignalControlModule extends AbstractModule {

    public SignalControlModule(int address) {
        super(address);
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

}

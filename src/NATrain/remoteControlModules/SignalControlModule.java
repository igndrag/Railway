package NATrain.remoteControlModules;
import NATrain.UI.NavigatorFxController;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.SignalState;

public class SignalControlModule extends AbstractModule {
    static final long serialVersionUID = 1L;

    private static final int NOT_LIGHT_RESPONSE_STATUS_CODE = 2;
    private static final int CLOSED_RESPONSE_STATUS_CODE = 3;
    private static final int OPENED_ON_WHITE_RESPONSE_STATUS_CODE = 4;
    private static final int OPENED_ON_GREEN_RESPONSE_STATUS_CODE = 5;
    private static final int OPENED_ON_YELLOW_RESPONSE_STATUS_CODE = 6;
    private static final int OPENED_ON_YELLOW_AND_BLINKED_YELLOW_RESPONSE_STATUS_CODE = 7;
    private static final int OPENED_ON_BLINKED_WHITE_RESPONSE_STATUS_CODE = 8;

    public SignalControlModule(int address) {
        super(address);
        channels = new Signal[4];


    }

    @Override
    public void refreshObjectState(int channel, int responseStatusCode) {
        Signal signal = (Signal)channels[channel];
        switch (responseStatusCode) {
            case UNDEFINED_RESPONSE_STATUS_CODE:
                signal.setSignalState(SignalState.UNDEFINED);
                break;
            case NOT_LIGHT_RESPONSE_STATUS_CODE:
                signal.setSignalState(SignalState.NOT_LIGHT);
                break;
            case CLOSED_RESPONSE_STATUS_CODE:
                signal.close();
                break;
            case OPENED_ON_WHITE_RESPONSE_STATUS_CODE:
                signal.setSignalState(SignalState.WHITE);
                break;
            case OPENED_ON_GREEN_RESPONSE_STATUS_CODE:
                signal.setSignalState(SignalState.GREEN);
                break;
            case OPENED_ON_YELLOW_RESPONSE_STATUS_CODE:
                signal.setSignalState(SignalState.YELLOW);
                break;
            case OPENED_ON_YELLOW_AND_BLINKED_YELLOW_RESPONSE_STATUS_CODE:
                signal.setSignalState(SignalState.YELLOW_AND_BLINKED_YELLOW);
                break;
            case OPENED_ON_BLINKED_WHITE_RESPONSE_STATUS_CODE:
                signal.setSignalState(SignalState.BLINKED_WHITE);
        }
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

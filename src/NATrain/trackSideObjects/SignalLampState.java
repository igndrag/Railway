package NATrain.trackSideObjects;

public enum SignalLampState {
    NOT_LIGHT (0),
    LIGHT (1),
    BLINKING (2);

    private int stateCode;

    private SignalLampState (int stateCode) {
        this.stateCode = stateCode;
    }

    public int getCode() {
        return stateCode;
    }

}

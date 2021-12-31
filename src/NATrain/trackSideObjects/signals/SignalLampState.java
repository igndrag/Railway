package NATrain.trackSideObjects.signals;

public enum SignalLampState {
    NOT_LIGHT (0),
    LIGHT (1),
    BLINKING (2),
    INVERTED_BLINKING (3);

    private int stateCode;

    private SignalLampState (int stateCode) {
        this.stateCode = stateCode;
    }

    public int getCode() {
        return stateCode;
    }

}

package NATrain.trackSideObjects.switches;

public enum SwitchState {
    PLUS (1),
    MINUS (2),
    UNDEFINED (0);

    private int stateCode;

    private SwitchState (int stateCode) {
        this.stateCode = stateCode;
    }

    public int getCode() {
        return stateCode;
    }
}

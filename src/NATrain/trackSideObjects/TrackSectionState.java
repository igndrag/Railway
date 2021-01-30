package NATrain.trackSideObjects;

public enum TrackSectionState {
    FREE (1),
    OCCUPIED (2),
    UNDEFINED (0);

    private int stateCode;

    private TrackSectionState (int stateCode) {
        this.stateCode = stateCode;
    }

    public int getCode() {
        return stateCode;
    }
}

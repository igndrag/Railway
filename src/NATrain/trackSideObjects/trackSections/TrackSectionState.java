package NATrain.trackSideObjects.trackSections;

public enum TrackSectionState {
    FREE (1),
    OCCUPIED (0),
    UNDEFINED (-1);

    private final int stateCode;

    private TrackSectionState (int stateCode) {
        this.stateCode = stateCode;
    }

    public int getCode() {
        return stateCode;
    }
}

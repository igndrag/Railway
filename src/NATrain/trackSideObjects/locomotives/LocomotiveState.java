package NATrain.trackSideObjects.locomotives;

public enum LocomotiveState {
    MOVING_FORWARD ("Moving Forward", 0),
    MOVING_BACKWARD ("Moving Backward", 1),
    NOT_MOVING ("Not Moving", 2),
    ;

    private final String description;

    private final int responseCode;

    private LocomotiveState (String description, int responseCode) {
        this.description = description;
        this.responseCode = responseCode;
    }

    public String getDescription() {
        return description;
    }

    public int getResponseCode() {
        return responseCode;
    }
}


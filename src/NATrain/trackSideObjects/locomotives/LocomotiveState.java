package NATrain.trackSideObjects.locomotives;

public enum LocomotiveState {
    MOVING_FORWARD ("Moving Forward"),
    MOVING_BACKWARD ("Moving Backward"),
    NOT_MOVING ("Not Moving"),
    AUTOPILOT ("AUTOPILOT"),
    ;

    private final String description;

    private LocomotiveState (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}


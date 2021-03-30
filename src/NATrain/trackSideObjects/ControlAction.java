package NATrain.trackSideObjects;

public enum ControlAction {
    ALLOCATE_LOCOMOTIVE("Allocate locomotive"),

    SET_ROUT_FROM("Set rout from"),
    SET_ROUT_TO ("Set rout to"),
    SET_ROUTE_TO_TRACK("Set route to track"),
    SET_ROUTE_TO_TRACK_LINE("Set route to track line"),

    SET_AUTO_MODE ("Set auto mode"),
    CHANGE_SWITCH_POSITION ("Change switch position"),
    CHANGE_TRACK_LINE_DIRECTION("Change track line direction"),
    OPEN_ON_BLINKED_WHITE("Open on white"),
    ;

    private final String description;

    ControlAction (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

package NATrain.trackSideObjects;

public enum ControlAction {
    SET_ROUT_FROM("Set rout from"),
    SET_ROUT_TO ("Set rout to"),
    SET_ROUT_BEHIND ("Set rout behind"),
    SET_ROUTE_TO_TRACK("Set route to track"),
    SET_AUTO_MODE ("Set auto mode"),
    CHANGE_SWITCH_POSITION ("Change switch position"),
    OPEN_ON_WHITE  ("Open on white"),
    ;

    private final String description;

    ControlAction (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

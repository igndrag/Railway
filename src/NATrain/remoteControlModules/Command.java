package NATrain.remoteControlModules;

public enum Command {
    SET_SWITCH_TO_PLUS (20),
    SET_SWITCH_TO_MINUS (21),
    CHANGE_SWITCH_POSITION (22),

    LIGHT_OFF (30),
    CLOSE (31),
    OPEN_ON_WHITE (32),
    OPEN_ON_GREEN (33),
    OPEN_ON_YELLOW (34),
    OPEN_ON_YELLOW_AND_BLINKED_YELLOW (35),
    OPEN_ON_BLINKED_WHITE (36);

    private final Integer commandCode;

    private Command (Integer commandCode) {
        this.commandCode = commandCode;
    }

    public Integer getCode() {
        return commandCode;
    }


}

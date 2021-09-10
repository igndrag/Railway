package NATrain.trackSideObjects.signals;

import java.util.*;

public enum SignalState {

    NOT_LIGHT (Collections.EMPTY_MAP),
    GREEN (new HashMap<>()),
    RED (new HashMap<>()),
    YELLOW (new HashMap<>()),
    BLINKED_YELLOW (new HashMap<>()),
    YELLOW_AND_YELLOW (new HashMap<>()),
    YELLOW_AND_BLINKED_YELLOW (new HashMap<>()),
    WHITE (new HashMap<>()),
    BLINKED_WHITE (new HashMap<>()),
    BLUE (new HashMap<>()),
    UNDEFINED (Collections.EMPTY_MAP);

    static {
        GREEN.getLampStates().put(SignalLampType.GREEN_LAMP, SignalLampState.LIGHT);
        RED.getLampStates().put(SignalLampType.RED_LAMP, SignalLampState.LIGHT);
        YELLOW.getLampStates().put(SignalLampType.YELLOW_LAMP, SignalLampState.LIGHT);
        BLINKED_YELLOW.getLampStates().put(SignalLampType.YELLOW_LAMP, SignalLampState.BLINKING);
        YELLOW_AND_YELLOW.getLampStates().put(SignalLampType.YELLOW_LAMP, SignalLampState.LIGHT);
        YELLOW_AND_YELLOW.getLampStates().put(SignalLampType.SECOND_YELLOW_LAMP, SignalLampState.LIGHT);
        YELLOW_AND_BLINKED_YELLOW.getLampStates().put(SignalLampType.YELLOW_LAMP, SignalLampState.BLINKING);
        YELLOW_AND_BLINKED_YELLOW.getLampStates().put(SignalLampType.SECOND_YELLOW_LAMP, SignalLampState.LIGHT);
        WHITE.getLampStates().put(SignalLampType.WHITE_LAMP, SignalLampState.LIGHT);
        BLINKED_WHITE.getLampStates().put(SignalLampType.WHITE_LAMP, SignalLampState.BLINKING);
        BLUE.getLampStates().put(SignalLampType.BLUE_LAMP, SignalLampState.LIGHT);
    }

    private final Map<SignalLampType, SignalLampState> lampStates;

    private SignalState (Map<SignalLampType, SignalLampState> lampStates) {
        this.lampStates = lampStates;
    }

    public Map<SignalLampType, SignalLampState> getLampStates() {
        return lampStates;
    }

    public static final Set<SignalState> STANDARD_STATION_SIGNAL_STATES = new HashSet<>(Arrays.asList(
            NOT_LIGHT,
            GREEN,
            RED,
            YELLOW,
            BLINKED_YELLOW,
            YELLOW_AND_YELLOW,
            YELLOW_AND_BLINKED_YELLOW,
            BLINKED_WHITE,
            UNDEFINED
    ));

    public static final Set<SignalState> STANDARD_TRACK_SIGNAL_STATES = new HashSet<>(Arrays.asList(
            RED,
            YELLOW,
            BLINKED_YELLOW,
            GREEN,
            NOT_LIGHT,
            UNDEFINED
    ));

    public static final Set<SignalState> STANDARD_TRIMMER_SIGNAL_STATES = new HashSet<>(Arrays.asList(
            NOT_LIGHT,
            RED,
            WHITE,
            BLUE,
            UNDEFINED
    ));

    public boolean isBlinking() {
        switch (this) {
            case BLINKED_WHITE:
            case YELLOW_AND_BLINKED_YELLOW:
            case BLINKED_YELLOW:
                return true;
            default: return false;
        }

    }

}

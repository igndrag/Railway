package NATrain.trackSideObjects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum SignalState {

    NOT_LIGHT,
    GREEN,
    RED,
    YELLOW,
    BLINKED_YELLOW,
    YELLOW_AND_YELLOW,
    YELLOW_AND_BLINKED_YELLOW,
    WHITE,
    BLINKED_WHITE,
    BLUE,
    UNDEFINED;

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

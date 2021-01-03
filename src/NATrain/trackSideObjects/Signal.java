package NATrain.trackSideObjects;

import NATrain.remoteControlModules.Command;

import java.util.Collections;
import java.util.Set;

import static NATrain.trackSideObjects.SignalState.*;

public class Signal extends TracksideObject {
    static final long serialVersionUID = 1L;

    public static final Signal EMPTY_SIGNAL = new Signal("None", Collections.emptySet(), SignalType.EMPTY_SIGNAL);
    public static final String INITIAL_SIGNAL_NAME = "New Signal";

    public Signal(String id, SignalType signalType) {
        super(id);
        setSignalType(signalType);
    }

    Set<SignalState> approvedSignals;
    transient SignalState signalState = SignalState.UNDEFINED;
    private SignalType signalType;
    private SignalState closedSignalState;

    public void setSignalType(SignalType signalType) {
        this.signalType = signalType;
        switch (signalType) {
            case STATION:
                approvedSignals = STANDARD_STATION_SIGNAL_STATES;
                closedSignalState = RED;
                break;
            case TRIMMER:
                approvedSignals = STANDARD_TRIMMER_SIGNAL_STATES;
                closedSignalState = BLUE;
                break;
            case TRACK:
                approvedSignals = STANDARD_TRACK_SIGNAL_STATES;
                closedSignalState = RED;
                break;
        }
    }

    public SignalType getSignalType() {
        return signalType;
    }

    public Set<SignalState> getApprovedSignals() {
        return approvedSignals;
    }

    public SignalState getSignalState() {
        return signalState;
    }

    public void setSignalState(SignalState signalState) {
        if (approvedSignals.contains(signalState)) {
            SignalState oldState = this.signalState;
            this.signalState = signalState;
            propertyChangeSupport.firePropertyChange("signalStateProperty", oldState, signalState);
        }
    }

    public void setClosedSignalState(SignalState closedSignalState) {
        if (closedSignalState == RED || closedSignalState == BLUE) {
            this.closedSignalState = closedSignalState;
        };
    }

    public Signal(String id, Set<SignalState> approvedSignals, SignalType signalType) {
        super(id);
        this.approvedSignals = approvedSignals;
        this.signalType = signalType;
    }

    public void close() {
        setSignalState(closedSignalState);
    }

    public void sendCommand(Command command) {
        assert controlModule != null;
        controlModule.sendCommand(channel, command);
    }

    public GlobalSignalState getGlobalStatus() {
        switch (signalState) {
            case RED:
            case BLUE:
                return GlobalSignalState.CLOSED;
            case GREEN:
            case WHITE:
                return GlobalSignalState.OPENED;
            case YELLOW:
            case BLINKED_WHITE:
            case BLINKED_YELLOW:
            case YELLOW_AND_YELLOW:
            case YELLOW_AND_BLINKED_YELLOW:
                return GlobalSignalState.OPENED_ON_RESTRICTED_SPEED;
            default:
                return GlobalSignalState.NOT_ACTIVE; //if not configured or not light
        }
    }
}

package NATrain.trackSideObjects;

import java.util.Collections;
import java.util.Set;

import static NATrain.trackSideObjects.SignalState.*;

public class Signal extends TracksideObject {
    static final long serialVersionUID = 1L;

    public static final Signal EMPTY_SIGNAL = new Signal("", Collections.emptySet(), SignalType.EMPTY_SIGNAL);
    public static final String INITIAL_SIGNAL_NAME = "New Signal";


    Set<SignalState> approvedSignals;
    transient SignalState signalState = SignalState.UNDEFINED;
    private SignalType signalType;
    private SignalState closedSignalState;

    public void setSignalType(SignalType signalType) {
        this.signalType = signalType;
        switch (signalType) {
            case TRACK:
                approvedSignals = STANDARD_TRACK_SIGNAL_STATES;
                closedSignalState = RED;
                break;
            case TRIMMER:
                approvedSignals = STANDARD_TRIMMER_SIGNAL_STATES;
                closedSignalState = BLUE;
                break;
        }
    }

    public SignalType getSignalType() {
        return signalType;
    }

    public Set<SignalState> getApprovedSignals() {
        return approvedSignals;
    }

    public Signal(String id, SignalType signalType) {
        super(id);
        this.signalType = signalType;
        if (signalType == SignalType.TRACK)
            this.approvedSignals = STANDARD_TRACK_SIGNAL_STATES;
        else
            this.approvedSignals = STANDARD_TRIMMER_SIGNAL_STATES;
    }

    public SignalState getSignalState() {
        return signalState;
    }

    public void setSignalState(SignalState signalState) {
        if (approvedSignals.contains(signalState)) {
            this.signalState = signalState;
            propertyChangeSupport.firePropertyChange("signalStateProperty", null, signalState);
        }
    }

    public Signal(String id, Set<SignalState> approvedSignals, SignalType signalType) {
        super(id);
        this.approvedSignals = approvedSignals;
        this.signalType = signalType;
    }

    public void close() {
        setSignalState(closedSignalState);
    }
}

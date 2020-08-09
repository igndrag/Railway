package NATrain.trackSideObjects;

import NATrain.controller.SignalState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Signal extends TrackSideObject{

    public static final Signal EMPTY_SIGNAL = new Signal("", Collections.emptySet(), SignalType.EMPTY_SIGNAL);


    Set<SignalState> approvedSignals;
    transient SignalState signalState = SignalState.UNDEFINED;
    SignalType signalType;

    public Signal(String id, HashSet<SignalState> approvedSignals) {
        super(id);
        this.approvedSignals = approvedSignals;
    }

    public SignalState getSignalState() {
        return signalState;
    }

    public void setSignalState(SignalState signalState) {
        this.signalState = signalState;
    }

    public Signal(String id, Set<SignalState> approvedSignals, SignalType signalType) {
        super(id);
        this.approvedSignals = approvedSignals;
        this.signalType = signalType;
    }
}

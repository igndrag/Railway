package NATrain.trackSideObjects;

import NATrain.controller.SignalState;

import java.util.ArrayList;
import java.util.HashSet;

public class Signal extends TrackSideObject{

    HashSet<SignalState> approvedSignals;
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

    public Signal(String id, HashSet<SignalState> approvedSignals, SignalType signalType) {
        super(id);
        this.approvedSignals = approvedSignals;
        this.signalType = signalType;
    }
}

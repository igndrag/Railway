package NATrain.trackSideObjects;

import NATrain.controller.SignalState;

import java.util.ArrayList;
import java.util.HashSet;

public class Signal extends TrackSideObject{

    HashSet<SignalState> approvedSignals;
    SignalState currentSignal = SignalState.UNDEFINED;
    SignalType signalType;

    public Signal(String id, HashSet<SignalState> approvedSignals) {
        super(id);
        this.approvedSignals = approvedSignals;
    }

    public Signal(String id, HashSet<SignalState> approvedSignals, SignalType signalType) {
        super(id);
        this.approvedSignals = approvedSignals;
        this.signalType = signalType;
    }
}

package NATrain.trackSideObjects;

import NATrain.controller.SignalState;

import java.util.ArrayList;

public class Signal extends TrackSideObject{
    ArrayList<SignalState> approvedSignals;
    SignalState currentSignal = SignalState.UNDEFINED;
}

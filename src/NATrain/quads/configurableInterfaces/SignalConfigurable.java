package NATrain.quads.configurableInterfaces;

import NATrain.trackSideObjects.Signal;

public interface SignalConfigurable {
    void setAssociatedSignal(Signal associatedSignal);
    Signal getAssociatedSignal();
}

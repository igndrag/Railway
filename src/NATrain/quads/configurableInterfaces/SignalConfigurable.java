package NATrain.quads.configurableInterfaces;

import NATrain.trackSideObjects.signals.Signal;

public interface SignalConfigurable {
    void setAssociatedSignal(Signal associatedSignal);
    Signal getAssociatedSignal();
}

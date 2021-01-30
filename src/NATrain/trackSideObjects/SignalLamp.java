package NATrain.trackSideObjects;

import NATrain.сontrolModules.OutputChannel;

public class SignalLamp {
    public SignalLampType signalLampType;
    public OutputChannel outputChannel;
    public SignalLampState actualStatus = SignalLampState.NOT_LIGHT;
}

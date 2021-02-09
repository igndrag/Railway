package NATrain.quads.configurableInterfaces;

import NATrain.trackSideObjects.switches.Switch;

public interface SwitchConfigurable {
    void setAssociatedSwitch(Switch associatedSwitch);
    Switch getAssociatedSwitch();
}

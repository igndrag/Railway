package NATrain.quads.configurableInterfaces;

import NATrain.trackSideObjects.Switch;

public interface SwitchConfigurable {
    void setAssociatedSwitch(Switch associatedSwitch);
    Switch getAssociatedSwitch();
}

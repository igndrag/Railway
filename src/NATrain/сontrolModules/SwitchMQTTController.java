package NATrain.сontrolModules;

import java.util.TreeMap;

public class SwitchMQTTController extends AbstractMQTTModule {
    static final long serialVersionUID = 1L;

    public SwitchMQTTController(String id) {
        super(id, 2, 2);
        this.moduleType = ControlModuleType.SWITCH_MQTT_CONTROLLER;
        this.inputChannels = new TreeMap<Integer, InputChannel>();
        this.outputChannels = new TreeMap<Integer, OutputChannel>();
    }

    @Override
    public String toString() {
        return "Switch MQTT Module: " + id;
    }
}

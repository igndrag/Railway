package NATrain.сontrolModules;

import java.util.TreeMap;

public class SignalMQTTController extends AbstractMQTTModule {
    static final long serialVersionUID = 1L;

    public SignalMQTTController(String id) {
        super(id, 2, 8);
        this.moduleType = ControlModuleType.SIGNAL_MQTT_CONTROLLER;
        this.inputChannels = new TreeMap<Integer, InputChannel>();
        this.outputChannels = new TreeMap<Integer, OutputChannel>();
    }

    @Override
    public String toString() {
        return "Universal MQTT Module";
    }
}

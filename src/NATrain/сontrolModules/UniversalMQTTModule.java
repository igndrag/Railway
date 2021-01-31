package NATrain.сontrolModules;

import java.util.ArrayList;

public class UniversalMQTTModule extends AbstractMQTTModule {

    public UniversalMQTTModule(String id) {
        super(id);
        this.moduleType = ControlModuleType.UNIVERSAL_MQTT_MODULE;
        this.inputChannels = new ArrayList<>(15);
        this.outputChannels = new ArrayList<>(15);
    }

    @Override
    public String toString() {
        return "Universal MQTT Module";
    }
}

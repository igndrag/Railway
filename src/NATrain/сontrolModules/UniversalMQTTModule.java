package NATrain.сontrolModules;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class UniversalMQTTModule extends AbstractMQTTModule {
    static final long serialVersionUID = 1L;

    public UniversalMQTTModule(String id) {
        super(id, 6, 14);
        this.moduleType = ControlModuleType.UNIVERSAL_MQTT_MODULE;
        this.inputChannels = new TreeMap<Integer, InputChannel>();
        this.outputChannels = new TreeMap<Integer, OutputChannel>();
    }

    @Override
    public String toString() {
        return "Universal MQTT Module";
    }
}

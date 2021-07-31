package NATrain.сontrolModules;

public enum ControlModuleType {
    SIGNAL_MQTT_CONTROLLER ("Signal MQTT Controller"),
    SWITCH_MQTT_CONTROLLER("Switch MQTT Controller"),
    REVERSE_LOOP_MQTT_CONTROLLER ("Reverse Loop MQTT Controller"),
    UNIVERSAL_MQTT_MODULE ("Universal Controller");

    private String string;

    private ControlModuleType (String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}

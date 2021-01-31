package NATrain.сontrolModules;

public enum ControlModuleType {
    UNIVERSAL_MQTT_MODULE ("Universal MQTT Module"),
    SWITCH_MQTT_MODULE ("Switch MQTT Module"),
    RS485_MODULE ("RS485 Module");

    private String string;

    private ControlModuleType (String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}

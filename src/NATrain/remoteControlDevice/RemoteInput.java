package NATrain.remoteControlDevice;

public class RemoteInput {
    ControlModule controlModule;
    int ch;

    public RemoteInput (ControlModule controlModule, int chNumber) {
        this.controlModule = controlModule;
        this.ch = chNumber;
    }
}

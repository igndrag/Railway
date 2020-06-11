package NATrain.remoteControlDevice;

public class SwitchControlModule implements ControlModule{

    SwitchControlChannel channel1 = new SwitchControlChannel();

    private static class SwitchControlChannel {
        RemoteInput switchStateInput;
        RemoteOutput switchControlOutput;

    }
}

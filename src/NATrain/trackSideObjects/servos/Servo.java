package NATrain.trackSideObjects.servos;

import NATrain.trackSideObjects.Controllable;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.сontrolModules.OutputChannel;
import NATrain.сontrolModules.OutputChannelType;

public class Servo extends TracksideObject implements Controllable {

    private final OutputChannel positionOneOutput = new OutputChannel(OutputChannelType.PWM, this, null);


    public Servo(String id) {
        super(id);
    }




    @Override
    public String getModules() {
        return null;
    }
}

package NATrain.trackSideObjects.customObjects;

import NATrain.trackSideObjects.Controllable;
import NATrain.сontrolModules.AbstractModule;
import NATrain.сontrolModules.OutputChannel;
import NATrain.сontrolModules.OutputChannelType;

public class Servo extends AbstractCustomObject implements Controllable {
    static final long serialVersionUID = 1L;

    private int positionOne = 0;
    private int positionTwo = 180;
    private int speed = 5;

    public static final Servo EMPTY_SERVO = new Servo("None");
    public static Servo TEST_SERVO = new Servo("TEST SERVO");
    public static Servo TEST_SERVO_2 = new Servo("TEST_SERVO_2");

    private final OutputChannel outputChannel = new OutputChannel(OutputChannelType.PWM, this, null);

    public Servo(String id) {
        super(id);
        this.type = CustomObjectType.SERVO;
    }

    public OutputChannel getOutputChannel() {
        return outputChannel;
    }

    public int getPositionOne() {
        return positionOne;
    }

    public void setPositionOne(int positionOne) {
        this.positionOne = positionOne;
    }

    public int getPositionTwo() {
        return positionTwo;
    }

    public void setPositionTwo(int positionTwo) {
        this.positionTwo = positionTwo;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void toFirstPosition() {
        outputChannel.sendCommandWithValue(AbstractModule.SET_SERVO_POSITION_COMMAND_CODE, positionOne);
    }

    public void toSecondPosition() {
        outputChannel.sendCommandWithValue(AbstractModule.SET_SERVO_POSITION_COMMAND_CODE, positionTwo);
    }

    @Override
    public String getModules() {
        if (outputChannel.getModule() != null) {
            return outputChannel.getModule().getId();
        } else {
            return "none";
        }
    }
}

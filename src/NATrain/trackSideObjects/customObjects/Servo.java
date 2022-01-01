package NATrain.trackSideObjects.customObjects;

import NATrain.trackSideObjects.Controllable;
import NATrain.сontrolModules.AbstractModule;
import NATrain.сontrolModules.OutputChannel;
import NATrain.сontrolModules.OutputChannelType;

public class Servo extends AbstractCustomObject implements Controllable {
    static final long serialVersionUID = 1L;

    private int positionOne = 0;
    private int positionTwo = 180;
    private int actualPosition = 0;
    private int speed = 5;

    public static final String INITIAL_SERVO_NAME = "New servo";

    public static final Servo EMPTY_SERVO = new Servo("None");
    public static Servo TEST_SERVO = new Servo("TEST SERVO");
    public static Servo TEST_SERVO_2 = new Servo("TEST_SERVO_2");

    static {
        TEST_SERVO.setPositionOne(90);
        TEST_SERVO.setPositionTwo(180);

        TEST_SERVO_2.setPositionOne(90);
        TEST_SERVO_2.setPositionTwo(0);
    }

    private final OutputChannel outputChannel = new OutputChannel(OutputChannelType.SERVO, this, null);

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

    public int getActualPosition() {
        return actualPosition;
    }

    public void setServoPosition(int position) {
        actualPosition = position;
        outputChannel.sendCommandWithValue(AbstractModule.SET_SERVO_POSITION_COMMAND_CODE, position);
    }

    public void toFirstPosition() {
        actualPosition = positionOne;
        outputChannel.sendCommandWithValue(AbstractModule.SET_SERVO_POSITION_COMMAND_CODE, positionOne);
    }

    public void toSecondPosition() {
        actualPosition = positionTwo;
        outputChannel.sendCommandWithValue(AbstractModule.SET_SERVO_POSITION_COMMAND_CODE, positionTwo);
    }

    public void init() {
        outputChannel.sendCommandWithValue(AbstractModule.SET_SERVO_ACTUAL_ANGLE, actualPosition);
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

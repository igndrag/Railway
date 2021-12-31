package NATrain.trackSideObjects.customObjects;

public class Gate extends AbstractCustomObject{
    static final long serialVersionUID = 1L;

    private GatePosition position = GatePosition.OPENED;
    private Servo firstServo = Servo.EMPTY_SERVO;
    private Servo secondServo = Servo.EMPTY_SERVO;

    public static Gate TEST_GATE = new Gate("TEST_GATE");
    public static final Gate EMPTY_GATE = new Gate("None");

    static {
        TEST_GATE.addPropertyChangeSupport();
        TEST_GATE.setFirstServo(Servo.TEST_SERVO);
        TEST_GATE.setSecondServo(Servo.TEST_SERVO_2);
    }

    public Gate(String id) {
        super(id);
        this.type = CustomObjectType.GATES;
    }

    public Servo getFirstServo() {
        return firstServo;
    }

    public void setFirstServo(Servo firstServo) {
        this.firstServo = firstServo;
    }

    public Servo getSecondServo() {
        return secondServo;
    }

    public void setSecondServo(Servo secondServo) {
        this.secondServo = secondServo;
    }

    public GatePosition getPosition() {
        return position;
    }

    public void setPosition(GatePosition position) {
        this.position = position;
    }

    public void openGate() {
        firstServo.toSecondPosition();
        secondServo.toSecondPosition();
        position = GatePosition.OPENED;
        propertyChangeSupport.firePropertyChange("position",GatePosition.CLOSED, GatePosition.OPENED);
    }

    public void closeGate() {
        firstServo.toFirstPosition();
        secondServo.toFirstPosition();
        position = GatePosition.CLOSED;
        propertyChangeSupport.firePropertyChange("position",GatePosition.OPENED, GatePosition.CLOSED);
    }
}

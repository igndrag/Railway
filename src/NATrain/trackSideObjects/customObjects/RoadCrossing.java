package NATrain.trackSideObjects.customObjects;

import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.trackSections.TrackSection;

public class RoadCrossing extends AbstractCustomObject {
    static final long serialVersionUID = 1L;
    public static final String INITIAL_ROAD_CROSSING_NAME = "New road crossing";

    public static final RoadCrossing EMPTY_ROAD_CROSSING = new RoadCrossing("None");

    private TrackSection leftSection;
    private TrackSection rightSection;
    private Signal firstSignal;
    private Signal secondSignal;
    private Servo firstServo;
    private Servo secondServo;
    private RoadCrossingState state = RoadCrossingState.OPENED;

    public RoadCrossing(String id) {
        super(id);
        this.type = CustomObjectType.ROAD_CROSSING;
    }

    public Signal getFirstSignal() {
        return firstSignal;
    }

    public void setFirstSignal(Signal firstSignal) {
        if (firstSignal == Signal.EMPTY_SIGNAL) {
            this.firstSignal = null;
        } else {
            this.firstSignal = firstSignal;

        }
    }

    public Signal getSecondSignal() {
        return secondSignal;
    }

    public void setSecondSignal(Signal secondSignal) {
        if (secondSignal == Signal.EMPTY_SIGNAL) {
            this.secondSignal = null;
        } else {
            this.secondSignal = secondSignal;
        }
    }

    public Servo getFirstServo() {
        return firstServo;
    }

    public void setFirstServo(Servo firstServo) {
        if (firstServo == Servo.EMPTY_SERVO) {
            this.firstServo = null;
        } else {
            this.firstServo = firstServo;
        }
    }

    public Servo getSecondServo() {
        return secondServo;
    }

    public void setSecondServo(Servo secondServo) {
        if (secondServo == Servo.EMPTY_SERVO) {
            this.secondServo = null;
        } else {
            this.secondServo = secondServo;
        }
    }

    public TrackSection getLeftSection() {
        return leftSection;
    }

    public void setLeftSection(TrackSection leftSection) {
        if (leftSection == TrackSection.EMPTY_TRACK_SECTION) {
            this.leftSection = null;
        } else {
            this.leftSection = leftSection;
        }
    }

    public TrackSection getRightSection() {
        return rightSection;
    }

    public void setRightSection(TrackSection rightSection) {
        if (rightSection == TrackSection.EMPTY_TRACK_SECTION) {
            this.rightSection = null;
        } else {
            this.rightSection = rightSection;
        }
    }

    public RoadCrossingState getState() {
        return state;
    }

    public void setState(RoadCrossingState newState) {
        if (propertyChangeSupport != null) {
            propertyChangeSupport.firePropertyChange("state", state, newState);
        }
        this.state = newState;
    }
}

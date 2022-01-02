package NATrain.trackSideObjects.customObjects;

import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.сontrolModules.AbstractModule;
import NATrain.сontrolModules.OutputChannel;
import NATrain.сontrolModules.OutputChannelType;

public class PolarityChanger extends AbstractCustomObject{
    static final long serialVersionUID = 1L;

    public static final String INITIAL_POLARITY_CHANGER_NAME = "New polarity changer";

    private Polarity polarity = Polarity.OFF;
    private final OutputChannel normalPolarityOutput = new OutputChannel(OutputChannelType.ON_OFF, this, null);
    private final OutputChannel reversedPolarityOutput = new OutputChannel(OutputChannelType.ON_OFF, this, null);

    public static final PolarityChanger EMPTY_POLARITY_CHANGER = new PolarityChanger("None", CustomObjectType.SWITCH_POLARITY_CHANGER);
    public static final PolarityChanger TEST_POLARITY_CHANGER = new PolarityChanger("TEST_POLARITY_CHANGER", CustomObjectType.SWITCH_POLARITY_CHANGER);

    private Switch associatedSwitch = null;
    private TrackSection leftTrackSection = null;
    private TrackSection rightTrackSection = null;

    public PolarityChanger(String id, CustomObjectType type) {
        super(id);
        this.type = type;
    }

    public OutputChannel getNormalPolarityOutput() {
        return normalPolarityOutput;
    }

    public OutputChannel getReversedPolarityOutput() {
        return reversedPolarityOutput;
    }

    public Polarity getPolarity() {
        return polarity;
    }

    public Switch getAssociatedSwitch() {
        return associatedSwitch;
    }

    public void setAssociatedSwitch(Switch associatedSwitch) {
        if (associatedSwitch == Switch.EMPTY_SWITCH) {
            this.associatedSwitch = null;
        }
        this.associatedSwitch = associatedSwitch;
    }

    public TrackSection getLeftTrackSection() {
        return leftTrackSection;
    }

    public void setLeftTrackSection(TrackSection leftTrackSection) {
        if (leftTrackSection == TrackSection.EMPTY_TRACK_SECTION) {
            this.leftTrackSection = null;
        } else {
            this.leftTrackSection = leftTrackSection;
        }
    }

    public TrackSection getRightTrackSection() {
        return rightTrackSection;
    }

    public void setRightTrackSection(TrackSection rightTrackSection) {
        if (rightTrackSection == TrackSection.EMPTY_TRACK_SECTION) {
            this.rightTrackSection = null;
        } else {
            this.rightTrackSection = rightTrackSection;
        }
    }

    public void normalPolarityOn() {
        this.polarity = Polarity.NORMAL;
        if (normalPolarityOutput.getModule() != null && reversedPolarityOutput.getModule() != null) {
            normalPolarityOutput.sendCommandWithValue(AbstractModule.TOGGLE_OUTPUT_ON, reversedPolarityOutput.getChNumber());
        }
        if (propertyChangeSupport != null) {
            propertyChangeSupport.firePropertyChange("polarity", Polarity.REVERSED, Polarity.NORMAL);
        }
    }

    public void reversedPolarityOn() {
        this.polarity = Polarity.REVERSED;
        if (reversedPolarityOutput.getModule() != null && normalPolarityOutput.getModule() != null) {
            reversedPolarityOutput.sendCommandWithValue(AbstractModule.TOGGLE_OUTPUT_ON, normalPolarityOutput.getChNumber());
        }
        if (propertyChangeSupport != null) {
            propertyChangeSupport.firePropertyChange("polarity", Polarity.NORMAL, Polarity.REVERSED);
        }
    }

    public void changePolarity() {
        if (polarity == Polarity.NORMAL) {
            reversedPolarityOn();
        } else if (polarity == Polarity.REVERSED) {
            normalPolarityOn();
        }
    }

    public void off() {
        this.polarity = Polarity.OFF;
        if (reversedPolarityOutput.getModule() != null && normalPolarityOutput.getModule() != null) {
            normalPolarityOutput.sendCommandCode(0);
            reversedPolarityOutput.sendCommandCode(0);
        }
        if (propertyChangeSupport != null) {
            propertyChangeSupport.firePropertyChange("polarity", null, Polarity.OFF);
        }
    }
}

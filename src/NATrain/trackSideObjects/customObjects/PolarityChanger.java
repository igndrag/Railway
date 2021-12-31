package NATrain.trackSideObjects.customObjects;

import NATrain.trackSideObjects.switches.Switch;
import NATrain.сontrolModules.AbstractModule;
import NATrain.сontrolModules.OutputChannel;
import NATrain.сontrolModules.OutputChannelType;

public class PolarityChanger extends AbstractCustomObject{
    static final long serialVersionUID = 1L;
    private Polarity polarity = Polarity.OFF;
    private final OutputChannel normalPolarityOutput = new OutputChannel(OutputChannelType.ON_OFF, this, null);
    private final OutputChannel reversedPolarityOutput = new OutputChannel(OutputChannelType.ON_OFF, this, null);

    public static final PolarityChanger EMPTY_POLARITY_CHANGER = new PolarityChanger("None");
    public static final PolarityChanger TEST_POLARITY_CHANGER = new PolarityChanger("TEST_POLARITY_CHANGER");

    private Switch associatedSwitch;

    public PolarityChanger(String id) {
        super(id);
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



    public void normalPolarityOn() {
        this.polarity = Polarity.NORMAL;
        if (normalPolarityOutput != null && reversedPolarityOutput != null) {
            normalPolarityOutput.sendCommandWithValue(AbstractModule.TOGGLE_OUTPUT_ON, reversedPolarityOutput.getChNumber());
        }
        if (propertyChangeSupport != null) {
            propertyChangeSupport.firePropertyChange("polarity", Polarity.REVERSED, Polarity.NORMAL);
        }
    }

    public void reversedPolarityOn() {
        this.polarity = Polarity.REVERSED;
        if (reversedPolarityOutput != null && normalPolarityOutput != null) {
            reversedPolarityOutput.sendCommandWithValue(AbstractModule.TOGGLE_OUTPUT_ON, normalPolarityOutput.getChNumber());
        }
        if (propertyChangeSupport != null) {
            propertyChangeSupport.firePropertyChange("polarity", Polarity.NORMAL, Polarity.REVERSED);
        }
    }

    public void off() {
        this.polarity = Polarity.OFF;
        if (reversedPolarityOutput != null && normalPolarityOutput != null) {
            normalPolarityOutput.sendCommandCode(0);
            reversedPolarityOutput.sendCommandCode(0);
        }
        if (propertyChangeSupport != null) {
            propertyChangeSupport.firePropertyChange("polarity", null, Polarity.OFF);
        }
    }
}

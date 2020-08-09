package NATrain.quads;

import NATrain.controller.SwitchState;
import NATrain.quads.configurableInterfaces.SwitchConfigurable;
import NATrain.trackSideObjects.Switch;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public abstract class SwitchQuad extends SimpleTrackQuad implements SwitchConfigurable {

    private Switch associatedSwitch = Switch.EMPTY_SWITCH;
    protected Shape switchPlusElement;
    protected Shape switchMinusElement;
    protected Text switchLabel;

    @Override
    public Switch getAssociatedSwitch() {
        return associatedSwitch;
    }

    @Override
    public void setAssociatedSwitch(Switch associatedSwitch) {
        this.associatedSwitch = associatedSwitch;
    }

    public SwitchQuad(int x, int y) {
        super(x, y);
    }

    @Override
    public void refresh() {
        refreshTrackSectionState(firstAssociatedTrack);
        refreshSwitchState();
        trackLabel.setText(associatedSwitch.getId());
    }

    @Override
    public void showDescription(boolean show) {
        switchLabel.setVisible(show);
    }

    private void refreshSwitchState() {
        if (associatedSwitch != Switch.EMPTY_SWITCH) {
            switch (associatedSwitch.getState()) {
                case UNDEFINED:
                    if (associatedSwitch.getNormalState() == SwitchState.PLUS) {  //normal state always defined plus or minus
                        switchPlusElement.setFill(CONFIGURED_ELEMENT_COLOR);
                        switchMinusElement.setFill(DEFAULT_BACKGROUND_COLOR);
                    } else {
                        switchPlusElement.setFill(DEFAULT_BACKGROUND_COLOR);
                        switchMinusElement.setFill(CONFIGURED_ELEMENT_COLOR);
                    }
                    break;
                case PLUS:
                    switchPlusElement.setFill(firstTrackElement.getFill());
                    switchMinusElement.setFill(DEFAULT_BACKGROUND_COLOR);
                    break;
                case MINUS:
                    switchMinusElement.setFill(firstTrackElement.getFill());
                    switchPlusElement.setFill(DEFAULT_BACKGROUND_COLOR);
                    break;
            }
        } else {
            switchPlusElement.setFill(UNDEFINED_ELEMENT_COLOR);
            switchMinusElement.setFill(UNDEFINED_ELEMENT_COLOR);
        }
    }
}

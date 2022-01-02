package NATrain.quads;

import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.switches.SwitchState;
import NATrain.quads.configurableInterfaces.SwitchConfigurable;
import NATrain.trackSideObjects.switches.Switch;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public abstract class SwitchQuad extends SimpleTrackQuad implements SwitchConfigurable {

    protected Shape switchPlusElement;
    protected Shape switchMinusElement;
    private static final List<ControlAction> availableActions = new ArrayList<>();

    static {
        availableActions.add(ControlAction.CHANGE_SWITCH_POSITION);
    }


    public SwitchQuad(int x, int y) {
        super(x, y);
        globalQuadType = GlobalQuadType.SWITCH_QUAD;
    }

    @Override
    public Switch getAssociatedSwitch() {
        return associatedSwitch;
    }

    @Override
    public void setAssociatedSwitch(Switch associatedSwitch) {
        this.associatedSwitch = associatedSwitch;
    }


    @Override
    public void refresh() {
        updateFirstTrackView();
        updateSwitchView();
        if (associatedSwitch != Switch.EMPTY_SWITCH) {
            descriptionLabel.setText(associatedSwitch.getId());
        }
    }

    @Override
    public List<ControlAction> getAvailableActions() {
        return availableActions;
    }

    @Override
    public boolean isBorderShown() {
        if (borderElement != null) {
            return borderElement.isVisible();
        } else {
            return false;
        }
    }

    @Override
    public boolean hasBorder() {
        return borderElement != null;
    }

    private void refreshSwitchState() {
        if (associatedSwitch != Switch.EMPTY_SWITCH) {
            switch (associatedSwitch.getSwitchState()) {
                case UNDEFINED:
                    if (!WorkPlaceController.isActiveMode()) {
                        if (associatedSwitch.getNormalState() == SwitchState.PLUS) {  //normal state always defined plus or minus
                            switchPlusElement.setFill(CONFIGURED_ELEMENT_COLOR);
                            switchMinusElement.setFill(DEFAULT_BACKGROUND_COLOR);
                        } else {
                            switchPlusElement.setFill(DEFAULT_BACKGROUND_COLOR);
                            switchMinusElement.setFill(CONFIGURED_ELEMENT_COLOR);
                        }
                    } else {
                        switchPlusElement.setFill(DEFAULT_BACKGROUND_COLOR);
                        switchMinusElement.setFill(DEFAULT_BACKGROUND_COLOR);
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

    @Override
    public void updateSwitchView() {
        refreshSwitchState();
    }
}

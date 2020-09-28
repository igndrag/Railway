package NATrain.quads;

import NATrain.quads.configurableInterfaces.SignalConfigurable;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.Signal;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public abstract class SignalQuad extends DoubleTrackQuad implements SignalConfigurable {

    protected Shape firstLampElement;
    protected Shape secondLampElement;

    private static final List<ControlAction> availableActions = new ArrayList<>();

    static {
        availableActions.add(ControlAction.SET_ROUT_FROM);
        availableActions.add(ControlAction.SET_ROUT_BEHIND);
        availableActions.add(ControlAction.SET_ROUT_TO);
    }

    @Override
    public Signal getAssociatedSignal() {
        return associatedSignal;
    }

    @Override
    public void setAssociatedSignal(Signal associatedSignal) {
        this.associatedSignal = associatedSignal;
    }

    public SignalQuad(int x, int y) {
        super(x, y);
        paintView();
    }

    @Override
    public void refresh() {
        refreshTrackSectionState(firstAssociatedTrack, firstTrackElement);
        refreshTrackSectionState(secondAssociatedTrack, secondTrackElement);
        refreshSignalState();
        descriptionLabel.setText(associatedSignal.getId());
    }

    @Override
    public List<ControlAction> getAvailableActions() {
        return availableActions;
    }

    @Override
    public boolean hasDescription() {
        return true;
    }

    @Override
    public void showDescription(boolean show) {
        descriptionLabel.setVisible(show);
    }

    @Override
    public boolean hasBorder() {
        return false;
    }

    private void refreshSignalState() {
        if (associatedSignal != Signal.EMPTY_SIGNAL) {
            switch (associatedSignal.getSignalState()) {
                case UNDEFINED:
                    firstLampElement.setFill(CONFIGURED_ELEMENT_COLOR);
                    if (secondLampElement != null)
                        secondLampElement.setFill(CONFIGURED_ELEMENT_COLOR);
                    break;
                case RED:
                    firstLampElement.setFill(Color.RED);
                    break;
                case BLUE:
                    firstLampElement.setFill(Color.BLUE);
                    break;
                case GREEN:
                    firstLampElement.setFill(Color.GREEN);
                    break;
                case WHITE:
                    firstLampElement.setFill(Color.WHITE);
                    break;
                case YELLOW:
                    firstLampElement.setFill(Color.YELLOW);
                    break;
                case BLINKED_YELLOW:
                    firstLampElement.setFill(YELLOW_BLINKER);
                    break;
                case BLINKED_WHITE:
                    firstLampElement.setFill(WHITE_BLINKER);
                    break;
                case YELLOW_AND_BLINKED_YELLOW:
                    firstLampElement.setFill(Color.YELLOW);
                    if (secondLampElement != null)
                    secondLampElement.setFill(YELLOW_BLINKER);
                    break;
                case NOT_LIGHT:
                    firstLampElement.setFill(SIGNAL_LAMP_BACKGROUND_COLOR);
                    if (secondLampElement != null)
                        secondLampElement.setFill(SIGNAL_LAMP_BACKGROUND_COLOR);
                    break;
            }
        } else {
            firstLampElement.setFill(UNDEFINED_ELEMENT_COLOR);
            if (secondLampElement != null)
                secondLampElement.setFill(UNDEFINED_ELEMENT_COLOR);
        }
    }
}

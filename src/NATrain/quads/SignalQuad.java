package NATrain.quads;

import NATrain.quads.configurableInterfaces.SignalConfigurable;
import NATrain.trackSideObjects.Signal;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public abstract class SignalQuad extends DoubleTrackQuad implements SignalConfigurable {

    private Signal associatedSignal;

    protected Shape firstLampElement;
    protected Shape secondLampElement;
    protected Text signalLabel;

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
        refreshTrackSectionState(firstAssociatedTrack);
        refreshTrackSectionState(secondAssociatedTrack);
        refreshSignalState();
    }

    @Override
    public void showDescription(boolean show) {
        signalLabel.setVisible(show);
    }

    private void refreshSignalState() {
        if (associatedSignal != null) {
            switch (associatedSignal.getSignalState()) {
                case UNDEFINED:
                    firstLampElement.setFill(CONFIGURED_ELEMENT_COLOR);
                    if (secondLampElement != null)
                        secondLampElement.setFill(CONFIGURED_ELEMENT_COLOR);
                case RED:
                    firstLampElement.setFill(Color.RED);
                case BLUE:
                    firstLampElement.setFill(Color.BLUE);
                case GREEN:
                    firstLampElement.setFill(Color.GREEN);
                case WHITE:
                    firstLampElement.setFill(Color.WHITE);
                case YELLOW:
                    firstLampElement.setFill(Color.YELLOW);
                case BLINKED_YELLOW:
                    firstLampElement.setFill(YELLOW_BLINKER);
                case BLINKED_WHITE:
                    firstLampElement.setFill(WHITE_BLINKER);
                case YELLOW_AND_BLINKED_YELLOW:
                    firstLampElement.setFill(Color.YELLOW);
                    secondLampElement.setFill(YELLOW_BLINKER);
                case NOT_LIGHT:
                    firstLampElement.setFill(DEFAULT_BACKGROUND_COLOR);
                    if (secondLampElement != null)
                        secondLampElement.setFill(DEFAULT_BACKGROUND_COLOR);
            }
        } else {
            firstLampElement.setFill(UNDEFINED_ELEMENT_COLOR);
            if (secondLampElement != null)
                secondLampElement.setFill(UNDEFINED_ELEMENT_COLOR);
        }
    }
}

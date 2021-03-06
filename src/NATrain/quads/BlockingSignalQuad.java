package NATrain.quads;

import NATrain.quads.configurableInterfaces.BlockSectionConfigurable;
import NATrain.quads.configurableInterfaces.BlockSignalConfigurable;
import NATrain.routes.TrackDirection;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.signals.Signal;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import java.util.Collections;
import java.util.List;

public abstract class BlockingSignalQuad extends BlockingTrackQuad implements BlockSectionConfigurable, BlockSignalConfigurable {

    protected Shape secondTrackElement;
    protected Shape firstSignalLampElement;
    protected Shape secondSignalLampElement;

    public BlockingSignalQuad(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void refresh() {
        updateFirstTrackView();
        updateSecondTrackView();
        if (firstSignalLampElement != null)
        updateBlockSignalView(firstSignal, firstSignalLampElement);
        if (secondSignalLampElement != null)
        updateBlockSignalView(secondSignal, secondSignalLampElement);
        if (firstSignalLabel != null && firstSignal != Signal.EMPTY_SIGNAL) {
            firstSignalLabel.setText(firstSignal.getId());
        }
        if (secondSignalLabel != null && secondSignal != Signal.EMPTY_SIGNAL) {
            secondSignalLabel.setText(secondSignal.getId());
        }
    }

    @Override
    public void updateSecondTrackView() {
        refreshBlockSectionState(secondBlockSection, secondTrackElement);
    }

    @Override
    public void updateSignalView() {
        if (track.getTrackDirection() == TrackDirection.NORMAL) {
            if (reversedSignalView) {
                updateBlockSignalView(firstSignal, firstSignalLampElement);
            } else {
                updateBlockSignalView(secondSignal, secondSignalLampElement);
            }
        } else {
            if (reversedSignalView) {
                updateBlockSignalView(secondSignal, secondSignalLampElement);
            } else {
                updateBlockSignalView(firstSignal, firstSignalLampElement);
            }
        }
    }

    @Override
    public List<ControlAction> getAvailableActions() {
        return Collections.emptyList();
    }

    private void updateBlockSignalView(Signal signal, Shape lampElement) {
        if (signal != null && signal != Signal.EMPTY_SIGNAL) {
            lampElement.setFill(SIGNAL_LAMP_BACKGROUND_COLOR);
            switch (signal.getSignalState()) {
                case UNDEFINED:
                    lampElement.setFill(TRACK_CONFIGURED_COLOR);
                    break;
                case RED:
                    lampElement.setFill(Color.RED);
                    break;
                case GREEN:
                    lampElement.setFill(Color.GREEN);
                    break;
                case YELLOW:
                    lampElement.setFill(Color.YELLOW);
                    break;
                case BLINKED_YELLOW:
                    lampElement.setFill(YELLOW_BLINKER);
                    break;
                case NOT_LIGHT:
                    lampElement.setFill(SIGNAL_LAMP_BACKGROUND_COLOR);
                    break;
            }
        } else {
            lampElement.setFill(TRACK_UNDEFINED_ELEMENT_COLOR);
        }
    }
}

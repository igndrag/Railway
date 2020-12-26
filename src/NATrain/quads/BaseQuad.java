package NATrain.quads;

import NATrain.UI.workPlace.Blinker;
import NATrain.quads.configurableInterfaces.Configurable;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.SignalState;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import javafx.scene.text.Text;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public abstract class BaseQuad extends AbstractQuad implements Configurable {

    protected TrackSection firstAssociatedTrack = TrackSection.EMPTY_TRACK_SECTION;
    protected TrackSection secondAssociatedTrack = TrackSection.EMPTY_TRACK_SECTION;
    protected Switch associatedSwitch = Switch.EMPTY_SWITCH;
    protected Signal associatedSignal = Signal.EMPTY_SIGNAL;
    protected Text descriptionLabel = new Text("");

    public BaseQuad(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void showDescription(boolean show) {
        descriptionLabel.setVisible(show);
    }

    @Override
    public boolean isDescriptionShown() {
        return descriptionLabel.isVisible();
    }

    @Override
    public void activateListeners() {
        if (firstAssociatedTrack != TrackSection.EMPTY_TRACK_SECTION) {
            firstAssociatedTrack.addPropertyChangeListener(new QuadViewUpdater());
        }
        if (secondAssociatedTrack != TrackSection.EMPTY_TRACK_SECTION) {
            secondAssociatedTrack.addPropertyChangeListener(new QuadViewUpdater());
        }
        if (associatedSwitch != Switch.EMPTY_SWITCH) {
            associatedSwitch.addPropertyChangeListener(new QuadViewUpdater());
        }
        if (associatedSignal != Signal.EMPTY_SIGNAL) {
            associatedSignal.addPropertyChangeListener(new SignalQuadViewUpdater(this));
        }
    }

    private class QuadViewUpdater implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            refresh();
        }
    }

    private class SignalQuadViewUpdater implements PropertyChangeListener {

        Quad quad;
        public SignalQuadViewUpdater(Quad quad) {
            this.quad = quad;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            SignalState newSignalState =  (SignalState) evt.getNewValue();
            if (newSignalState.isBlinking()) {
                Blinker.registerQuad(quad);
            } else {
                Blinker.unregisterQuad(quad);
            }
            refresh();
        }
    }
}

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
            firstAssociatedTrack.addPropertyChangeListener(new FirstTrackViewUpdater());
        }
        if (secondAssociatedTrack != TrackSection.EMPTY_TRACK_SECTION) {
            secondAssociatedTrack.addPropertyChangeListener(new SecondTrackViewUpdater());
        }
        if (associatedSwitch != Switch.EMPTY_SWITCH) {
            associatedSwitch.addPropertyChangeListener(new SwitchTrackViewUpdater());
        }
        if (associatedSignal != Signal.EMPTY_SIGNAL) {
            associatedSignal.addPropertyChangeListener(new SignalQuadViewUpdater(this));
        }
    }
}

package NATrain.quads;

import NATrain.quads.configurableInterfaces.Configurable;
import NATrain.routes.Track;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.scene.text.Text;

import java.beans.PropertyChangeListener;
import java.util.HashMap;


public abstract class BaseQuad extends AbstractQuad implements Configurable {

    protected Track track = Track.EMPTY_TRACK;
    protected TrackSection firstAssociatedTrack = TrackSection.EMPTY_TRACK_SECTION;
    protected TrackSection secondAssociatedTrack = TrackSection.EMPTY_TRACK_SECTION;
    protected Switch associatedSwitch = Switch.EMPTY_SWITCH;
    protected Signal associatedSignal = Signal.EMPTY_SIGNAL;
    protected Text descriptionLabel = new Text("");

    public BaseQuad(int x, int y) {
        super(x, y);
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Text getDescriptionLabel() {
        return descriptionLabel;
    }

    public void setDescriptionLabel(Text descriptionLabel) {
        this.descriptionLabel = descriptionLabel;
    }

    public TrackSection getFirstAssociatedTrack() {
        return firstAssociatedTrack;
    }

    public void setFirstAssociatedTrack(TrackSection firstAssociatedTrack) {
        this.firstAssociatedTrack = firstAssociatedTrack;
    }

    public TrackSection getSecondAssociatedTrack() {
        return secondAssociatedTrack;
    }

    public void setSecondAssociatedTrack(TrackSection secondAssociatedTrack) {
        this.secondAssociatedTrack = secondAssociatedTrack;
    }

    public Switch getAssociatedSwitch() {
        return associatedSwitch;
    }

    public void setAssociatedSwitch(Switch associatedSwitch) {
        this.associatedSwitch = associatedSwitch;
    }

    public Signal getAssociatedSignal() {
        return associatedSignal;
    }

    public void setAssociatedSignal(Signal associatedSignal) {
        this.associatedSignal = associatedSignal;
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
        if (quadListeners == null) {
            quadListeners = new HashMap<>();
        }

        if (firstAssociatedTrack != TrackSection.EMPTY_TRACK_SECTION) {
            PropertyChangeListener firstAssociatedTrackListener = new FirstTrackViewUpdater();
            quadListeners.put(firstAssociatedTrack, firstAssociatedTrackListener);
            firstAssociatedTrack.addPropertyChangeListener(firstAssociatedTrackListener);
        }
        if (secondAssociatedTrack != TrackSection.EMPTY_TRACK_SECTION) {
            PropertyChangeListener secondAssociatedTrackListener = new SecondTrackViewUpdater();
            quadListeners.put(secondAssociatedTrack, secondAssociatedTrackListener);
            secondAssociatedTrack.addPropertyChangeListener(secondAssociatedTrackListener);
        }
        if (associatedSwitch != Switch.EMPTY_SWITCH) {
            PropertyChangeListener switchStateListener = new SwitchTrackViewUpdater();
            quadListeners.put(associatedSwitch, switchStateListener);
            associatedSwitch.addPropertyChangeListener(switchStateListener);
        }
        if (associatedSignal != Signal.EMPTY_SIGNAL) {
            PropertyChangeListener signalListener = new SignalQuadViewUpdater(this);
            quadListeners.put(associatedSignal, signalListener);
            associatedSignal.addPropertyChangeListener(signalListener);
        }
    }

    @Override
    public void deactivateListeners() {
        quadListeners.forEach(TracksideObject::removePropertyChangeListener);
        quadListeners.clear();
    }
}

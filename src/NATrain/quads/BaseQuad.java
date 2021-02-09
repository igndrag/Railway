package NATrain.quads;

import NATrain.quads.configurableInterfaces.Configurable;
import NATrain.routes.Track;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.scene.text.Text;


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

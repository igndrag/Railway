package NATrain.quads;

import NATrain.routes.Track;
import NATrain.routes.TrackBlockSection;
import NATrain.trackSideObjects.Signal;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public abstract class TrackBaseQuad extends AbstractQuad {
    protected Track track = Track.EMPTY_TRACK;
    protected TrackBlockSection firstBlockSection = TrackBlockSection.EMPTY_BLOCK_SECTION;
    protected TrackBlockSection secondBlockSection = TrackBlockSection.EMPTY_BLOCK_SECTION;
    protected Signal firstSignal = Signal.EMPTY_SIGNAL;
    protected Signal secondSignal = Signal.EMPTY_SIGNAL;
    protected Shape firstTrackElement;
    protected Shape secondTrackElement;
    protected Shape firstSignalLampElement;
    protected Shape secondSignalLampElement;
    protected Text blockSectionName; //creating in factory
    protected Boolean reversedSignalView = false;
    protected Text firstSignalLabel;
    protected Text secondSignalLabel;

    public TrackBaseQuad(int x, int y) {
        super(x, y);
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public TrackBlockSection getFirstBlockSection() {
        return firstBlockSection;
    }

    public void setFirstBlockSection(TrackBlockSection firstBlockSection) {
        this.firstBlockSection = firstBlockSection;
    }

    public TrackBlockSection getSecondBlockSection() {
        return secondBlockSection;
    }

    public void setSecondBlockSection(TrackBlockSection secondBlockSection) {
        this.secondBlockSection = secondBlockSection;
    }

    public Signal getFirstSignal() {
        return firstSignal;
    }

    public void setFirstSignal(Signal firstSignal) {
        this.firstSignal = firstSignal;
        if (firstSignalLabel != null) {
            this.firstSignalLabel.setText(firstSignal.getId());
        }
    }

    public Signal getSecondSignal() {
        return secondSignal;
    }

    public void setSecondSignal(Signal secondSignal) {
        this.secondSignal = secondSignal;
        if (secondSignalLabel != null) {
            this.secondSignalLabel.setText(secondSignal.getId());
        }
    }

    public void showBlockSectionName(boolean show) {
        blockSectionName.setVisible(show);
    }

    public Text getBlockSectionName() {
        return blockSectionName;
    }

    public Boolean getReversedSignalView() {
        return reversedSignalView;
    }

    public void setReversedSignalView(Boolean reversedSignalView) {
        this.reversedSignalView = reversedSignalView;
    }
}

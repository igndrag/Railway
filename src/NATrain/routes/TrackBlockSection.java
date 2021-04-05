package NATrain.routes;

import NATrain.trackSideObjects.locomotives.Locomotive;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.trackSections.TrackSection;

import java.io.Serializable;

public class TrackBlockSection extends TrackSection implements Serializable {
    static final long serialVersionUID = 1L;

    public static final TrackBlockSection EMPTY_BLOCK_SECTION = new TrackBlockSection(Track.EMPTY_TRACK, "None");

    private final Track track;
    private Signal normalDirectionSignal = Signal.EMPTY_SIGNAL;
    private Signal reversedDirectionSignal = Signal.EMPTY_SIGNAL;
    private boolean bidirectional = false;
    private boolean lastInNormalDirection = false;
    private boolean lastInReverseDirection = false;

    public TrackBlockSection(Track track, String id) {
        super(id);
        this.track = track;
    }

    public Track getTrack() {
        return track;
    }

    public Signal getNormalDirectionSignal() {
        return normalDirectionSignal;
    }

    public Signal getReversedDirectionSignal() {
        return reversedDirectionSignal;
    }

    public Boolean getBidirectional() {
        return bidirectional;
    }

    public void setNormalDirectionSignal(Signal normalDirectionSignal) {
        this.normalDirectionSignal = normalDirectionSignal;
    }

    public void setReversedDirectionSignal(Signal reversedDirectionSignal) {
        this.reversedDirectionSignal = reversedDirectionSignal;
    }

    public void setBidirectional(Boolean bidirectional) {
        this.bidirectional = bidirectional;
    }

    public boolean isLastInNormalDirection() {
        return lastInNormalDirection;
    }

    public void setLastInNormalDirection(boolean lastInNormalDirection) {
        this.lastInNormalDirection = lastInNormalDirection;
    }

    public boolean isLastInReverseDirection() {
        return lastInReverseDirection;
    }

    public void setLastInReversedDirection(boolean lastInReversedDirection) {
        this.lastInReverseDirection = lastInReversedDirection;
    }

    @Override
    public String toString() {
        return getId();
    }
}

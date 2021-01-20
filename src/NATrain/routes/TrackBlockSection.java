package NATrain.routes;

import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.TrackSection;

import java.io.Serializable;

public class TrackBlockSection implements Serializable {
    static final long serialVersionUID = 1L;

    public static final TrackBlockSection EMPTY_BLOCK_SECTION = new TrackBlockSection(Track.EMPTY_TRACK,TrackSection.EMPTY_TRACK_SECTION);

    private final Track track;
    private TrackSection section = TrackSection.EMPTY_TRACK_SECTION;
    private Signal normalDirectionSignal = Signal.EMPTY_SIGNAL;
    private Signal reversedDirectionSignal = Signal.EMPTY_SIGNAL;
    private boolean bidirectional = false;
    private boolean lastInNormalDirection = false;
    private boolean lastInReverseDirection = false;

    public TrackBlockSection(Track track, TrackSection section) {
        this.track = track;
        this.section = section;
    }

    public Track getTrack() {
        return track;
    }

    public TrackSection getSection() {
        return section;
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

    public String getId() {
        return section.getId();
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
        return section.getId();
    }
}

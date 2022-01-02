package NATrain.routes;

import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.trackSections.TrackSection;

import java.io.Serializable;

public class TracklineBlockSection extends TrackSection implements Serializable {
    static final long serialVersionUID = 1L;

    public static final TracklineBlockSection EMPTY_BLOCK_SECTION = new TracklineBlockSection(Trackline.EMPTY_TRACKLINE, "None");

    private final Trackline trackline;
    private Signal normalDirectionSignal = Signal.EMPTY_SIGNAL;
    private Signal reversedDirectionSignal = Signal.EMPTY_SIGNAL;
    private boolean bidirectional = false;
    private boolean lastInNormalDirection = false;
    private boolean lastInReverseDirection = false;

    public TracklineBlockSection(Trackline trackline, String id) {
        super(id);
        this.trackline = trackline;
    }

    public Trackline getTrack() {
        return trackline;
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

package NATrain.routes;

import NATrain.trackSideObjects.Signal;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Track implements Serializable {
    static final long serialVersionUID = 1L;

    private String id;
    private TrackDirection trackDirection = TrackDirection.NORMAL;
    private boolean bidirectional = false;
    private final List<TrackBlockSection> blockSections = new CopyOnWriteArrayList<>();
    private TrackBlockingType trackBlockingType = TrackBlockingType.AUTOMATIC_THREE_SIGNAL_BLOCKING;
    private Signal normalDirectionArrivalSignal = Signal.EMPTY_SIGNAL;
    private Signal reversedDirectionArrivalSignal = Signal.EMPTY_SIGNAL;

    public Track(String id) {
        this.id = id;
    }

    public TrackDirection getTrackDirection() {
        return trackDirection;
    }

    public List<TrackBlockSection> getBlockSections() {
        return blockSections;
    }

    public void setTrackDirection(TrackDirection trackDirection) {
        if (bidirectional) {
            this.trackDirection = trackDirection;
        } else {
            System.out.println("Try to set direction for specialized track");
        }
    }

    public void setBidirectional(boolean bidirectional) {
        this.bidirectional = bidirectional;
    }

    public boolean isBidirectional() {
        return bidirectional;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBlockSectionCount() {
        return blockSections.size();
    }

    public TrackBlockingType getTrackBlockingType() {
        return trackBlockingType;
    }

    public void setTrackBlockingType(TrackBlockingType trackBlockingType) {
        this.trackBlockingType = trackBlockingType;
    }

    public Signal getNormalDirectionArrivalSignal() {
        return normalDirectionArrivalSignal;
    }

    public void setNormalDirectionArrivalSignal(Signal normalDirectionArrivalSignal) {
        this.normalDirectionArrivalSignal = normalDirectionArrivalSignal;
    }

    public Signal getReversedDirectionArrivalSignal() {
        return reversedDirectionArrivalSignal;
    }

    public void setReversedDirectionArrivalSignal(Signal reversedDirectionArrivalSignal) {
        this.reversedDirectionArrivalSignal = reversedDirectionArrivalSignal;
    }
}

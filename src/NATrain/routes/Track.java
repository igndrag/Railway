package NATrain.routes;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Track implements Serializable {
    static final long serialVersionUID = 1L;

    TrackDirection trackDirection = TrackDirection.NORMAL;
    boolean bidirectional = false;
    List<TrackBlockSection> blockSections = new CopyOnWriteArrayList<>();

    public TrackDirection getTrackDirection() {
        return trackDirection;
    }

    public List<TrackBlockSection> getBlockSections() {
        return blockSections;
    }

    public void setTrackDirection(TrackDirection trackDirection) {
        this.trackDirection = trackDirection;
    }

    public boolean isBidirectional() {
        return bidirectional;
    }

    public int getBlockSectionCount() {
        return blockSections.size();
    }
}

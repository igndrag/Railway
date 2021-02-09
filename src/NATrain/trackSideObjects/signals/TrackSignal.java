package NATrain.trackSideObjects.signals;

import NATrain.routes.TrackDirection;

public class TrackSignal {
    TrackDirection trackDirection = TrackDirection.NORMAL;

    public TrackDirection getTrackDirection() {
        return trackDirection;
    }

    public void setTrackDirection(TrackDirection trackDirection) {
        this.trackDirection = trackDirection;
    }
}

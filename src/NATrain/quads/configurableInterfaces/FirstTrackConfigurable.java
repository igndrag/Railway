package NATrain.quads.configurableInterfaces;

import NATrain.trackSideObjects.TrackSection;

public interface FirstTrackConfigurable {
    TrackSection getFirstAssociatedTrack();
    void setFirstAssociatedTrack(TrackSection trackSection);
}

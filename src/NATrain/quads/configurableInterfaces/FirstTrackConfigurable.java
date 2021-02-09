package NATrain.quads.configurableInterfaces;

import NATrain.trackSideObjects.trackSections.TrackSection;

public interface FirstTrackConfigurable {
    TrackSection getFirstAssociatedTrack();
    void setFirstAssociatedTrack(TrackSection trackSection);
}

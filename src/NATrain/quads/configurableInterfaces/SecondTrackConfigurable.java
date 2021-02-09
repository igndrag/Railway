package NATrain.quads.configurableInterfaces;

import NATrain.trackSideObjects.trackSections.TrackSection;

public interface SecondTrackConfigurable {
    TrackSection getSecondAssociatedTrack();
    void setSecondAssociatedTrack(TrackSection trackSection);
}

package NATrain.quads.configurableInterfaces;

import NATrain.trackSideObjects.TrackSection;

public interface SecondTrackConfigurable {
    TrackSection getSecondAssociatedTrack();
    void setSecondAssociatedTrack(TrackSection trackSection);
}

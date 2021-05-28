package NATrain.trackSideObjects;

import NATrain.trackSideObjects.trackSections.TrackSection;

public interface Movable {
    void setFrontTag(RFIDTag tag);
    RFIDTag getFrontTag();
    void setRearTag(RFIDTag tag);
    RFIDTag getRearTag();
    TrackSection getLocation();
}

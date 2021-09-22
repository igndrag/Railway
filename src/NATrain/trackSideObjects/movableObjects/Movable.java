package NATrain.trackSideObjects.movableObjects;

import NATrain.trackSideObjects.RFIDTag;
import NATrain.trackSideObjects.trackSections.TrackSection;

public interface Movable {
    String getId();
    void setFrontTag(RFIDTag tag);
    RFIDTag getFrontTag();
    void setRearTag(RFIDTag tag);
    RFIDTag getRearTag();
    default TrackSection getFrontTagLocation() {
        if (getFrontTag() == null) {
            return TrackSection.EMPTY_TRACK_SECTION;
        } else {
            return getFrontTag().getTagLocation();
        }
    };

    default TrackSection getRearTagLocation() {
        if (getRearTag() == null) {
            return TrackSection.EMPTY_TRACK_SECTION;
        } else {
            return getRearTag().getTagLocation();
        }
    }

    MovableObjectType getMovableObjectType();
}

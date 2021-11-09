package NATrain.trackSideObjects.movableObjects;

import NATrain.model.Model;
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

    default void deallocateMovableObject() {
        if (getFrontTag() != null) {
            getFrontTag().setTagLocation(null);
            TrackSection occupiedSection = getFrontTagLocation();
            if (occupiedSection != null) {
                occupiedSection.getTags().remove(getFrontTag());
                occupiedSection.updateVacancyState();
            }
        }

        if (getRearTag() != null) {
            getRearTag().setTagLocation(null);
            TrackSection occupiedSection = getRearTagLocation();
            if (occupiedSection != null) {
                occupiedSection.getTags().remove(getRearTag());
                occupiedSection.updateVacancyState();
            }
        }
    }
}

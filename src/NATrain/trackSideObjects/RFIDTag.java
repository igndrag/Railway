package NATrain.trackSideObjects;

import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.utils.UtilFunctions;

import java.io.Serializable;

public class RFIDTag implements Serializable {
    static final long serialVersionUID = 1L;
    private String[] uid;
    private String id;
    private long decUid;
    private TrackSection tagLocation;
    private TagType tagType;

    public static RFIDTag EMPTY_TAG = new RFIDTag("ff", "ff", "ff", "ff", TagType.EMPTY_TAG);


    public RFIDTag(String b1, String b2, String b3, String b4, TagType tagType) {
        this.uid = new String[4];
        uid[0] = b1;
        uid[1] = b2;
        uid[2] = b3;
        uid[3] = b4;
        decUid = UtilFunctions.convertUidToLong(b1, b2, b3, b4);
        this.tagType = tagType;
    }

    public TrackSection getTagLocation() {
        return tagLocation;
    }

    public void setTagLocation(TrackSection tagLocation) {
        this.tagLocation = tagLocation;
    }

    public long getDecUid() {
        return decUid;
    }

    public String[] getUid() {
        return uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TagType getTagType() {
        return tagType;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", uid[0], uid[1], uid[2], uid[3]);
    }
}

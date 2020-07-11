package NATrain.trackSideObjects;

import java.util.Comparator;

public abstract class TrackSideObject {

    public static Comparator<TrackSideObject> idComparator = Comparator.comparing(TrackSideObject::getId);

    public TrackSideObject(String id) {
        this.id = id;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

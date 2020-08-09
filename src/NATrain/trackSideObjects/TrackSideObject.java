package NATrain.trackSideObjects;

import NATrain.quads.Quad;

import java.util.ArrayList;
import java.util.List;

public abstract class TrackSideObject {

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

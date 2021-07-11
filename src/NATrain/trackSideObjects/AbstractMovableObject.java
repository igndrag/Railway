package NATrain.trackSideObjects;

import NATrain.trackSideObjects.trackSections.TrackSection;

public abstract class AbstractMovableObject extends TracksideObject implements Movable {
    static final long serialVersionUID = 1L;

    private RFIDTag frontTag;
    private RFIDTag rearTag;

    public AbstractMovableObject(String id) {
        super(id);
    }

    @Override
    public RFIDTag getFrontTag() {
        return frontTag;
    }

    @Override
    public void setFrontTag(RFIDTag frontTag) {
        this.frontTag = frontTag;
    }

    @Override
    public RFIDTag getRearTag() {
        return rearTag;
    }

    @Override
    public void setRearTag(RFIDTag rearTag) {
        this.rearTag = rearTag;
    }
}

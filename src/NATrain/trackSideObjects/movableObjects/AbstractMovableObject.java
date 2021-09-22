package NATrain.trackSideObjects.movableObjects;

import NATrain.trackSideObjects.RFIDTag;
import NATrain.trackSideObjects.TracksideObject;

public abstract class AbstractMovableObject extends TracksideObject implements Movable {
    static final long serialVersionUID = 1L;

    protected RFIDTag frontTag;
    protected RFIDTag rearTag;
    protected MovableObjectType movableObjectType;

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

    @Override
    public MovableObjectType getMovableObjectType() {
        return movableObjectType;
    }

    public void setMovableObjectType(MovableObjectType movableObjectType) {
        this.movableObjectType = movableObjectType;
    }
}

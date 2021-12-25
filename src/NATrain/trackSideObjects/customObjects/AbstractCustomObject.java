package NATrain.trackSideObjects.customObjects;

import NATrain.trackSideObjects.TracksideObject;

public abstract class AbstractCustomObject extends TracksideObject {

    protected CustomObjectType type;

    public AbstractCustomObject(String id) {
        super(id);
    }

    public CustomObjectType getType() {
        return type;
    }
}

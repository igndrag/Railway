package NATrain.trackSideObjects.movableObjects;

import NATrain.trackSideObjects.RFIDTag;

import java.io.Serializable;

public class Wagon extends AbstractMovableObject implements Serializable {
    static final long serialVersionUID = 1L;

    public static final String INITIAL_WAGON_NAME = "New Wagon";

    public Wagon(String id) {
        super(id);
    }

}

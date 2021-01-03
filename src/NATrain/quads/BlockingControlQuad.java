package NATrain.quads;

import NATrain.trackSideObjects.ControlAction;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockingControlQuad extends BlockingTrackQuad {

    private static final List<ControlAction> availableActions = new ArrayList<>();

    static {
        availableActions.add(ControlAction.CHANGE_TRACK_DIRECTION);
    }

    public BlockingControlQuad(int x, int y) {
        super(x, y);
    }

    @Override
    public List<ControlAction> getAvailableActions() {
        return availableActions;
    }

    //create direction rows in view
}

package NATrain.quads.custom;

import NATrain.quads.AbstractQuad;
import NATrain.trackSideObjects.ControlAction;

import java.util.List;

public abstract class AbstractCustomQuad extends AbstractQuad { //excluding useless function of Quad interface
    public AbstractCustomQuad(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void select() {
        //do nothing
    }

    @Override
    public void unselect() {
        //do nothing
    }

    @Override
    public void updateFirstTrackView() {
        //do nothing
    }

    @Override
    public void updateSecondTrackView() {
        //do nothing
    }

    @Override
    public void updateSignalView() {
        //do nothing
    }

    @Override
    public void updateSwitchView() {
        //do nothing
    }
}

package NATrain.quads;

import NATrain.routes.Trackline;
import NATrain.routes.TrackDirection;
import NATrain.trackSideObjects.ControlAction;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockingControlQuad extends BlockingBaseQuad {

    private static final List<ControlAction> availableActions = new ArrayList<>();

    protected Shape firstRawElement;
    protected Shape secondRawElement;

    static {
        availableActions.add(ControlAction.CHANGE_TRACK_LINE_DIRECTION);
    }

    public BlockingControlQuad(int x, int y) {
        super(x, y);
        paintView();
        globalQuadType = GlobalQuadType.BLOCKING_TRACK_QUAD;
    }

    @Override
    public List<ControlAction> getAvailableActions() {
        return availableActions;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void refresh() {
        if (blockSectionName != null) {
            if (trackline != Trackline.EMPTY_TRACKLINE) {
                if (trackline.getTrackDirection() == TrackDirection.NORMAL) {
                    firstRawElement.setFill(TRACK_NORMAL_DIRECTION_RAW_COLOR);
                    secondRawElement.setFill(TRACK_DIRECTION_RAW_BACKGROUND_COLOR);
                } else {
                    firstRawElement.setFill(TRACK_DIRECTION_RAW_BACKGROUND_COLOR);
                    secondRawElement.setFill(TRACK_REVERSED_DIRECTION_RAW_COLOR);
                }
                blockSectionName.setText(trackline.getId());
            } else {
                firstRawElement.setFill(TRACK_DIRECTION_RAW_BACKGROUND_COLOR);
                secondRawElement.setFill(TRACK_DIRECTION_RAW_BACKGROUND_COLOR);
                blockSectionName.setText("");
            }
        }
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
    //create direction rows in view
}

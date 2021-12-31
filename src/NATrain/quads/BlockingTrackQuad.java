package NATrain.quads;

import NATrain.quads.configurableInterfaces.BlockSectionConfigurable;
import NATrain.routes.TrackBlockSection;
import NATrain.trackSideObjects.ControlAction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockingTrackQuad extends BlockingBaseQuad implements BlockSectionConfigurable {

    public BlockingTrackQuad(int x, int y) {
        super(x, y);
        paintView();
    }

    protected Shape firstTrackElement;


    public void showBlockSectionName(boolean show) {
        if (blockSectionName != null) {
            blockSectionName.setVisible(show);
        }
    }

    private static final List<ControlAction> availableActions = new ArrayList<>();

    static {
        availableActions.add(ControlAction.SET_ROUTE_TO_TRACK_LINE);
        availableActions.add(ControlAction.ALLOCATE_MOVABLE_MODEl);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void refresh() {
        updateFirstTrackView();
        if (firstBlockSection != null && firstBlockSection != TrackBlockSection.EMPTY_BLOCK_SECTION) {
            blockSectionName.setText(firstBlockSection.getId());
        }
    }

    @Override
    public void updateFirstTrackView() {
        refreshBlockSectionState(firstBlockSection, firstTrackElement);
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

    @Override
    public List<ControlAction> getAvailableActions() {
        return availableActions;
    }


}

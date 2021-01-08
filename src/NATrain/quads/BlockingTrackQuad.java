package NATrain.quads;

import NATrain.quads.configurableInterfaces.BlockSectionConfigurable;
import NATrain.routes.TrackBlockSection;
import NATrain.trackSideObjects.ControlAction;

import java.util.Collections;
import java.util.List;

public abstract class BlockingTrackQuad extends TrackBaseQuad implements BlockSectionConfigurable {

    public BlockingTrackQuad(int x, int y) {
        super(x, y);
    }

    public void showBlockSectionName(boolean show) {
        blockSectionName.setVisible(show);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void refresh() {
        updateFirstTrackView();
        if (firstBlockSection != TrackBlockSection.EMPTY_BLOCK_SECTION) {
            blockSectionName.setText(firstBlockSection.getId());
        }
    }

    @Override
    public void activateListeners() {
        // TODO when create view updaters
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
        return Collections.emptyList();
    }
}

package NATrain.quads;

import NATrain.quads.configurableInterfaces.BlockSectionConfigurable;
import NATrain.routes.TrackBlockSection;
import NATrain.trackSideObjects.ControlAction;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.Collections;
import java.util.List;

public abstract class BlockingTrackQuad extends AbstractQuad implements BlockSectionConfigurable {

    TrackBlockSection blockSection;
    protected Shape firstTrackElement;
    protected Text trackName = new Text(); // move to factory created instances ???


    public BlockingTrackQuad(int x, int y) {
        super(x, y);
    }

    public TrackBlockSection getBlockSection() {
        return blockSection;
    }

    public void setBlockSection(TrackBlockSection blockSection) {
        this.blockSection = blockSection;
        trackName.setText(blockSection.getId());
    }

    public void showTrackName(boolean show) {
        trackName.setVisible(show);
    }

    public boolean isTrackNameShown () {
        return trackName.isVisible();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void refresh() {
        refreshTrackSectionState(blockSection.getSection(), firstTrackElement);
    }

    @Override
    public void activateListeners() {

    }

}

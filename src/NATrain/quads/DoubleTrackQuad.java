package NATrain.quads;

import NATrain.quads.configurableInterfaces.FirstTrackConfigurable;
import NATrain.quads.configurableInterfaces.SecondTrackConfigurable;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.TrackSection;
import javafx.scene.shape.Shape;

import java.util.Collections;
import java.util.List;

public abstract class DoubleTrackQuad extends BaseQuad implements FirstTrackConfigurable, SecondTrackConfigurable {

    protected Shape firstTrackElement;
    protected Shape borderElement = null;
    protected Shape secondTrackElement;

    public DoubleTrackQuad(int x, int y) { super(x, y);
    }

    @Override
    public void refresh() {
        updateFirstTrackView();
        updateSecondTrackView();
    }

    @Override
    public TrackSection getFirstAssociatedTrack() {
        return firstAssociatedTrack;
    }

    @Override
    public void setFirstAssociatedTrack(TrackSection trackSection) {
        this.firstAssociatedTrack = trackSection;
    }

    @Override
    public TrackSection getSecondAssociatedTrack() {
        return secondAssociatedTrack;
    }

    @Override
    public void setSecondAssociatedTrack(TrackSection trackSection) {
        this.secondAssociatedTrack = trackSection;
    }

    @Override
    public void showDescription(boolean show) {
        //show nothing
    }

    @Override
    public boolean hasDescription() {
        return false;
    }

    @Override
    public boolean hasBorder() {
        return false;
    }

    @Override
    public void showTrackBorder(boolean show) {
        //do nothing
    }

    @Override
    public boolean isBorderShown() {
        return false;
    }

    @Override
    public List<ControlAction> getAvailableActions() {
        return Collections.emptyList();
    }

    @Override
    public void updateFirstTrackView() {
        refreshTrackSectionState(firstAssociatedTrack, firstTrackElement);
    }

    @Override
    public void updateSecondTrackView() {
        refreshTrackSectionState(secondAssociatedTrack, secondTrackElement);
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

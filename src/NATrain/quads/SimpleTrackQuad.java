package NATrain.quads;

import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.quads.configurableInterfaces.FirstTrackConfigurable;
import NATrain.routes.StationTrack;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleTrackQuad extends BaseQuad implements FirstTrackConfigurable {

    protected Shape firstTrackElement;
    protected Shape borderElement;
    protected Shape isolatorElement;

    protected static final List<ControlAction> availableActions = new ArrayList<>();
    protected static final List<ControlAction> availableActionsForStationTrack = new ArrayList<>();

    static {
        availableActions.add(ControlAction.SET_ROUTE_TO_TRACK);
        availableActions.add(ControlAction.ALLOCATE_MOVABLE_MODEl);

        availableActionsForStationTrack.add(ControlAction.SET_ROUTE_TO_TRACK);
        availableActionsForStationTrack.add(ControlAction.ALLOCATE_MOVABLE_MODEl);
}

    public SimpleTrackQuad(int x, int y) {
        super(x, y);
        globalQuadType = GlobalQuadType.SIMPLE_TRACK_QUAD;
        paintView();
    }


    @Override
    public void refresh() {
        updateFirstTrackView();
        if (!WorkPlaceController.isActiveMode() && firstAssociatedTrack != TrackSection.EMPTY_TRACK_SECTION) {
            descriptionLabel.setText(firstAssociatedTrack.getId());
        }
    }

    @Override
    public List<ControlAction> getAvailableActions() {
        if (firstAssociatedTrack instanceof StationTrack)
            return availableActionsForStationTrack;
        else
            return availableActions;
    }

    @Override
    public void setFirstAssociatedTrack(TrackSection trackSection) {
        this.firstAssociatedTrack = trackSection;
    }

    @Override
    public TrackSection getFirstAssociatedTrack() {
        return firstAssociatedTrack;
    }

    @Override
    public void showDescription(boolean show) {
            descriptionLabel.setVisible(show);
    }

    @Override
    public void showTrackBorder(boolean show) {
        if (borderElement != null)
            borderElement.setVisible(show);
        if (isolatorElement != null)
            isolatorElement.setVisible(show);
    }

    @Override
    public boolean isBorderShown() {
        if (borderElement != null) {
            return borderElement.isVisible();
        } else {
            return false;
        }
    }

    @Override
    public boolean hasDescription() {
        return true;
    }

    @Override
    public boolean hasBorder() {
        return borderElement != null;
    }

    @Override
    public void updateFirstTrackView() {
        refreshTrackSectionState(firstAssociatedTrack, firstTrackElement);
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


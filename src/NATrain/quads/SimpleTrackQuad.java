package NATrain.quads;

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

    private static final List<ControlAction> availableActions = new ArrayList<>();
    private static final List<ControlAction> availableActionsForStationTrack = new ArrayList<>();

    static {
        availableActions.add(ControlAction.SET_ROUTE_TO_TRACK);

        availableActionsForStationTrack.add(ControlAction.SET_ROUTE_TO_TRACK);
        availableActionsForStationTrack.add(ControlAction.ALLOCATE_LOCOMOTIVE);
}


    public SimpleTrackQuad(int x, int y) {
        super(x, y);
        paintView();
    }


    @Override
    public void refresh() {
        updateFirstTrackView();
        if (firstAssociatedTrack != TrackSection.EMPTY_TRACK_SECTION) { // TODO do it only in constructor mode
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
        return borderElement.isVisible();
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


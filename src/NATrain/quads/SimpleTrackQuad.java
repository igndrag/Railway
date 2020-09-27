package NATrain.quads;

import NATrain.quads.configurableInterfaces.Configurable;
import NATrain.quads.configurableInterfaces.FirstTrackConfigurable;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.TrackSection;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SimpleTrackQuad extends BaseQuad implements FirstTrackConfigurable {

    protected Shape firstTrackElement;
    protected Shape borderElement;
    protected Shape isolatorElement;

    private static final List<ControlAction> availableActions = new ArrayList<>();

    static {
        availableActions.add(ControlAction.SET_ROUTE_TO_TRACK);
    }

    public SimpleTrackQuad(int x, int y) {
        super(x, y);
        paintView();
    }


    @Override
    public void refresh() {
        refreshTrackSectionState(firstAssociatedTrack, firstTrackElement);
        descriptionLabel.setText(firstAssociatedTrack.getId());
    }

    @Override
    public List<ControlAction> getAvailableActions() {
        return availableActions;
    }

    void refreshTrackSectionState(TrackSection associatedTrack, Shape trackSectionElement) {
        if (associatedTrack == TrackSection.EMPTY_TRACK_SECTION) {
            trackSectionElement.setFill(UNDEFINED_ELEMENT_COLOR);
        } else {
            switch (associatedTrack.getVacancyState()) {
                case UNDEFINED:
                    trackSectionElement.setFill(CONFIGURED_ELEMENT_COLOR);
                    break;
                case FREE:
                    if (firstAssociatedTrack.isInterlocked())
                        trackSectionElement.setFill(INTERLOCKED_ELEMENT_COLOR);
                    else
                        trackSectionElement.setFill(FREE_ELEMENT_COLOR);
                    break;
                case OCCUPIED:
                    trackSectionElement.setFill(OCCUPIED_ELEMENT_COLOR);
                    break;
            }
        }
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
}


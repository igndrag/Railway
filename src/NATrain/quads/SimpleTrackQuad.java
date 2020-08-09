package NATrain.quads;

import NATrain.quads.configurableInterfaces.Configurable;
import NATrain.quads.configurableInterfaces.FirstTrackConfigurable;
import NATrain.trackSideObjects.TrackSection;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public abstract class SimpleTrackQuad extends BaseQuad implements Quad, Paintable, Configurable, FirstTrackConfigurable {

    protected TrackSection firstAssociatedTrack;
    protected Shape firstTrackElement;
    protected Text trackLabel;
    protected Shape borderElement;
    protected Shape isolatorElement;

    public SimpleTrackQuad(int x, int y) {
        super(x, y);
        paintView();
    }

    @Override
    public void refresh() {
        refreshTrackSectionState(firstAssociatedTrack);
    }

    void refreshTrackSectionState(TrackSection secondAssociatedTrack) {
        if (secondAssociatedTrack == null) {
            firstTrackElement.setFill(UNDEFINED_ELEMENT_COLOR);
        } else {
            switch (secondAssociatedTrack.getVacancyState()) {
                case UNDEFINED:
                    firstTrackElement.setFill(UNDEFINED_ELEMENT_COLOR);
                    break;
                case FREE:
                    if (firstAssociatedTrack.isInterlocked())
                        firstTrackElement.setFill(INTERLOCKED_ELEMENT_COLOR);
                    else
                        firstTrackElement.setFill(FREE_ELEMENT_COLOR);
                    break;
                case OCCUPIED:
                    firstTrackElement.setFill(OCCUPIED_ELEMENT_COLOR);
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
        if (trackLabel != null)
            trackLabel.setVisible(show);
    }

    @Override
    public void showTrackBorder(boolean show) {
        if (borderElement != null)
            borderElement.setVisible(show);
        if (isolatorElement != null)
            isolatorElement.setVisible(show);
    }
}


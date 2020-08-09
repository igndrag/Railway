package NATrain.quads;

import NATrain.quads.configurableInterfaces.FirstTrackConfigurable;
import NATrain.quads.configurableInterfaces.SecondTrackConfigurable;
import NATrain.trackSideObjects.TrackSection;
import javafx.scene.shape.Shape;

public abstract class DoubleTrackQuad extends SimpleTrackQuad implements FirstTrackConfigurable, SecondTrackConfigurable {

   TrackSection secondAssociatedTrack = TrackSection.EMPTY_TRACK_SECTION;
   protected Shape secondTrackElement;

    public DoubleTrackQuad(int x, int y) {
        super(x, y);
    }

    @Override
    public void refresh() {
        refreshTrackSectionState(firstAssociatedTrack, firstTrackElement);
        refreshTrackSectionState(secondAssociatedTrack, secondTrackElement);
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
}

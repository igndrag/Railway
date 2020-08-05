package NATrain.quads;

import NATrain.quads.configurableInterfaces.FirstTrackConfigurable;
import NATrain.quads.configurableInterfaces.SecondTrackConfigurable;
import NATrain.trackSideObjects.TrackSection;
import javafx.scene.shape.Shape;

public abstract class DoubleTrackQuad extends SimpleTrackQuad implements FirstTrackConfigurable, SecondTrackConfigurable {

   TrackSection secondAssociatedTrack;
   protected Shape secondTrackElement;

    public DoubleTrackQuad(int x, int y) {
        super(x, y);
    }

    @Override
    public void refresh() {
        super.refresh();
        refreshTrackSectionState(secondAssociatedTrack);
    }

    @Override
    public TrackSection getSecondAssociatedTrack() {
        return secondAssociatedTrack;
    }

    @Override
    public void setSecondAssociatedTrack(TrackSection trackSection) {
        this.secondAssociatedTrack = trackSection;
    }
}

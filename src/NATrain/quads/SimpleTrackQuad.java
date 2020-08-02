package NATrain.quads;

import NATrain.NavigatorFxController;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public abstract class SimpleTrackQuad extends BaseQuad implements Quad, Paintable {

    protected Shape firstTrackElement;

    public SimpleTrackQuad(int x, int y) {
        super(x, y);
    }

    @Override
    public void refresh() {
        if (NavigatorFxController.constructorMode) {
            trackConfigured(firstAssociatedTrack, firstTrackElement);
        } else {
            trackStateRefresh(firstAssociatedTrack, firstTrackElement);
        }
    }
}


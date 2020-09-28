package NATrain.quads.STQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Polygon;

public class STQ3_3 extends SimpleTrackQuad {

    public STQ3_3(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.STQ3_3;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                0, 50,
                10, 50,
                35, 0,
                15, 0,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
    }
}

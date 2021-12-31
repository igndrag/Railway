package NATrain.quads.STQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Polygon;

public class STQ6_2 extends SimpleTrackQuad {

    public STQ6_2(int x, int y) {
        super(x, y);
        quadType = QuadType.STQ6_2;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                15, 0,
                35, 0,
                10, 40,
                35, 80,
                15, 80,
                0, 55,
                0, 25,
                15, 0
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
    }
}

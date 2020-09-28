package NATrain.quads.STQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Polygon;

public class STQ3_2 extends SimpleTrackQuad {

    public STQ3_2(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.STQ3_2;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                90, 30,
                90, 50,
                75, 80,
                55, 80,
                80, 30,
                90, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

    }
}

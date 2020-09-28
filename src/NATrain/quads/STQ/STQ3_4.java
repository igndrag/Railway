package NATrain.quads.STQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Polygon;

public class STQ3_4 extends SimpleTrackQuad {

    public STQ3_4(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.STQ3_4;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                90, 30,
                90, 50,
                80, 50,
                55, 0,
                75, 0,
                90, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
    }
}

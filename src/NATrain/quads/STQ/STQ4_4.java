package NATrain.quads.STQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Polygon;

public class STQ4_4 extends SimpleTrackQuad {

    public STQ4_4(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.STQ4_4;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                35, 0,
                55, 0,
                55, 15,
                70, 30,
                90, 30,
                90, 50,
                65, 50,
                35, 20,
                35, 0
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
    }
}

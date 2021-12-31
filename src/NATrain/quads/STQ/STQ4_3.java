package NATrain.quads.STQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class STQ4_3 extends SimpleTrackQuad {

    public STQ4_3(int x, int y) {
        super(x, y);
        quadType = QuadType.STQ4_3;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                0, 50,
                25, 50,
                55, 20,
                55, 0,
                35, 0,
                35, 15,
                20, 30,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
    }
}

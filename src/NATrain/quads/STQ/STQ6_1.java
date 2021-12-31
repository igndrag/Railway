package NATrain.quads.STQ;

import NATrain.quads.AbstractQuad;
import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class STQ6_1 extends SimpleTrackQuad {

    public STQ6_1(int x, int y) {
        super(x, y);
        quadType = QuadType.STQ6_1;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                55, 0,
                75, 0,
                90, 25,
                90, 55,
                75, 80,
                55, 80,
                80, 40,
                55, 0
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
    }
}

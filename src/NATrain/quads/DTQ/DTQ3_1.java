package NATrain.quads.DTQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.DoubleTrackQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class DTQ3_1 extends DoubleTrackQuad {
    public DTQ3_1(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.DTQ3_1;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(0, 30, 90 , 20);
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        secondTrackElement = new Rectangle(35, 0, 20, 80);
        secondTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondTrackElement);
    }
}

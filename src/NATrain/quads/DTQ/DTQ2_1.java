package NATrain.quads.DTQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.DoubleTrackQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class DTQ2_1 extends DoubleTrackQuad {
    public DTQ2_1(int x, int y) {
        super(x, y);
        quadType = QuadType.DTQ2_1;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                15, 0,
                35, 0,
                50, 30,
                30, 30,
                15, 0
                );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        secondTrackElement = new Polygon(
                40, 50,
                60, 50,
                75, 80,
                55, 80,
                40, 50
        );
        secondTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondTrackElement);
    }
}

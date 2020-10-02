package NATrain.quads.DTQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.DoubleTrackQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Polygon;

public class DTQ2_2 extends DoubleTrackQuad {
    public DTQ2_2(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.DTQ2_2;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                55, 0,
                75, 0,
                60, 30,
                40, 30,
                55, 0
                );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        secondTrackElement = new Polygon(
                30, 50,
                50, 50,
                35, 80,
                15, 80,
                30, 50
        );
        secondTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondTrackElement);
    }
}

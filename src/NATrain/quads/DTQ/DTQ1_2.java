package NATrain.quads.DTQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.DoubleTrackQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Rectangle;

public class DTQ1_2 extends DoubleTrackQuad {
    public DTQ1_2(int x, int y) {
        super(x, y);
        quadType = QuadType.DTQ1_2;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(35, 0, 20 , 30);
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        secondTrackElement = new Rectangle(35, 50, 20 , 30);
        secondTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondTrackElement);
    }
}
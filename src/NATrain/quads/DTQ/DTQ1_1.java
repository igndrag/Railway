package NATrain.quads.DTQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.DoubleTrackQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Rectangle;

public class DTQ1_1 extends DoubleTrackQuad {
    public DTQ1_1(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.DTQ1_1;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(0, 30, 35 , 20);
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        secondTrackElement = new Rectangle(55, 30, 35 , 20);
        secondTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondTrackElement);
    }
}

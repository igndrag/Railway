package NATrain.quads.BTQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.BlockingTrackQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BTQ4_3 extends BlockingTrackQuad {

    public BTQ4_3(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.BTQ4_3;
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
        firstTrackElement.setFill(BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
    }
}

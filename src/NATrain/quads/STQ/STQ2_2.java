package NATrain.quads.STQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class STQ2_2 extends SimpleTrackQuad {

    public STQ2_2(int x, int y) {
        super(x, y);
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                55, 0,
                75 , 0,
                35, 80,
                15, 80,
                55, 0);
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
        trackLabel = new Text();
        trackLabel.setTextAlignment(TextAlignment.RIGHT);
        trackLabel.setWrappingWidth(40);
        trackLabel.setX(0);
        trackLabel.setY(20);
        addToQuadView(trackLabel);
    }
}

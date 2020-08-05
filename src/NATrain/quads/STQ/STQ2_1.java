package NATrain.quads.STQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class STQ2_1 extends SimpleTrackQuad {

    public STQ2_1(int x, int y) {
        super(x, y);
        paintView();
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                15, 0,
                35 , 0,
                75, 80,
                55, 80,
                15, 0);
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
        trackLabel = new Text();
        trackLabel.setTextAlignment(TextAlignment.LEFT);
        trackLabel.setWrappingWidth(40);
        trackLabel.setX(50);
        trackLabel.setY(20);
        addToQuadView(trackLabel);
    }
}

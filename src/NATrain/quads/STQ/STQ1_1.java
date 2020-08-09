package NATrain.quads.STQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class STQ1_1 extends SimpleTrackQuad {

    public STQ1_1(int x, int y) {
        super(x, y);
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(0, 30, 90 , 20);
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
        trackLabel = new Text();
        trackLabel.setTextAlignment(TextAlignment.CENTER);
        trackLabel.setWrappingWidth(90);
        trackLabel.setX(0);
        trackLabel.setY(25);
        addToQuadView(trackLabel);
    }
}

package NATrain.quads.STQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class STQ1_2 extends SimpleTrackQuad {

    public STQ1_2(int x, int y) {
        super(x, y);
        paintView();
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(35, 0, 20, 80);
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
        trackLabel = new Text();
        trackLabel.setTextAlignment(TextAlignment.LEFT);
        trackLabel.setWrappingWidth(30);
        trackLabel.setX(60);
        trackLabel.setY(40);
        addToQuadView(trackLabel);
    }
}

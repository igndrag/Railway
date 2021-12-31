package NATrain.quads.STQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class STQ1_1 extends SimpleTrackQuad {

    public STQ1_1(int x, int y) {
        super(x, y);
        quadType = QuadType.STQ1_1;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(0, 30, 90 , 20);
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);
        descriptionLabel.setWrappingWidth(90);
        descriptionLabel.setX(0);
        descriptionLabel.setY(25);
        addToQuadView(descriptionLabel);
    }
}

package NATrain.quads.STQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class STQ2_1 extends SimpleTrackQuad {

    public STQ2_1(int x, int y) {
        super(x, y);
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
        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.LEFT);
        descriptionLabel.setWrappingWidth(40);
        descriptionLabel.setX(50);
        descriptionLabel.setY(20);
        addToQuadView(descriptionLabel);
    }
}

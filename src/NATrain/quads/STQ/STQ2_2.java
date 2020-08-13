package NATrain.quads.STQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class STQ2_2 extends SimpleTrackQuad {

    public STQ2_2(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.STQ2_2;
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
        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.RIGHT);
        descriptionLabel.setWrappingWidth(40);
        descriptionLabel.setX(0);
        descriptionLabel.setY(20);
        addToQuadView(descriptionLabel);
    }
}

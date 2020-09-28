package NATrain.quads.STQ;

import NATrain.quads.AbstractQuad;
import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class STQ4_2 extends SimpleTrackQuad {

    public STQ4_2(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.STQ4_2;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                90, 30,
                90, 50,
                70, 50,
                55, 65,
                55, 80,
                35, 80,
                35, 60,
                65, 30,
                90, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.RIGHT);
        descriptionLabel.setWrappingWidth(80);
        descriptionLabel.setX(10);
        descriptionLabel.setY(20);
        addToQuadView(descriptionLabel);
    }
}

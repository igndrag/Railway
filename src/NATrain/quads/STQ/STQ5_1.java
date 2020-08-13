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

public class STQ5_1 extends SimpleTrackQuad {

    public STQ5_1(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.STQ5_1;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                30, 30,
                55, 80,
                35, 80,
                20, 50,
                0, 50,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        borderElement = new Polygon(
                30, 70,
                50, 70,
                55, 80,
                35, 80,
                30, 70
        );
        borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
        borderElement.setVisible(false);
        addToQuadView(borderElement);

        Shape isolator = new Rectangle(35, 78, 20 , 2);
        isolator.setFill(AbstractQuad.ISOLATOR_ELEMENT_COLOR);
        isolator.setVisible(false);
        addToQuadView(isolator);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.LEFT);
        descriptionLabel.setWrappingWidth(80);
        descriptionLabel.setX(10);
        descriptionLabel.setY(20);
        addToQuadView(descriptionLabel);
    }
}

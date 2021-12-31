package NATrain.quads.STQ;

import NATrain.quads.AbstractQuad;
import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class STQ5_3 extends SimpleTrackQuad {

    public STQ5_3(int x, int y) {
        super(x, y);
        quadType = QuadType.STQ5_3;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                20, 30,
                35, 0,
                55, 0,
                30, 50,
                0, 50,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        borderElement = new Polygon(
                30, 10,
                35, 0,
                55, 0,
                50, 10,
                30, 10
        );
        borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
        borderElement.setVisible(false);
        addToQuadView(borderElement);

        isolatorElement = new Rectangle(35, 0, 20 , 2);
        isolatorElement.setFill(AbstractQuad.ISOLATOR_ELEMENT_COLOR);
        isolatorElement.setVisible(false);
        addToQuadView(isolatorElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.LEFT);
        descriptionLabel.setWrappingWidth(80);
        descriptionLabel.setX(10);
        descriptionLabel.setY(60);
        addToQuadView(descriptionLabel);
    }
}

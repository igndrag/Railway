package NATrain.quads.STQ;

import NATrain.quads.AbstractQuad;
import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class STQ5_4 extends SimpleTrackQuad {

    public STQ5_4(int x, int y) {
        super(x, y);
        quadType = QuadType.STQ5_4;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                90, 30,
                70, 30,
                55, 0,
                35, 0,
                60, 50,
                90, 50,
                90, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        borderElement = new Polygon(
                35, 0,
                55, 0,
                60, 10,
                40, 10,
                35, 0
        );
        borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
        borderElement.setVisible(false);
        addToQuadView(borderElement);

        isolatorElement = new Rectangle(35, 0, 20 , 2);
        isolatorElement.setFill(AbstractQuad.ISOLATOR_ELEMENT_COLOR);
        isolatorElement.setVisible(false);
        addToQuadView(isolatorElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.RIGHT);
        descriptionLabel.setWrappingWidth(80);
        descriptionLabel.setX(10);
        descriptionLabel.setY(20);
        addToQuadView(descriptionLabel);
    }
}

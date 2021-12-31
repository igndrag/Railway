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

public class STQ5_2 extends SimpleTrackQuad {

    public STQ5_2(int x, int y) {
        super(x, y);
        quadType = QuadType.STQ5_2;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                90, 30,
                60, 30,
                35, 80,
                55, 80,
                70, 50,
                90, 50,
                90, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        borderElement = new Polygon(
                40, 70,
                60, 70,
                55, 80,
                35, 80,
                40, 70
        );
        borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
        borderElement.setVisible(false);
        addToQuadView(borderElement);

        isolatorElement = new Rectangle(35, 78, 20 , 2);
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

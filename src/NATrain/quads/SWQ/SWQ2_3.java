package NATrain.quads.SWQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SwitchQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SWQ2_3 extends SwitchQuad {
    public SWQ2_3(int x, int y) {
        super(x, y);
        quadType = QuadType.SWQ2_3;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                90, 30,
                90, 50,
                75, 80,
                55, 80,
                70, 50,
                0, 50,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        switchPlusElement = new Polygon(
                70, 30,
                80, 30,
                70, 50,
                60, 50,
                70, 30
        );
        switchPlusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchPlusElement);

        switchMinusElement = new Polygon(
                70, 50,
                90, 50,
                85, 60,
                65, 60,
                70, 50
        );
        switchMinusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchMinusElement);

        borderElement = new Polygon(
                60, 70,
                80, 70,
                75, 80,
                55, 80,
                60, 70
        );

        borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
        borderElement.setVisible(false);
        addToQuadView(borderElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.RIGHT);
        descriptionLabel.setWrappingWidth(60);
        descriptionLabel.setX(0);
        descriptionLabel.setY(20);
        addToQuadView(descriptionLabel);
    }
}

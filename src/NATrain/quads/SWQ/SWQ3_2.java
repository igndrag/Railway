package NATrain.quads.SWQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SwitchQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SWQ3_2 extends SwitchQuad {
    public SWQ3_2(int x, int y) {
        super(x, y);
        quadType = QuadType.SWQ3_2;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                55, 30,
                70, 0,
                90, 0,
                75, 30,
                90, 30,
                90, 50,
                0, 50,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        switchPlusElement = new Polygon(
                75, 30,
                85, 30,
                75, 50,
                65, 50,
                75, 30
        );
        switchPlusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchPlusElement);

        switchMinusElement = new Polygon(
                55, 30,
                60, 20,
                80, 20,
                75, 30,
                55, 30
        );
        switchMinusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchMinusElement);

        borderElement = new Polygon(
                70, 0,
                90, 0,
                85, 10,
                65, 10,
                70, 0
        );

        borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
        borderElement.setVisible(false);
        addToQuadView(borderElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.RIGHT);
        descriptionLabel.setWrappingWidth(65);
        descriptionLabel.setX(0);
        descriptionLabel.setY(65);
        addToQuadView(descriptionLabel);
    }
}

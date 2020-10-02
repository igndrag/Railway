package NATrain.quads.SWQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SwitchQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SWQ3_1 extends SwitchQuad {
    public SWQ3_1(int x, int y) {
        super(x, y);
        quadType = QuadType.SWQ3_1;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                15, 30,
                0, 0,
                20, 0,
                35, 30,
                90, 30,
                90, 50,
                0, 50,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        switchPlusElement = new Polygon(
                5, 30,
                15, 30,
                25, 50,
                15, 50,
                5, 30
        );
        switchPlusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchPlusElement);

        switchMinusElement = new Polygon(
                10, 20,
                30, 20,
                35, 30,
                15, 30,
                10, 20
        );
        switchMinusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchMinusElement);

        borderElement = new Polygon(
                0, 0,
                20, 0,
                25, 10,
                5, 10,
                0, 0
        );

        borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
        borderElement.setVisible(false);
        addToQuadView(borderElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.LEFT);
        descriptionLabel.setWrappingWidth(60);
        descriptionLabel.setX(30);
        descriptionLabel.setY(65);
        addToQuadView(descriptionLabel);
    }
}

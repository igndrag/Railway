package NATrain.quads.SWQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SwitchQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SWQ2_4 extends SwitchQuad {
    public SWQ2_4(int x, int y) {
        super(x, y);
        quadType = QuadType.SWQ2_4;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                90, 30,
                90, 50,
                20, 50,
                35, 80,
                15, 80,
                0, 50,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        switchPlusElement = new Polygon(
                10, 30,
                20, 30,
                30, 50,
                20, 50,
                10, 30
        );
        switchPlusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchPlusElement);

        switchMinusElement = new Polygon(
                0, 50,
                20, 50,
                25, 60,
                5, 60,
                0, 50
        );
        switchMinusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchMinusElement);

        borderElement = new Polygon(
                10, 70,
                30, 70,
                35, 80,
                15, 80,
                10, 70
        );

        borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
        borderElement.setVisible(false);
        addToQuadView(borderElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.LEFT);
        descriptionLabel.setWrappingWidth(80);
        descriptionLabel.setX(10);
        descriptionLabel.setY(20);
        addToQuadView(descriptionLabel);
    }
}

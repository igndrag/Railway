package NATrain.quads.SWQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SwitchQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SWQ2_2 extends SwitchQuad {
    public SWQ2_2(int x, int y) {
        super(x, y);
        quadType = QuadType.SWQ2_2;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                15, 0,
                35, 0,
                20, 30,
                90, 30,
                90, 50,
                0, 50,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        switchPlusElement = new Polygon(
                20, 30,
                30, 30,
                20, 50,
                10, 50,
                20, 30
        );
        switchPlusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchPlusElement);

        switchMinusElement = new Polygon(
                0, 30,
                5, 20,
                25, 20,
                20, 30,
                0, 30
        );
        switchMinusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchMinusElement);

        borderElement = new Polygon(
                15, 0,
                35, 0,
                30, 10,
                10, 10,
                15, 0
        );

        borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
        borderElement.setVisible(false);
        addToQuadView(borderElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.LEFT);
        descriptionLabel.setWrappingWidth(70);
        descriptionLabel.setX(10);
        descriptionLabel.setY(65);
        addToQuadView(descriptionLabel);
    }
}

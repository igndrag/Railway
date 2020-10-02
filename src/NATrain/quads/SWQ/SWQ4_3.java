package NATrain.quads.SWQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SwitchQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SWQ4_3 extends SwitchQuad {
    public SWQ4_3(int x, int y) {
        super(x, y);
        quadType = QuadType.SWQ4_3;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                90, 30,
                90, 50,
                50, 50,
                35, 80,
                15, 80,
                30, 50,
                0, 50,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        switchPlusElement = new Polygon(
                30, 30,
                40, 30,
                30, 50,
                20, 50,
                30, 30
        );
        switchPlusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchPlusElement);

        switchMinusElement = new Polygon(
                30, 50,
                50, 50,
                45, 60,
                25, 60,
                30, 50
        );
        switchMinusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchMinusElement);

        borderElement = new Polygon(
                20, 70,
                40, 70,
                35, 80,
                15, 80,
                20, 70
        );

        borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
        borderElement.setVisible(false);
        addToQuadView(borderElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.RIGHT);
        descriptionLabel.setWrappingWidth(40);
        descriptionLabel.setX(0);
        descriptionLabel.setY(20);
        addToQuadView(descriptionLabel);
    }
}

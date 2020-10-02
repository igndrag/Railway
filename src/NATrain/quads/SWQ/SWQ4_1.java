package NATrain.quads.SWQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SwitchQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SWQ4_1 extends SwitchQuad {
    public SWQ4_1(int x, int y) {
        super(x, y);
        quadType = QuadType.SWQ4_1;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                30, 30,
                15, 0,
                35, 0,
                50, 30,
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
                40, 50,
                30, 50,
                20, 30
        );
        switchPlusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchPlusElement);

        switchMinusElement = new Polygon(
                25, 20,
                45, 20,
                50, 30,
                30, 30,
                25, 20
        );
        switchMinusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchMinusElement);

        borderElement = new Polygon(
                15, 0,
                35, 0,
                40, 10,
                30, 10,
                15, 0
        );

        borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
        borderElement.setVisible(false);
        addToQuadView(borderElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.LEFT);
        descriptionLabel.setWrappingWidth(50);
        descriptionLabel.setX(40);
        descriptionLabel.setY(65);
        addToQuadView(descriptionLabel);
    }
}

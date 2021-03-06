package NATrain.quads.SWQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SwitchQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SWQ1_1 extends SwitchQuad {
    public SWQ1_1(int x, int y) {
        super(x, y);
        quadType = QuadType.SWQ1_1;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                50, 30,
                35, 0,
                55, 0,
                70, 30,
                90, 30,
                90, 50,
                0, 50,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        switchPlusElement = new Polygon(
                40, 30,
                50, 30,
                60, 50,
                50, 50,
                40, 30
        );
        switchPlusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchPlusElement);

        switchMinusElement = new Polygon(
                45, 20,
                65, 20,
                70, 30,
                50, 30,
                45,20
        );
        switchMinusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchMinusElement);

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

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.RIGHT);
        descriptionLabel.setWrappingWidth(70);
        descriptionLabel.setX(0);
        descriptionLabel.setY(65);
        addToQuadView(descriptionLabel);
    }
}

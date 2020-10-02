package NATrain.quads.SWQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SwitchQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SWQ1_3 extends SwitchQuad {
    public SWQ1_3(int x, int y) {
        super(x, y);
        quadType = QuadType.SWQ1_3;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                90, 30,
                90, 50,
                70, 50,
                55, 80,
                35, 80,
                50, 50,
                0, 50,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        switchPlusElement = new Polygon(
                50, 30,
                60, 30,
                50, 50,
                40, 50,
                50, 30
        );
        switchPlusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchPlusElement);

        switchMinusElement = new Polygon(
                50, 50,
                70, 50,
                65, 60,
                45, 60,
                50, 50
        );
        switchMinusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchMinusElement);

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

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.RIGHT);
        descriptionLabel.setWrappingWidth(60);
        descriptionLabel.setX(0);
        descriptionLabel.setY(20);
        addToQuadView(descriptionLabel);
    }
}

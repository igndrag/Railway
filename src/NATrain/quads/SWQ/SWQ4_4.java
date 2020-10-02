package NATrain.quads.SWQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SwitchQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SWQ4_4 extends SwitchQuad {
    public SWQ4_4(int x, int y) {
        super(x, y);
        quadType = QuadType.SWQ4_4;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                90, 30,
                90, 50,
                60, 50,
                75, 80,
                55, 80,
                40, 50,
                0, 50,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        switchPlusElement = new Polygon(
                50, 30,
                60, 30,
                70, 50,
                60, 50,
                50, 30
        );
        switchPlusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchPlusElement);

        switchMinusElement = new Polygon(
                40, 50,
                60, 50,
                65, 60,
                45, 60,
                40, 50
        );
        switchMinusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchMinusElement);

        borderElement = new Polygon(
                50, 70,
                70, 70,
                75, 80,
                55, 80,
                50, 70
        );

        borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
        borderElement.setVisible(false);
        addToQuadView(borderElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.LEFT);
        descriptionLabel.setWrappingWidth(40);
        descriptionLabel.setX(50);
        descriptionLabel.setY(20);
        addToQuadView(descriptionLabel);
    }
}

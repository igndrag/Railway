package NATrain.quads.SWQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SwitchQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SWQ4_2 extends SwitchQuad {
    public SWQ4_2(int x, int y) {
        super(x, y);
        quadType = QuadType.SWQ4_2;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                40, 30,
                55, 0,
                75, 0,
                60, 30,
                90, 30,
                90, 50,
                0, 50,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        switchPlusElement = new Polygon(
                60, 30,
                70, 30,
                60, 50,
                50, 50,
                60, 30
        );
        switchPlusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchPlusElement);

        switchMinusElement = new Polygon(
                40, 30,
                45, 20,
                65, 20,
                60, 30,
                40, 30
        );
        switchMinusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchMinusElement);

        borderElement = new Polygon(
                55, 0,
                75, 0,
                70, 10,
                50, 10,
                55, 0
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

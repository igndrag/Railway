package NATrain.quads.SWQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.SwitchQuad;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SWQ1_1 extends SwitchQuad {
    public SWQ1_1(int x, int y) {
        super(x, y);
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                70, 30,
                55, 0,
                75, 0,
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
                80, 50,
                70, 50,
                60, 30
        );
        switchPlusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchPlusElement);

        switchMinusElement = new Polygon(
                65, 20,
                85, 20,
                90, 30,
                70, 30,
                65,20
        );
        switchMinusElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(switchMinusElement);

        borderElement = new Polygon(
                55, 0,
                75, 0,
                80, 10,
                60, 10,
                55, 0
        );

        borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
        borderElement.setVisible(false);
        addToQuadView(borderElement);

        switchLabel = new Text();
        switchLabel.setTextAlignment(TextAlignment.RIGHT);
        switchLabel.setWrappingWidth(50);
        switchLabel.setX(0);
        switchLabel.setY(20);
        addToQuadView(switchLabel);
    }
}

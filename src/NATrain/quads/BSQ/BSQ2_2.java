package NATrain.quads.BSQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.BlockingSignalQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BSQ2_2 extends BlockingSignalQuad {
    public BSQ2_2(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.BSQ2_2;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(0, 30 , 35, 20);
        firstTrackElement.setFill(BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        secondTrackElement = new Rectangle(55, 30, 35, 20);
        secondTrackElement.setFill(BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondTrackElement);

        Line secondSignalBottom = new Line(45, 55, 45, 75);
        secondSignalBottom.setStrokeWidth(2);
        addToQuadView(secondSignalBottom);

        Line secondSignalStand = new Line(45, 65, 50, 65);
        secondSignalStand.setStrokeWidth(2);
        addToQuadView(secondSignalStand);

        Circle secondSignalBorder = new Circle(60, 65, 10);
        addToQuadView(secondSignalBorder);

        secondSignalLampElement = new Circle(60, 65, 8, BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondSignalLampElement);

        secondSignalLabel = new Text();
        secondSignalLabel.setTextAlignment(TextAlignment.RIGHT);
        secondSignalLabel.setWrappingWidth(40);
        secondSignalLabel.setX(0);
        secondSignalLabel.setY(70);
        addToQuadView(secondSignalLabel);
    }
}

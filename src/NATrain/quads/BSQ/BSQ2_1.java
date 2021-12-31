package NATrain.quads.BSQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.BlockingSignalQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BSQ2_1 extends BlockingSignalQuad {
    public BSQ2_1(int x, int y) {
        super(x, y);
        quadType = QuadType.BSQ2_1;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(0, 30 , 35, 20);
        firstTrackElement.setFill(BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        secondTrackElement = new Rectangle(55, 30, 35, 20);
        secondTrackElement.setFill(BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondTrackElement);

        Line firstSignalBottom = new Line(45, 5, 45, 25);
        firstSignalBottom.setStrokeWidth(2);
        addToQuadView(firstSignalBottom);

        Line firstSignalStand = new Line(45, 15, 40, 15);
        firstSignalStand.setStrokeWidth(2);
        addToQuadView(firstSignalStand);

        Circle firstSignalBorder = new Circle(30, 15, 10);
        addToQuadView(firstSignalBorder);

        firstSignalLampElement = new Circle(30, 15, 8, BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstSignalLampElement);

        firstSignalLabel = new Text();
        firstSignalLabel.setTextAlignment(TextAlignment.LEFT);
        firstSignalLabel.setWrappingWidth(40);
        firstSignalLabel.setX(50);
        firstSignalLabel.setY(20);
        addToQuadView(firstSignalLabel);
    }
}

package NATrain.quads.BSQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.BlockingSignalQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BSQ3_1 extends BlockingSignalQuad {
    public BSQ3_1(int x, int y) {
        super(x, y);
        quadType = QuadType.BSQ3_1;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(35, 0, 20 , 30);
        firstTrackElement.setFill(BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        secondTrackElement = new Rectangle(35, 50, 20 , 30);
        secondTrackElement.setFill(BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondTrackElement);

        Line firstSignalBottom = new Line(65, 40, 85, 40);
        firstSignalBottom.setStrokeWidth(2);
        addToQuadView(firstSignalBottom);

        Line firstSignalStand = new Line(75, 40, 75, 35);
        firstSignalStand.setStrokeWidth(2);
        addToQuadView(firstSignalStand);

        Circle firstSignalBorder = new Circle(75, 25, 10);
        addToQuadView(firstSignalBorder);

        firstSignalLampElement = new Circle(75, 25, 8, BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstSignalLampElement);

        firstSignalLabel = new Text();
        firstSignalLabel.setTextAlignment(TextAlignment.RIGHT);
        firstSignalLabel.setWrappingWidth(30);
        firstSignalLabel.setX(55);
        firstSignalLabel.setY(55);
        addToQuadView(firstSignalLabel);

        Line secondSignalBottom = new Line(5, 40, 25, 40);
        secondSignalBottom.setStrokeWidth(2);
        addToQuadView(secondSignalBottom);

        Line secondSignalStand = new Line(15, 40, 15, 45);
        secondSignalStand.setStrokeWidth(2);
        addToQuadView(secondSignalStand);

        Circle secondSignalBorder = new Circle(15, 55, 10);
        addToQuadView(secondSignalBorder);

        secondSignalLampElement = new Circle(15, 55, 8, BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondSignalLampElement);

        secondSignalLabel = new Text();
        secondSignalLabel.setTextAlignment(TextAlignment.LEFT);
        secondSignalLabel.setWrappingWidth(30);
        secondSignalLabel.setX(5);
        secondSignalLabel.setY(35);
        addToQuadView(secondSignalLabel);
    }
}


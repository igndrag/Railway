package NATrain.quads.BSQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.BlockingSignalQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BSQ4_1 extends BlockingSignalQuad {
    public BSQ4_1(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.BSQ4_1;
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
        firstSignalLabel.setTextAlignment(TextAlignment.CENTER);
        firstSignalLabel.setWrappingWidth(20);
        firstSignalLabel.setX(65);
        firstSignalLabel.setY(55);
        addToQuadView(firstSignalLabel);
    }
}

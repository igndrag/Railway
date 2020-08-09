package NATrain.quads.SIQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.SignalQuad;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SIQ1_1 extends SignalQuad {
    public SIQ1_1(int x, int y) {
        super(x, y);
        paintView();
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(0, 30 , 10, 20);
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        secondTrackElement = new Rectangle(30, 30, 60, 20);
        secondTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondTrackElement);

        Line signalBottom = new Line(10, 55, 10, 75);
        signalBottom.setStrokeWidth(2);
        addToQuadView(signalBottom);

        Circle signalBorder = new Circle(20, 65, 10);
        addToQuadView(signalBorder);

        firstLampElement = new Circle(20, 65, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstLampElement);

        signalLabel = new Text();
        signalLabel.setTextAlignment(TextAlignment.LEFT);
        signalLabel.setWrappingWidth(50);
        signalLabel.setX(35);
        signalLabel.setY(70);
        addToQuadView(signalLabel);
    }
}

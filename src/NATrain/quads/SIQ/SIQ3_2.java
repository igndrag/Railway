package NATrain.quads.SIQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SignalQuad;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SIQ3_2 extends SignalQuad {
    public SIQ3_2(int x, int y) {
        super(x, y);
        quadType = QuadType.SIQ3_2;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(0, 30 , 35, 20);
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        secondTrackElement = new Rectangle(55, 30, 35, 20);
        secondTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondTrackElement);

        Line signalBottom = new Line(45, 5, 45, 25);
        signalBottom.setStrokeWidth(2);
        addToQuadView(signalBottom);

        Circle firstSignalBorder = new Circle(35, 15, 10);
        addToQuadView(firstSignalBorder);

        firstLampElement = new Circle(35, 15, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstLampElement);

        Circle secondSignalBorder = new Circle(15, 15, 10);
        addToQuadView(secondSignalBorder);

        secondLampElement = new Circle(15, 15, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondLampElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.LEFT);
        descriptionLabel.setWrappingWidth(40);
        descriptionLabel.setX(50);
        descriptionLabel.setY(20);
        addToQuadView(descriptionLabel);
    }
}

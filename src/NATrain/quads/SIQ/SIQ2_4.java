package NATrain.quads.SIQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SignalQuad;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SIQ2_4 extends SignalQuad {
    public SIQ2_4(int x, int y) {
        super(x, y);
        quadType = QuadType.SIQ2_4;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(35, 0, 20 , 30);
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        secondTrackElement = new Rectangle(35, 50, 20 , 30);
        secondTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondTrackElement);

        Line signalBottom = new Line(5, 40, 25, 40);
        signalBottom.setStrokeWidth(2);
        addToQuadView(signalBottom);

        Line signalStand = new Line(15, 40, 15, 45);
        signalStand.setStrokeWidth(2);
        addToQuadView(signalStand);

        Circle firstSignalBorder = new Circle(15, 55, 10);
        addToQuadView(firstSignalBorder);

        firstLampElement = new Circle(15, 55, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstLampElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);
        descriptionLabel.setWrappingWidth(30);
        descriptionLabel.setX(0);
        descriptionLabel.setY(35);
        addToQuadView(descriptionLabel);
    }
}

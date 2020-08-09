package NATrain.quads.DSQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.SignalQuad;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class DSQ1_1 extends SignalQuad {
    public DSQ1_1(int x, int y) {
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

        Line signalStand = new Line (10, 65, 20, 65);
        signalStand.setStrokeWidth(2);
        addToQuadView(signalStand);

        Circle signalBorder = new Circle(30, 65, 10);
        addToQuadView(signalBorder);

        signalBorder = new Circle(50, 65, 10);
        addToQuadView(signalBorder);

        firstLampElement = new Circle(30, 65, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstLampElement);

        secondLampElement = new Circle(50, 65, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondLampElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.LEFT);
        descriptionLabel.setWrappingWidth(25);
        descriptionLabel.setX(65);
        descriptionLabel.setY(70);
        addToQuadView(descriptionLabel);
    }
}

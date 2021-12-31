package NATrain.quads.SIQ;

import NATrain.quads.ArrivalSignalQuad;
import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SIQA3_4 extends ArrivalSignalQuad {
    public SIQA3_4(int x, int y) {
        super(x, y);
        quadType = QuadType.SIQA3_4;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(35, 0, 20 , 30);
        firstTrackElement.setFill(BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        secondTrackElement = new Rectangle(35, 50, 20 , 30);
        secondTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondTrackElement);

        Line signalBottom = new Line(5, 40, 25, 40);
        signalBottom.setStrokeWidth(2);
        addToQuadView(signalBottom);

        Circle firstSignalBorder = new Circle(15, 50, 10);
        addToQuadView(firstSignalBorder);

        firstLampElement = new Circle(15, 50, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstLampElement);

        Circle secondSignalBorder = new Circle(15, 70, 10);
        addToQuadView(secondSignalBorder);

        secondLampElement = new Circle(15, 70, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondLampElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);
        descriptionLabel.setWrappingWidth(30);
        descriptionLabel.setX(0);
        descriptionLabel.setY(35);
        addToQuadView(descriptionLabel);
    }
}

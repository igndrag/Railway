package NATrain.quads.SIQ;

import NATrain.quads.ArrivalSignalQuad;
import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SIQA3_2 extends ArrivalSignalQuad {
    public SIQA3_2(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.SIQA3_2;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(0, 30 , 35, 20);
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        secondTrackElement = new Rectangle(55, 30, 35, 20);
        secondTrackElement.setFill(BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondTrackElement);

        Line signalBottom = new Line(45, 55, 45, 75);
        signalBottom.setStrokeWidth(2);
        addToQuadView(signalBottom);

        Circle firstLampBorder = new Circle(55, 65, 10);
        addToQuadView(firstLampBorder);

        firstLampElement = new Circle(55, 65, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstLampElement);

        Circle secondLampBorder = new Circle(75, 65, 10);
        addToQuadView(secondLampBorder);

        secondLampElement = new Circle(75, 65, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondLampElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.RIGHT);
        descriptionLabel.setWrappingWidth(40);
        descriptionLabel.setX(0);
        descriptionLabel.setY(70);
        addToQuadView(descriptionLabel);
    }
}

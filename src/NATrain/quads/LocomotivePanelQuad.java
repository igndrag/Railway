package NATrain.quads;

import NATrain.UI.workPlace.Blinker;
import NATrain.trackSideObjects.signals.Signal;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class LocomotivePanelQuad extends SignalQuad {

    private Signal signal;

    public LocomotivePanelQuad() {
        super(0, 0);

    }

    @Override
    public void paintView() {
        Line signalBottom = new Line(20, 70, 40, 70);
        signalBottom.setStrokeWidth(2);
        addToQuadView(signalBottom);

        Line signalStand = new Line(30, 70, 30, 60);
        signalStand.setStrokeWidth(2);
        addToQuadView(signalStand);

        Circle firstSignalBorder = new Circle(30, 55, 10);
        addToQuadView(firstSignalBorder);

        firstLampElement = new Circle(30, 55, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstLampElement);

        Circle secondSignalBorder = new Circle(30, 35, 10);
        addToQuadView(secondSignalBorder);

        secondLampElement = new Circle(30, 35, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondLampElement);

        descriptionLabel = new Text();
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);
        descriptionLabel.setWrappingWidth(40);
        descriptionLabel.setX(10);
        descriptionLabel.setY(85);
        addToQuadView(descriptionLabel);

        quadView.getChildren().remove(background);
    }

    @Override
    public void refresh() {
        updateSignalView();
    }
}

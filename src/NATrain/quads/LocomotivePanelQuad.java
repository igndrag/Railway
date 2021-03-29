package NATrain.quads;

import NATrain.trackSideObjects.signals.Signal;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class LocomotivePanelQuad extends SignalQuad {

    private Signal signal;
    protected Shape background;
    protected Group quadView;

    public LocomotivePanelQuad() {
        super(0, 0);
     //   background = new Rectangle(40, 80);
     //   background.setFill(AbstractQuad.DEFAULT_BACKGROUND_COLOR);
     //  addToQuadView(background);
    }

    @Override
    public void paintView() {
        Line signalBottom = new Line(10, 70, 30, 70);
        signalBottom.setStrokeWidth(2);
        addToQuadView(signalBottom);

        Line signalStand = new Line(20, 70, 20, 60);
        signalStand.setStrokeWidth(2);
        addToQuadView(signalStand);

        Circle firstSignalBorder = new Circle(20, 55, 10);
        addToQuadView(firstSignalBorder);

        firstLampElement = new Circle(20, 55, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstLampElement);

        Circle secondSignalBorder = new Circle(20, 45, 10);
        addToQuadView(secondSignalBorder);

        secondLampElement = new Circle(20, 55, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(secondLampElement);
    }

    @Override
    public void refresh() {
        updateSignalView();
    }
}

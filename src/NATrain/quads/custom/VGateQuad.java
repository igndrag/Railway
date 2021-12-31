package NATrain.quads.custom;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VGateQuad extends GateQuad {
    public VGateQuad(int x, int y) {
        super(x, y);
        quadType = QuadType.V_GATE_QUAD;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(35, 0, 20, 80);
        firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        leftDoorOpenedElement = new Rectangle(15, 20, 10 , 30);
        leftDoorOpenedElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(leftDoorOpenedElement);

        rightDoorOpenedElement = new Rectangle(65, 20, 10 , 30);
        rightDoorOpenedElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(rightDoorOpenedElement);

        leftDoorClosedElement = new Rectangle(15, 40, 30 , 10);
        leftDoorClosedElement.setFill(Color.BROWN);
        leftDoorClosedElement.setVisible(false);
        addToQuadView(leftDoorClosedElement);

        rightDoorClosedElement = new Rectangle(45, 40, 30 , 10);
        rightDoorClosedElement.setFill(Color.BROWN);
        rightDoorClosedElement.setVisible(false);
        addToQuadView(rightDoorClosedElement);

        /*
        leftDoorOpenedElement = new Rectangle(20, 10, 30 , 10);
        leftDoorOpenedElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(leftDoorOpenedElement);
        rightDoorOpenedElement = new Rectangle(20, 60, 30 , 10);
        rightDoorOpenedElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        addToQuadView(rightDoorOpenedElement);
        leftDoorClosedElement = new Rectangle(40, 10, 10 , 30);
        leftDoorClosedElement.setFill(Color.BLACK);
        addToQuadView(leftDoorClosedElement);
        rightDoorClosedElement = new Rectangle(40, 40, 10 , 30);
        rightDoorClosedElement.setFill(Color.GREEN);
        addToQuadView(rightDoorClosedElement);

        */
    }
}

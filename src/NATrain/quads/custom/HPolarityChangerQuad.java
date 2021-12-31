package NATrain.quads.custom;

import NATrain.quads.QuadType;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class HPolarityChangerQuad extends PolarityChangerQuad {
    public HPolarityChangerQuad(int x, int y) {
        super(x, y);
        quadType = QuadType.HPCQ;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(0, 30, 90 , 20);
        firstTrackElement.setFill(UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
        leftRailMinusElement = new Rectangle(30, 10 , 30 , 10);
        leftRailMinusElement.setFill(UNDEFINED_ELEMENT_COLOR);
        addToQuadView(leftRailMinusElement);
        leftRailPlusElement = new Polygon(30, 10,
                                                40, 10,
                                                40, 0,
                                                50, 0,
                                                50, 10,
                                                60, 10,
                                                60, 20,
                                                50, 20,
                                                50, 30,
                                                40,30,
                                                40, 20,
                                                30,20,
                                                30, 10);
        leftRailPlusElement.setFill(UNDEFINED_ELEMENT_COLOR);
        leftRailPlusElement.setVisible(false);
        addToQuadView(leftRailPlusElement);

        rightRailMinusElement = new Rectangle(30, 60, 30, 10);
        rightRailMinusElement.setFill(UNDEFINED_ELEMENT_COLOR);
        rightRailMinusElement.setVisible(false);
        addToQuadView(rightRailMinusElement);

        rightRailPlusElement = new Polygon(30, 60,
                                                40, 60,
                                                40, 50,
                                                50, 50,
                                                50, 60,
                                                60, 60,
                                                60, 70,
                                                50, 70,
                                                50, 80,
                                                40,80,
                                                40, 70,
                                                30,70,
                                                30, 60);
        rightRailPlusElement.setFill(UNDEFINED_ELEMENT_COLOR);
        addToQuadView(rightRailPlusElement);
    }
}

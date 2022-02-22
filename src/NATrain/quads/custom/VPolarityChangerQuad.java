package NATrain.quads.custom;

import NATrain.quads.QuadType;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class VPolarityChangerQuad extends PolarityChangerQuad {
    public VPolarityChangerQuad(int x, int y) {
        super(x, y);
        quadType = QuadType.VPCQ;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(35, 0, 20, 80);
        firstTrackElement.setFill(UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
        leftRailMinusElement = new Rectangle(0, 30 , 30 , 10);
        leftRailMinusElement.setFill(UNDEFINED_ELEMENT_COLOR);
        addToQuadView(leftRailMinusElement);
        leftRailPlusElement = new Polygon(0, 30,
                                                10, 30,
                                                10, 20,
                                                20, 20,
                                                20, 30,
                                                30, 10,
                                                30, 40,
                                                20, 40,
                                                20, 50,
                                                10,50,
                                                10, 40,
                                                0, 40,
                                                0, 30);
        leftRailPlusElement.setFill(UNDEFINED_ELEMENT_COLOR);
        leftRailPlusElement.setVisible(false);
        addToQuadView(leftRailPlusElement);

        rightRailMinusElement = new Rectangle(60, 30, 30, 10);
        rightRailMinusElement.setFill(UNDEFINED_ELEMENT_COLOR);
        rightRailMinusElement.setVisible(false);
        addToQuadView(rightRailMinusElement);

        rightRailPlusElement = new Polygon(60, 30,
                                                70, 30,
                                                70, 20,
                                                80, 20,
                                                80, 30,
                                                90, 30,
                                                90, 40,
                                                80, 40,
                                                80, 50,
                                                70, 50,
                                                70, 40,
                                                60, 40,
                                                60, 30);
        rightRailPlusElement.setFill(UNDEFINED_ELEMENT_COLOR);
        addToQuadView(rightRailPlusElement);
    }
}

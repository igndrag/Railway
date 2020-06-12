package NATrain.utils;

import NATrain.library.QuadType;
import NATrain.quads.Quad;
import NATrain.quads.QuadImpl;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


public class QuadPainter {

    public static Shape getQuadBoarder() {
        Polyline border = new Polyline();
        border.getPoints().addAll(
                0.0, 80.0,
                90.0, 80.0,
                90.0, 0.0,
                0.0, 0.0,
                0.0, 80.0);
        return border;
    }

    protected static void paintQuadViewForType(QuadImpl quad, QuadType quadType) {
        Shape trackOneElement;
        Shape trackTwoElement;
        Shape signalLampOneElement;
        Shape signalLampTwoElement;
        Shape switchPlusElement;
        Shape switchMinusElement;

        switch (quadType) {
            case STQ1_1:
                trackOneElement = new Rectangle(0, 30, 90 , 20);
                trackOneElement.setFill(Color.BLUE);
                quad.setTrackOneElement(trackOneElement);
                addElementToQuadView(quad, trackOneElement);
                break;
            case STQ1_2:
                trackOneElement = new Rectangle(35, 0, 20 , 80);
                trackOneElement.setFill(Color.BLUE);
                quad.setTrackOneElement(trackOneElement);
                addElementToQuadView(quad, trackOneElement);
                break;
            case STQ2_1:
                trackOneElement = new Polygon(
                        15, 0,
                                35 , 0,
                                75, 80,
                                55, 80,
                                15, 0);
                trackOneElement.setFill(Color.BLUE);
                quad.setTrackOneElement(trackOneElement);
                addElementToQuadView(quad, trackOneElement);
                break;
            case STQ2_2:
                trackOneElement = new Polygon(
                        55, 0,
                                75 , 0,
                                35, 80,
                                15, 80,
                                55, 0);
                trackOneElement.setFill(Color.BLUE);
                quad.setTrackOneElement(trackOneElement);
                addElementToQuadView(quad, trackOneElement);
                break;
        }

    }

    private static void addElementToQuadView (Quad quad, Shape element) {
        quad.getView().getChildren().add(element);
    }

}


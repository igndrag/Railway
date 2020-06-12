package NATrain.utils;

import NATrain.library.QuadType;
import NATrain.quads.QuadImpl;
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
        switch (quadType) {
            case STQ1_1:
                Shape trackOneElement = new Rectangle(0, 30, 90 , 20);
                trackOneElement.setFill(Color.BLUE);
                quad.setTrackOneElement(trackOneElement);
                quad.getView().getChildren().add(trackOneElement);
                //quad.refresh();
                break;
        }

    }

}


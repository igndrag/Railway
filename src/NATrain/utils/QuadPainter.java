package NATrain.utils;

import NATrain.quads.QuadType;
import NATrain.quads.Quad;
import NATrain.quads.BaseQuad;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


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

    protected static void paintQuadViewForType(BaseQuad quad, QuadType quadType) {
        Shape firstTrackElement;
        Shape secondTrackElement;
        Shape firstLampElement;
        Shape secondLampElement;
        Shape switchPlusElement;
        Shape switchMinusElement;
        Shape borderElement;
        Text trackLabel;
        Text signalLabel;
        Text switchLabel;

        switch (quadType) {
            case STQ1_1:
                firstTrackElement = new Rectangle(0, 30, 90 , 20);
                firstTrackElement.setFill(Color.BLUE);
                quad.setFirstTrackElement(firstTrackElement);
                addElementToQuadView(quad, firstTrackElement);
                trackLabel = new Text();
                trackLabel.setTextAlignment(TextAlignment.CENTER);
                trackLabel.setWrappingWidth(90);
                trackLabel.setX(0);
                trackLabel.setY(25);
                quad.setTrackLabel(trackLabel);
                addElementToQuadView(quad,trackLabel);
                break;
            case STQ1_2:
                firstTrackElement = new Rectangle(35, 0, 20 , 80);
                firstTrackElement.setFill(Color.BLUE);
                quad.setFirstTrackElement(firstTrackElement);
                addElementToQuadView(quad, firstTrackElement);
                trackLabel = new Text();
                trackLabel.setTextAlignment(TextAlignment.LEFT);
                trackLabel.setWrappingWidth(30);
                trackLabel.setX(60);
                trackLabel.setY(40);
                quad.setTrackLabel(trackLabel);
                addElementToQuadView(quad,trackLabel);
                break;
            case STQ2_1:
                firstTrackElement = new Polygon(
                        15, 0,
                                35 , 0,
                                75, 80,
                                55, 80,
                                15, 0);
                firstTrackElement.setFill(Color.BLUE);
                quad.setFirstTrackElement(firstTrackElement);
                addElementToQuadView(quad, firstTrackElement);

                trackLabel = new Text();
                trackLabel.setTextAlignment(TextAlignment.LEFT);
                trackLabel.setWrappingWidth(40);
                trackLabel.setX(50);
                trackLabel.setY(20);
                quad.setTrackLabel(trackLabel);
                addElementToQuadView(quad,trackLabel);
                break;
            case STQ2_2:
                firstTrackElement = new Polygon(
                        55, 0,
                                75 , 0,
                                35, 80,
                                15, 80,
                                55, 0);
                firstTrackElement.setFill(Color.BLUE);
                quad.setFirstTrackElement(firstTrackElement);
                addElementToQuadView(quad, firstTrackElement);

                trackLabel = new Text();
                trackLabel.setTextAlignment(TextAlignment.RIGHT);
                trackLabel.setWrappingWidth(40);
                trackLabel.setX(0);
                trackLabel.setY(20);
                quad.setTrackLabel(trackLabel);
                addElementToQuadView(quad,trackLabel);
                break;

            case STQ5_1:
                firstTrackElement = new Polygon(
                        0, 30,
                        30, 30,
                        55, 80,
                        35, 80,
                        20, 50,
                        0, 50,
                        30, 30,
                        0, 30
                        );
                firstTrackElement.setFill(Color.BLUE);
                quad.setFirstTrackElement(firstTrackElement);
                addElementToQuadView(quad, firstTrackElement);
                borderElement = new Polygon(
                    30, 70,
                            50, 70,
                            55, 80,
                            35, 80,
                            30, 70
                    );
                borderElement.setFill(Color.WHITE);
                borderElement.setVisible(false);
                quad.setBorderElement(borderElement);
                addElementToQuadView(quad, borderElement);
                trackLabel = new Text();
                trackLabel.setTextAlignment(TextAlignment.LEFT);
                trackLabel.setWrappingWidth(80);
                trackLabel.setX(0);
                trackLabel.setY(10);
                quad.setTrackLabel(trackLabel);
                addElementToQuadView(quad,trackLabel);
                break;
        }

    }

    private static void addElementToQuadView (Quad quad, Shape element) {
        quad.getView().getChildren().add(element);
    }

}


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
                firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
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
                firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
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
                firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
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
                firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
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
                        0, 30
                        );
                firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
                quad.setFirstTrackElement(firstTrackElement);
                addElementToQuadView(quad, firstTrackElement);
                borderElement = new Polygon(
                    30, 70,
                            50, 70,
                            55, 80,
                            35, 80,
                            30, 70
                    );
                borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
                borderElement.setVisible(false);
                quad.setBorderElement(borderElement);
                addElementToQuadView(quad, borderElement);
                trackLabel = new Text();
                trackLabel.setTextAlignment(TextAlignment.LEFT);
                trackLabel.setWrappingWidth(80);
                trackLabel.setX(10);
                trackLabel.setY(20);
                quad.setTrackLabel(trackLabel);
                addElementToQuadView(quad,trackLabel);
                break;

            case DTQ1_1:
                firstTrackElement = new Rectangle(0, 30, 35 , 20);
                firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
                quad.setFirstTrackElement(firstTrackElement);
                addElementToQuadView(quad, firstTrackElement);

                secondTrackElement = new Rectangle(55, 30, 35 , 20);
                secondTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
                quad.setSecondTrackElement(secondTrackElement);
                addElementToQuadView(quad, secondTrackElement);

                borderElement = new Rectangle(35, 30, 20, 20);
                borderElement.setFill(BaseQuad.DEFAULT_BACKGROUND_COLOR);
                quad.setBorderElement(borderElement);
                addElementToQuadView(quad, borderElement);
                break;

            case SIQ1_1:
                firstTrackElement = new Rectangle(0, 30 , 10, 20);
                firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
                quad.setFirstTrackElement(firstTrackElement);
                addElementToQuadView(quad, firstTrackElement);

                secondTrackElement = new Rectangle(30, 30, 60, 20);
                secondTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
                quad.setSecondTrackElement(secondTrackElement);
                addElementToQuadView(quad, secondTrackElement);

                Line signalBottom = new Line(10, 55, 10, 75);
                signalBottom.setStrokeWidth(2);
                addElementToQuadView(quad, signalBottom);
                addElementToQuadView(quad, new Circle(20, 65, 10, Color.BLACK));

                firstLampElement = new Circle(20, 65, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
                quad.setFirstLampElement(firstLampElement);
                addElementToQuadView(quad, firstLampElement);

                signalLabel = new Text();
                signalLabel.setTextAlignment(TextAlignment.LEFT);
                signalLabel.setWrappingWidth(50);
                signalLabel.setX(35);
                signalLabel.setY(70);
                quad.setSignalLabel(signalLabel);
                addElementToQuadView(quad,signalLabel);
                break;

            case SWQ1_1:Q1_1:
                firstTrackElement = new Polygon();
                firstTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
                quad.setFirstTrackElement(firstTrackElement);
                addElementToQuadView(quad, firstTrackElement);

                secondTrackElement = new Rectangle(30, 30, 60, 20);
                secondTrackElement.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
                quad.setSecondTrackElement(secondTrackElement);
                addElementToQuadView(quad, secondTrackElement);

                Line signalBottom = new Line(10, 55, 10, 75);
                signalBottom.setStrokeWidth(2);
                addElementToQuadView(quad, signalBottom);
                addElementToQuadView(quad, new Circle(20, 65, 10, Color.BLACK));

                firstLampElement = new Circle(20, 65, 8, BaseQuad.UNDEFINED_ELEMENT_COLOR);
                quad.setFirstLampElement(firstLampElement);
                addElementToQuadView(quad, firstLampElement);

                signalLabel = new Text();
                signalLabel.setTextAlignment(TextAlignment.LEFT);
                signalLabel.setWrappingWidth(50);
                signalLabel.setX(35);
                signalLabel.setY(70);
                quad.setSignalLabel(signalLabel);
                addElementToQuadView(quad,signalLabel);
                break;
        }



    }

    private static void addElementToQuadView (Quad quad, Shape element) {
        quad.getView().getChildren().add(element);
    }

}


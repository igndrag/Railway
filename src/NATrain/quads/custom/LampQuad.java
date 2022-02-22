package NATrain.quads.custom;

import NATrain.quads.BaseQuad;
import NATrain.quads.GlobalQuadType;
import NATrain.quads.QuadType;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.customObjects.OnOffObject;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class LampQuad extends OnOffObjectQuad {

    private Group lampRays;
    private Circle lampBulb;
    private Rectangle lampBottom;

    public LampQuad(int x, int y) {
        super(x, y);
        quadType = QuadType.LMPQ;
        paintView();
    }


    @Override
    public void activateListeners() {
        super.activateListeners();
        if (onOffObject != null) {
            PropertyChangeListener lampListener = new OnOffObjectListener();
            quadListeners.put(onOffObject, lampListener);
            onOffObject.addPropertyChangeListener(lampListener);
        }
    }

    private void updateLampView() {
        if (onOffObject != null) {
            if (onOffObject.getState() == OnOffState.ON) {
                lampRays.setVisible(true);
            } else {
                lampRays.setVisible(false);
            }
        }
    }

    @Override
    public void refresh() {
        updateLampView();
    }

    @Override
    public void paintView() {
        lampRays = new Group();
        descriptionLabel = new Text();
        descriptionLabel.setText("Lamp ID");
        descriptionLabel.setY(75);
        quadView.getChildren().add(descriptionLabel);
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);
        descriptionLabel.setWrappingWidth(90);
        Polygon lampBottomBoard = new Polygon(35,40,
                35, 58,
                37, 60,
                53, 60,
                55, 58,
                55, 40,
                35, 40);
        quadView.getChildren().add(lampBottomBoard);
        Circle lampBorder = new Circle();
        lampBorder.setCenterX(45);
        lampBorder.setCenterY(30);
        lampBorder.setRadius(20);
        quadView.getChildren().add(lampBorder);
        lampBulb = new Circle();
        lampBulb.setFill(BaseQuad.UNDEFINED_ELEMENT_COLOR);
        lampBulb.setCenterX(45);
        lampBulb.setCenterY(30);
        lampBulb.setRadius(18);
        quadView.getChildren().add(lampBulb);
        lampBottom = new Rectangle(37, 46, 16, 12);
        lampBottom.setFill(Color.GRAY);
        quadView.getChildren().add(lampBottom);

        Line ray1 = new Line(10, 10 ,20,20);
        ray1.setStrokeWidth(2);
        lampRays.getChildren().add(ray1);
        Line ray2 = new Line(10, 30, 20, 30);
        ray2.setStrokeWidth(2);
        lampRays.getChildren().add(ray2);
        Line ray3 = new Line(10, 50, 20, 40);
        ray3.setStrokeWidth(2);
        lampRays.getChildren().add(ray3);
        Line ray4 = new Line(80, 10, 70, 20);
        ray4.setStrokeWidth(2);
        lampRays.getChildren().add(ray4);
        Line ray5 = new Line(70, 30, 80, 30);
        ray5.setStrokeWidth(2);
        lampRays.getChildren().add(ray5);
        Line ray6 = new Line(70, 40, 80, 50);
        ray6.setStrokeWidth(2);
        lampRays.getChildren().add(ray6);
        lampRays.setVisible(false);
        quadView.getChildren().add(lampRays);
    }
}
     

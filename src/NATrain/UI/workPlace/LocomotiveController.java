package NATrain.UI.workPlace;

import NATrain.quads.LocomotivePanelQuad;
import NATrain.trackSideObjects.locomotives.Locomotive;
import NATrain.сontrolModules.MQTTLocomotiveModule;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import org.w3c.dom.html.HTMLOptGroupElement;

public class LocomotiveController {

    @FXML
    private Label directionLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Button autopilotToggleButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Pane previewPane;
    @FXML
    private Slider speedSlider;
    @FXML
    private ToggleButton forwardToggleButton;
    @FXML
    private ToggleButton backwardToggleButton;
    @FXML
    private ToggleButton frontLightToggleButton;
    @FXML
    private ToggleButton rearLightToggleButton;
    @FXML
    private Button runButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button hornButton;

    private Locomotive locomotive;
    private LocomotivePanelQuad quad;
    private double actualSliderValue = 0;

    public void init(Locomotive locomotive) {
        this.quad = new LocomotivePanelQuad();
        this.locomotive = locomotive;
        ToggleGroup toggleGroup = new ToggleGroup();
        forwardToggleButton.setToggleGroup(toggleGroup);
        backwardToggleButton.setToggleGroup(toggleGroup);
        forwardToggleButton.setSelected(true);
        idLabel.setText(locomotive.getId());
        statusLabel.setText(locomotive.getActualState().getDescription());
        if (locomotive.mainLight) {
            frontLightToggleButton.setSelected(true);
        }
        if (locomotive.rearLight) {
            rearLightToggleButton.setSelected(true);
        }
        previewPane.getChildren().add(quad.getView());
        speedSlider.setMax(8);
        speedSlider.setMin(0);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);
        speedSlider.setBlockIncrement(0.2);
        speedSlider.setMajorTickUnit(1.0);
        speedSlider.setMinorTickCount(5);
        speedSlider.setSnapToTicks(true);
        speedSlider.setOnMouseReleased(event -> {
            sendSpeedChangeCommand();
        });
        speedSlider.setOnKeyReleased(event -> {
            sendSpeedChangeCommand();
        });
        stopButton.setOnAction(event -> {
            locomotive.getModule().sendCommand(MQTTLocomotiveModule.STOP_CHANNEL, "0");
        });
        forwardToggleButton.setOnAction(event -> {
            locomotive.getModule().sendCommand(MQTTLocomotiveModule.SET_MOVE_DIRECTION_CHANNEL, MQTTLocomotiveModule.FORWARD_COMMAND_CODE + "");
        });
        backwardToggleButton.setOnAction(event -> {
            locomotive.getModule().sendCommand(MQTTLocomotiveModule.SET_MOVE_DIRECTION_CHANNEL, MQTTLocomotiveModule.BACKWARD_COMMAND_CODE + "");
        });
    }


    private void sendSpeedChangeCommand() {
        if (actualSliderValue != speedSlider.getValue()) {
            actualSliderValue = speedSlider.getValue();
            int speed = (int) (speedSlider.getValue() * 128);
            locomotive.getModule().sendCommand(MQTTLocomotiveModule.SET_SPEED_CHANNEL, String.format("%04d", speed));
        }
    }



}



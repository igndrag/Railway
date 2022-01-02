package NATrain.UI.tracksideObjectRedactor;

import NATrain.trackSideObjects.customObjects.CustomObjectType;
import NATrain.trackSideObjects.customObjects.Gate;
import NATrain.trackSideObjects.customObjects.Servo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomObjectSelectorController {

    @FXML
    private Button createButton;
    @FXML
    private ToggleButton servoToggleButton;
    @FXML
    private ToggleButton gatesToggleButton;
    @FXML
    private ToggleButton switchPolarityChangerToggleButton;
    @FXML
    private ToggleButton sectionPolarityChangerToggleButton;
    @FXML
    private ToggleButton pwmSliderToggleButton;

    CustomObjectType objectType = CustomObjectType.SERVO;
    TracksideObjectNavigatorController parentController;

    public void init(TracksideObjectNavigatorController parentController) {
        this.parentController = parentController;

        ToggleGroup toggleGroup = new ToggleGroup();
        servoToggleButton.setToggleGroup(toggleGroup);
        gatesToggleButton.setToggleGroup(toggleGroup);
        switchPolarityChangerToggleButton.setToggleGroup(toggleGroup);
        sectionPolarityChangerToggleButton.setToggleGroup(toggleGroup);
        pwmSliderToggleButton.setToggleGroup(toggleGroup);
        servoToggleButton.setSelected(true);

        servoToggleButton.setOnAction(event -> {
            if(servoToggleButton.isSelected()) {
                objectType = CustomObjectType.SERVO;
            }
        });

        gatesToggleButton.setOnAction(event -> {
            if (gatesToggleButton.isSelected()) {
                objectType = CustomObjectType.GATE;
            }
        });

        switchPolarityChangerToggleButton.setOnAction(event -> {
            if (switchPolarityChangerToggleButton.isSelected()) {
                objectType = CustomObjectType.SWITCH_POLARITY_CHANGER;
            }
        });

        sectionPolarityChangerToggleButton.setOnAction(event -> {
            if (sectionPolarityChangerToggleButton.isSelected()) {
                objectType =CustomObjectType.SECTION_POLARITY_CHANGER;
            }
        });

        pwmSliderToggleButton.setOnAction(event -> {
            if (pwmSliderToggleButton.isSelected()) {
                objectType= CustomObjectType.PWM_SLIDER;
            }
        });

        createButton.setOnAction(event -> createCustomObject());
    }

    public void createCustomObject() {
        Stage thisStage = (Stage) createButton.getScene().getWindow();
        thisStage.close();
        switch (objectType) {
            case SERVO:
                try {
                    parentController.toServoRedactor(new Servo(Servo.INITIAL_SERVO_NAME), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case GATE:
                try {
                    parentController.toGateRedactor(new Gate(Gate.INITIAL_GATE_NAME), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }

    }

}

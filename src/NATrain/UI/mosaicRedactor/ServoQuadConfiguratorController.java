package NATrain.UI.mosaicRedactor;

import NATrain.connectionService.MQTTConnectionService;
import NATrain.model.Model;
import NATrain.quads.custom.ServoQuad;
import NATrain.trackSideObjects.customObjects.Servo;
import NATrain.сontrolModules.AbstractModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServoQuadConfiguratorController {

    @FXML
    private Slider positionOneSlider;
    @FXML
    private Slider positionTwoSlider;
    @FXML
    private Slider speedSlider;
    @FXML
    private TextField positionOneTextField;
    @FXML
    private ChoiceBox<Servo> servoChoiceBox;
    @FXML
    private TextField positionTwoTextField;
    @FXML
    private TextField speedTextField;
    @FXML
    private Button positionOneSetButton;
    @FXML
    private Button positionTwoSetButton;
    @FXML
    private Button initialPositionSetButton;
    @FXML
    private Button saveButton;

    ServoQuad quad;

    public void init(Servo servo, ServoQuad quad) {
        MQTTConnectionService.connect();
        this.quad = quad;

        ObservableList<Servo> servos = FXCollections.observableArrayList(Model.getServos());

        servoChoiceBox.setItems(servos);

        servoChoiceBox.setOnAction(event -> {
            initFields(servoChoiceBox.getValue());
        });

        speedSlider.setMax(10);
        speedSlider.setMin(0);
        speedSlider.setShowTickLabels(true);
        speedSlider.setBlockIncrement(1.0);
        speedSlider.setMajorTickUnit(1.0);
        speedSlider.setMinorTickCount(0);
        speedSlider.setSnapToTicks(true);
        speedSlider.setOnMouseReleased(event -> {
            speedTextField.setText(String.valueOf((int)speedSlider.getValue()));
        });
        speedSlider.setOnKeyReleased(event -> {
            speedTextField.setText(String.valueOf((int)speedSlider.getValue()));
        });


        positionOneSlider.setMax(180);
        positionOneSlider.setMin(0);
        positionOneSlider.setShowTickLabels(true);
      //  positionOneSlider.setBlockIncrement(5.0);
        positionOneSlider.setMajorTickUnit(90.0);
        positionOneSlider.setMinorTickCount(44);
        positionOneSlider.setSnapToTicks(true);
        positionOneSlider.setOnMouseReleased(event -> {
            positionOneTextField.setText(String.valueOf((int)positionOneSlider.getValue()));
        });
        positionOneSlider.setOnKeyReleased(event -> {
            positionOneTextField.setText(String.valueOf((int)positionOneSlider.getValue()));
        });

        positionOneSetButton.setOnAction(event -> {
            if (servoChoiceBox.getValue() != Servo.EMPTY_SERVO) {
                servoChoiceBox.getValue().getOutputChannel().sendCommandWithValue(AbstractModule.SET_SERVO_POSITION_COMMAND_CODE,(int) positionOneSlider.getValue());
            }
        });

        initFields(servo);
    }

    private void initFields(Servo servo) {
        if (servo != null && servo != Servo.EMPTY_SERVO) {
            servoChoiceBox.getSelectionModel().select(servo);
            speedSlider.setValue(servo.getSpeed());
            positionOneSlider.setValue(servo.getPositionOne());
            positionTwoSlider.setValue(servo.getPositionTwo());
            speedTextField.setText(String.valueOf(servo.getSpeed()));
            positionOneTextField.setText(String.valueOf(servo.getPositionOne()));
            positionTwoTextField.setText(String.valueOf(servo.getPositionTwo()));
        } else {
            servoChoiceBox.getSelectionModel().select(Servo.EMPTY_SERVO);
            speedSlider.setValue(0);
            positionOneSlider.setValue(0);
            positionTwoSlider.setValue(0);
            positionTwoTextField.clear();
            positionTwoTextField.clear();
        }
    }

    private void saveAndClose() {
        Servo newServo = servoChoiceBox.getValue();
        if (servoChoiceBox.getValue() != Servo.EMPTY_SERVO) {
            quad.setServo(newServo);
            newServo.setPositionOne((int) positionOneSlider.getValue());
            newServo.setPositionTwo((int) positionTwoSlider.getValue());
            newServo.setSpeed((int) speedSlider.getValue());
        }
        MQTTConnectionService.disconnect();
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

}

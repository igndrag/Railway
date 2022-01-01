package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.connectionService.MQTTConnectionService;
import NATrain.model.Model;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.customObjects.Servo;
import NATrain.trackSideObjects.movableObjects.Wagon;
import NATrain.сontrolModules.AbstractModule;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ServoRedactorController extends TracksideObjectRedactorController {

    @FXML
    private Slider positionOneSlider;
    @FXML
    private Slider positionTwoSlider;
    @FXML
    private Slider speedSlider;
    @FXML
    private TextField textField;
    @FXML
    private TextField positionOneTextField;
    @FXML
    private TextField positionTwoTextField;
    @FXML
    private TextField speedTextField;
    @FXML
    private Button positionOneSetButton;
    @FXML
    private Button positionTwoSetButton;
    @FXML
    private Button saveButton;


    private Servo servo;

    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        this.servo = (Servo) tracksideObject;
        textField.setText(servo.getId());
        this.tableView = tableView;
        this.observableList = observableList;
        this.initialName = servo.getId();
        MQTTConnectionService.connect();

        if (!servo.getId().equals(Servo.INITIAL_SERVO_NAME)) {
            textField.setDisable(true);
        } else {
            textField.setOnMouseClicked(event -> {
                textField.selectAll();
            });
        }

        positionOneTextField.setDisable(true);
        positionTwoTextField.setDisable(true);
        speedTextField.setDisable(true);

        speedSlider.setMax(10);
        speedSlider.setMin(0);
        speedSlider.setShowTickLabels(true);
        speedSlider.setBlockIncrement(1.0);
        speedSlider.setMajorTickUnit(1.0);
        speedSlider.setMinorTickCount(0);
        speedSlider.setSnapToTicks(true);
        speedSlider.setOnMouseReleased(event -> {
            speedTextField.setText(String.valueOf((int) speedSlider.getValue()));
        });
        speedSlider.setOnKeyReleased(event -> {
            speedTextField.setText(String.valueOf((int) speedSlider.getValue()));
        });

        positionOneSlider.setMax(180);
        positionOneSlider.setMin(0);
        positionOneSlider.setShowTickLabels(true);
        positionOneSlider.setMajorTickUnit(30.0);
        positionOneSlider.setMinorTickCount(29);
        positionOneSlider.setBlockIncrement(1.0);
        positionOneSlider.setSnapToTicks(true);
        positionOneSlider.setOnMouseReleased(event -> {
            positionOneTextField.setText(String.valueOf((int) positionOneSlider.getValue()));
        });
        positionOneSlider.setOnKeyReleased(event -> {
            positionOneTextField.setText(String.valueOf((int) positionOneSlider.getValue()));
        });

        positionOneSetButton.setOnAction(event -> {
            if (servo != Servo.EMPTY_SERVO) {
                servo.getOutputChannel().sendCommandWithValue(AbstractModule.SET_SERVO_POSITION_COMMAND_CODE, (int) positionOneSlider.getValue());
            }
        });

        positionTwoSlider.setMax(180);
        positionTwoSlider.setMin(0);
        positionTwoSlider.setShowTickLabels(true);
        positionTwoSlider.setMajorTickUnit(30.0);
        positionTwoSlider.setMinorTickCount(29);
        positionTwoSlider.setBlockIncrement(1.0);
        positionTwoSlider.setSnapToTicks(true);
        positionTwoSlider.setOnMouseReleased(event -> {
            positionTwoTextField.setText(String.valueOf((int) positionTwoSlider.getValue()));
        });
        positionTwoSlider.setOnKeyReleased(event -> {
            positionTwoTextField.setText(String.valueOf((int) positionTwoSlider.getValue()));
        });

        positionTwoSetButton.setOnAction(event -> {
            if (servo != Servo.EMPTY_SERVO) {
                servo.getOutputChannel().sendCommandWithValue(AbstractModule.SET_SERVO_POSITION_COMMAND_CODE, (int) positionTwoSlider.getValue());
            }
        });

        saveButton.setOnAction(this::saveAndClose);

        initFields(servo);
    }

    private void initFields(Servo servo) {
        if (servo != null && servo != Servo.EMPTY_SERVO) {
            speedSlider.setValue(servo.getSpeed());
            positionOneSlider.setValue(servo.getPositionOne());
            positionTwoSlider.setValue(servo.getPositionTwo());
            speedTextField.setText(String.valueOf(servo.getSpeed()));
            positionOneTextField.setText(String.valueOf(servo.getPositionOne()));
            positionTwoTextField.setText(String.valueOf(servo.getPositionTwo()));
        }
    }

    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        if (!isNameValid(Model.getServos(), Servo.INITIAL_SERVO_NAME)) {
            return;
        }
        if (MQTTConnectionService.getClient().isConnected()) {
            MQTTConnectionService.disconnect();
        }
        servo.setId(textField.getText());
        servo.setPositionOne((int)positionOneSlider.getValue());
        servo.setPositionTwo((int)positionTwoSlider.getValue());
        updateModelAndClose(Model.getServos(), servo);
    }
}

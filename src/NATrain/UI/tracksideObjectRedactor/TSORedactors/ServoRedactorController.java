package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.connectionService.MQTTConnectionService;
import NATrain.model.Model;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.customObjects.OnOffObject;
import NATrain.trackSideObjects.customObjects.Servo;
import NATrain.сontrolModules.ControlModule;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ServoRedactorController extends TracksideObjectRedactorController {

    @FXML
    private Button setOnePositionButton;
    @FXML
    private Button setTwoPositionButton;
    @FXML
    private Slider positionOneSlider;
    @FXML
    private Slider positionTwoSlider;
    @FXML
    private Slider speedSlider;
    @FXML
    private TextField positionOneTextField;
    @FXML
    private TextField positionTwoTextField;
    @FXML
    private Button saveButton;

    protected Servo servo;

    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        this.servo = (Servo) tracksideObject;
        textField.setText(servo.getId());
        this.tableView = tableView;
        this.observableList = observableList;
        this.initialName = servo.getId();

        if (!servo.getId().equals(Servo.INITIAL_SERVO_NAME)) {
            textField.setDisable(true);
        } else {
            textField.setOnMouseClicked(event -> {
                textField.selectAll();
            });
        }

        positionOneSlider.setValue(servo.getPositionOne());

        positionOneSlider.setOnMouseReleased(event -> {
            positionOneTextField.setText(Integer.toString((int)(positionOneSlider.getValue())));
            if (servo.getOutputChannel().getModule() != null && MQTTConnectionService.getClient() != null && MQTTConnectionService.getClient().isConnected()) {
                servo.setServoPosition((int)(positionOneSlider.getValue()));
            }
        });
        positionOneSlider.setOnKeyReleased(event -> {
            positionOneTextField.setText(Integer.toString((int)(positionOneSlider.getValue())));
            if (servo.getOutputChannel().getModule() != null && MQTTConnectionService.getClient() != null && MQTTConnectionService.getClient().isConnected()) {
                servo.setServoPosition((int)(positionOneSlider.getValue()));
            }
        });
        positionOneTextField.setOnAction(event -> {
            positionOneSlider.setValue(Double.parseDouble(positionOneTextField.getText()));
            if (servo.getOutputChannel().getModule() != null && MQTTConnectionService.getClient() != null && MQTTConnectionService.getClient().isConnected()) {
                servo.setServoPosition((int)(positionOneSlider.getValue()));
            }
        });

        positionTwoSlider.setValue(servo.getPositionTwo());

        positionTwoSlider.setOnMouseReleased(event -> {
            positionTwoTextField.setText(Integer.toString((int)(positionTwoSlider.getValue())));
            if (servo.getOutputChannel().getModule() != null && MQTTConnectionService.getClient() != null && MQTTConnectionService.getClient().isConnected()) {
                servo.setServoPosition((int)(positionTwoSlider.getValue()));
            }
        });
        positionTwoSlider.setOnKeyReleased(event -> {
            positionTwoTextField.setText(Integer.toString((int)(positionTwoSlider.getValue())));
            if (servo.getOutputChannel().getModule() != null && MQTTConnectionService.getClient() != null && MQTTConnectionService.getClient().isConnected()) {
                servo.setServoPosition((int)(positionTwoSlider.getValue()));
            }
        });
        positionTwoTextField.setOnAction(event -> {
            positionTwoSlider.setValue(Double.parseDouble(positionTwoTextField.getText()));
            if (servo.getOutputChannel().getModule() != null && MQTTConnectionService.getClient() != null && MQTTConnectionService.getClient().isConnected()) {
                servo.setServoPosition((int)(positionTwoSlider.getValue()));
            }
        });

        speedSlider.setValue(servo.getSpeed());

        positionOneTextField.setText(Integer.toString(servo.getPositionOne()));
        positionTwoTextField.setText(Integer.toString(servo.getPositionTwo()));

        if (servo.getOutputChannel().getModule() != null) {
            MQTTConnectionService.connect();

            if (MQTTConnectionService.getClient().isConnected()) {

                servo.init();

                setOnePositionButton.setDisable(false);
                setTwoPositionButton.setDisable(false);

                setOnePositionButton.setOnAction(event -> {
                    servo.setServoPosition((int)(positionOneSlider.getValue()));
                });

                setTwoPositionButton.setOnAction(event -> {
                    servo.setServoPosition((int)(positionTwoSlider.getValue()));
                });

                speedSlider.setOnMouseReleased(event -> {
                    servo.setSpeed((int)speedSlider.getValue());
                    servo.sendSpeed();
                });
            }
        }

        saveButton.setOnAction(this::saveAndClose);
    }

    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        if (!isNameValid(Model.getGates(), Servo.INITIAL_SERVO_NAME)) {
            return;
        }

        servo.setId(textField.getText());
        servo.setPositionOne((int)positionOneSlider.getValue());
        servo.setPositionTwo((int)positionTwoSlider.getValue());
        servo.setSpeed((int)speedSlider.getValue());

        if (MQTTConnectionService.getClient() != null && MQTTConnectionService.getClient().isConnected()) {
            MQTTConnectionService.disconnect();
        }

        updateModelAndClose(Model.getServos(), servo);
    }
}


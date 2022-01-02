package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.connectionService.MQTTConnectionService;
import NATrain.model.Model;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.customObjects.Gate;
import NATrain.trackSideObjects.customObjects.Servo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;

public class GateRedactorController extends TracksideObjectRedactorController {
    @FXML
    private Button saveButton;
    @FXML
    private ChoiceBox<Servo> leftDoorServoChoiceBox;
    @FXML
    private ChoiceBox<Servo> rightDoorServoChoiceBox;

    private Gate gate;

    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        this.gate = (Gate) tracksideObject;
        textField.setText(gate.getId());
        this.tableView = tableView;
        this.observableList = observableList;
        this.initialName = gate.getId();

        ObservableList<Servo> servos = FXCollections.observableArrayList(Model.getServos().values());
        servos.add(Servo.EMPTY_SERVO);

        if (!gate.getId().equals(Gate.INITIAL_GATE_NAME)) {
            textField.setDisable(true);
        } else {
            textField.setOnMouseClicked(event -> {
                textField.selectAll();
            });
        }

        leftDoorServoChoiceBox.setItems(servos);
        rightDoorServoChoiceBox.setItems(servos);

        if (gate.getFirstServo() == null) {
            leftDoorServoChoiceBox.setValue(Servo.EMPTY_SERVO);
        } else {
            leftDoorServoChoiceBox.setValue(gate.getFirstServo());
        }

        if (gate.getSecondServo() == null) {
            rightDoorServoChoiceBox.setValue(Servo.EMPTY_SERVO);
        } else {
            rightDoorServoChoiceBox.setValue(gate.getSecondServo());
        }

        saveButton.setOnAction(this::saveAndClose);
    }

    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        if (!isNameValid(Model.getGates(), Gate.INITIAL_GATE_NAME)) {
            return;
        }
        gate.setId(textField.getText());

        if (leftDoorServoChoiceBox.getValue() == Servo.EMPTY_SERVO) {
            gate.setFirstServo(null);
        } else {
            gate.setFirstServo(leftDoorServoChoiceBox.getValue());
        }

        if (rightDoorServoChoiceBox.getValue() == Servo.EMPTY_SERVO) {
            gate.setSecondServo(null);
        } else {
            gate.setSecondServo(rightDoorServoChoiceBox.getValue());
        }

        updateModelAndClose(Model.getGates(), gate);
    }
}


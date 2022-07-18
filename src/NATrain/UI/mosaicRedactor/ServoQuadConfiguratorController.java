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
    private ChoiceBox<Servo> servoChoiceBox;
    @FXML
    private Button saveButton;

    ServoQuad quad;

    public void init(Servo servo, ServoQuad quad) {
        MQTTConnectionService.connect();
        this.quad = quad;

        ObservableList<Servo> servos = FXCollections.observableArrayList(Model.getServos().values());
        servos.add(Servo.EMPTY_SERVO);

        servoChoiceBox.setItems(servos);

        if (servo != null) {
            servoChoiceBox.setValue(servo);
        }

        saveButton.setOnAction(event -> saveAndClose());
    }


    private void saveAndClose() {
        Servo newServo = servoChoiceBox.getValue();
        if (servoChoiceBox.getValue() != Servo.EMPTY_SERVO) {
            quad.setServo(newServo);
        } else {
            quad.setServo(null);
        }

        if (MQTTConnectionService.getClient().isConnected()) {
            MQTTConnectionService.disconnect();
        }
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

}

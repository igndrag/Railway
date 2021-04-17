package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.connectionService.MQTTConnectionService;
import NATrain.model.Model;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.locomotives.Autopilot;
import NATrain.trackSideObjects.locomotives.Locomotive;
import NATrain.utils.UtilFunctions;
import NATrain.сontrolModules.MQTTLocomotiveModule;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class LocomotiveRedactorController extends TracksideObjectRedactorController {

    @FXML
    private TextField fullSpeedTextField;
    @FXML
    private Button fullSpeedTestButton;
    @FXML
    private TextField halfSpeedTextField;
    @FXML
    private Button halfSpeedTestButton;
    @FXML
    private Locomotive locomotive;

    @Override
    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        this.locomotive = (Locomotive) tracksideObject;
        textField.setText(locomotive.getId());
        this.tableView = tableView;
        this.observableList = observableList;

        if (locomotive.getControlModule() != null) {
            textField.setDisable(true);
        } else {
            textField.setOnMouseClicked(event -> {
                textField.selectAll();
            });
        }

        fullSpeedTextField.setText(locomotive.getFullSpeed() + "");
        halfSpeedTextField.setText(locomotive.getRestrictedSpeed() + "");

        fullSpeedTestButton.setOnAction(event -> {
            speedTest(Autopilot.FULL_SPEED);
        });

        halfSpeedTestButton.setOnAction(event -> {
            speedTest(Autopilot.RESTRICTED_SPEED);
        });
    }

    @FXML
    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        locomotive.setId(textField.getText());
        if (!isNameValid(Model.getLocomotives(), Locomotive.INITIAL_LOCOMOTIVE_NAME)) {
            return;
        }

        int expectedFullSpeed = UtilFunctions.parseIfPositiveNumeric(fullSpeedTextField.getText());
        if (expectedFullSpeed > 0 ) {
            locomotive.setFullSpeed(expectedFullSpeed);
        }

        int expectedHalfSpeed = UtilFunctions.parseIfPositiveNumeric(halfSpeedTextField.getText());
        if (expectedHalfSpeed > 0) {
            locomotive.setRestrictedSpeed(expectedHalfSpeed);
        }

        locomotive.setModule(new MQTTLocomotiveModule(locomotive.getId() + "_MQTTLocomotiveModule", locomotive));
        updateModelAndClose(Model.getLocomotives(), locomotive);
    }

    private void speedTest(int speed) {
        Timeline speedTester = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            MQTTConnectionService.publish(MQTTLocomotiveModule.getCommandTopicRoot() + "/" + textField.getText(), String.format("%s:%s", MQTTLocomotiveModule.STOP_CHANNEL, "0"));
        }));
        speedTester.setCycleCount(1);
        MQTTConnectionService.publish(MQTTLocomotiveModule.getCommandTopicRoot() + "/" + textField.getText(), String.format("%s:%s", MQTTLocomotiveModule.SET_SPEED_CHANNEL, String.format("%04d", speed)));
        speedTester.play();
    }
}

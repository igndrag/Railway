package NATrain.UI.controlModuleRedactor;

import NATrain.model.Model;
import NATrain.сontrolModules.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CMCreatorController {

    private Stage primaryStage;
    TableView<ControlModule> tableView;
    ObservableList<ControlModule> observableList;

    @FXML
    private ToggleButton signalToggleButton;
    @FXML
    private ToggleButton switchToggleButton;
    @FXML
    private ToggleButton reverseLoopToggleButton;
    @FXML
    private ToggleButton checkConnectionButton;
    @FXML
    private TextField addressTextField;

    ControlModuleType selectedType = ControlModuleType.SIGNAL_MQTT_CONTROLLER;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize(TableView<ControlModule> tableView, ObservableList<ControlModule> observableList) {
        this.tableView = tableView;
        this.observableList = observableList;
        ToggleGroup toggleGroup = new ToggleGroup();
        signalToggleButton.setToggleGroup(toggleGroup);
        switchToggleButton.setToggleGroup(toggleGroup);
        reverseLoopToggleButton.setToggleGroup(toggleGroup);

        signalToggleButton.setSelected(true);

        signalToggleButton.setOnAction(event -> {
            selectedType = ControlModuleType.SIGNAL_MQTT_CONTROLLER;
        });

        switchToggleButton.setOnAction(event -> {
            selectedType = ControlModuleType.SWITCH_MQTT_CONTROLLER;
        });

        reverseLoopToggleButton.setOnAction(event -> {
            selectedType = ControlModuleType.REVERSE_LOOP_MQTT_CONTROLLER;
        });
    }

    private boolean isAddressValid() {
        String address = addressTextField.getText();
        if (address.equals("")) {
            return false;
        }
        for (ControlModule controlModule: Model.getControlModules().values()) {
            if (controlModule.getId().equals(address)) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText(String.format("Control Module with address %s is already exists!", address));
                a.show();
                return false;
            }
        }
        return true;
    }

    @FXML
    private void saveAndClose() {
        if (isAddressValid()) {
            String address = addressTextField.getText();
            ControlModule controlModule = null;
            switch (selectedType) {
                case SIGNAL_MQTT_CONTROLLER:
                    controlModule = new SignalMQTTController(address);
                    break;
                case SWITCH_MQTT_CONTROLLER:
                    controlModule = new SwitchMQTTController(address);
                    break;
                default:
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setContentText("Choose Control Module type!");
                    a.show();
                    return;
            }
            Model.getControlModules().put(addressTextField.getText() ,controlModule);
            observableList.add(controlModule);
            tableView.refresh();
            primaryStage.close();
        }
    }
}

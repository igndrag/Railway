package NATrain.UI.controlModuleRedactor;

import NATrain.model.Model;
import NATrain.сontrolModules.ControlModule;
import NATrain.сontrolModules.ControlModuleType;
import NATrain.сontrolModules.UniversalMQTTModule;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CMCreatorController {

    private Stage primaryStage;
    TableView<ControlModule> tableView;
    ObservableList<ControlModule> observableList;

    @FXML
    private ToggleButton universalMQTTToggleButton;
    @FXML
    private ToggleButton switchMQTTToggleButton;
    @FXML
    private ToggleButton RS485ToggleButton;
    @FXML
    private ToggleButton checkConnectionButton;
    @FXML
    private TextField addressTextField;

    ControlModuleType selectedType = ControlModuleType.UNIVERSAL_MQTT_MODULE;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize(TableView<ControlModule> tableView, ObservableList<ControlModule> observableList) {
        this.tableView = tableView;
        this.observableList = observableList;
        ToggleGroup toggleGroup = new ToggleGroup();
        universalMQTTToggleButton.setToggleGroup(toggleGroup);
        switchMQTTToggleButton.setToggleGroup(toggleGroup);
        RS485ToggleButton.setToggleGroup(toggleGroup);

        universalMQTTToggleButton.setSelected(true);

        universalMQTTToggleButton.setOnAction(event -> {
            selectedType = ControlModuleType.UNIVERSAL_MQTT_MODULE;
        });

        switchMQTTToggleButton.setOnAction(event -> {
            selectedType = ControlModuleType.SWITCH_MQTT_MODULE;
        });

        RS485ToggleButton.setOnAction(event -> {
            selectedType = ControlModuleType.RS485_MODULE;
        });
    }

    private boolean isAddressValid() {
        String address = addressTextField.getText();
        if (address.equals("")) {
            return false;
        }
        for (ControlModule controlModule: Model.getControlModules()) {
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
                case UNIVERSAL_MQTT_MODULE:
                    controlModule = new UniversalMQTTModule(address);
                    break;
                default:
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setContentText("Choose Control Module type!");
                    a.show();
                    return;
            }
            Model.getControlModules().add(controlModule);
            observableList.add(controlModule);
            tableView.refresh();
            primaryStage.close();
        }
    }
}

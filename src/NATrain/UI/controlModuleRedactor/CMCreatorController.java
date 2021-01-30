package NATrain.UI.controlModuleRedactor;

import NATrain.model.Model;
import NATrain.remoteControlModules.RemoteControlModule;
import NATrain.remoteControlModules.SignalControlModule;
import NATrain.remoteControlModules.SwitchControlModule;
import NATrain.remoteControlModules.TrackControlModule;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CMCreatorController {

    private Stage primaryStage;

    TableView<RemoteControlModule> tableView;
    ObservableList<RemoteControlModule> observableList;

    @FXML
    private ToggleButton signalToggleButton;
    @FXML
    private ToggleButton switchToggleButton;
    @FXML
    private ToggleButton trackToggleButton;
    @FXML
    private ToggleButton checkConnectionButton;
    @FXML
    private TextField addressTextField;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize(TableView<RemoteControlModule> tableView, ObservableList<RemoteControlModule> observableList) {
        this.tableView = tableView;
        this.observableList = observableList;
        ToggleGroup toggleGroup = new ToggleGroup();
        signalToggleButton.setToggleGroup(toggleGroup);
        switchToggleButton.setToggleGroup(toggleGroup);
        trackToggleButton.setToggleGroup(toggleGroup);
    }

    private boolean isAddressValid() {
        String address = addressTextField.getText();
        if (isNumeric(address) && !address.equals("")) {
            Integer adrNum = Integer.parseInt(address);
            if (Model.getRemoteControlModules().containsKey(adrNum)) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText(String.format("Control Module with address %s is already exists!", Integer.parseInt(address)));
                a.show();
                return false;
            } else if (0 < adrNum && adrNum < 256) {
                return true;
            }
        }
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setContentText("Please input number from 1 to 256.");
        a.show();
        return false;
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    private void saveAndClose() {
        if (isAddressValid()) {
            int address = Integer.parseInt(addressTextField.getText());
            RemoteControlModule controlModule = null;
            if (trackToggleButton.isSelected())
                controlModule = new TrackControlModule(address);
            else if (switchToggleButton.isSelected())
                controlModule = new SwitchControlModule(address);
            else if (signalToggleButton.isSelected())
                controlModule = new SignalControlModule(address);
            else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Change Control Module type!");
                a.show();
                return;
            }
            Model.getRemoteControlModules().put(address, controlModule);
            observableList.add(controlModule);
            tableView.refresh();
            primaryStage.close();
        }
    }
}

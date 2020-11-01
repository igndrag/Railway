package NATrain.UI.workPlace;

import NATrain.UI.NavigatorFxController;
import NATrain.model.Model;
import NATrain.remoteControlModules.ControlModule;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ConnectionServiceEmulatorController {

    @FXML
    private TextField reqCodeTextField;
    @FXML
    private TextField reqControlModuleAddressTextField;
    @FXML
    private TextField reqChannelNumberTextField;
    @FXML
    private Button sendButton;
    @FXML
    private TextField reqCommandTextField;
    @FXML
    private TextField statusTextField;
    @FXML
    private TextField resChannelNumberTextField;
    @FXML
    private TextField resControlModuleAddressTextField;
    @FXML
    private TextField resCodeTextField;
    @FXML
    private Button receiveButton;

    public void initialize() {


    }

    @FXML
    private void sendCommand() {
        StringBuilder command = new StringBuilder();
        try {
            int requestCode = Integer.parseInt(reqCodeTextField.getText());
            int commandCode = Integer.parseInt(reqCommandTextField.getText());
            int controlModuleAddress = Integer.parseInt(reqControlModuleAddressTextField.getText());
            int channelNumber = Integer.parseInt(reqChannelNumberTextField.getText());
            command.append(requestCode);
            command.append(commandCode);
            command.append(controlModuleAddress);
            command.append(channelNumber);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        //TODO send command to central module when real model created
    }

    @FXML
    private void receiveResponse() {
        try {
            int responseCode = Integer.parseInt(resCodeTextField.getText());
            int statusCode = Integer.parseInt(statusTextField.getText());
            int controlModuleAddress = Integer.parseInt(resControlModuleAddressTextField.getText());
            int channelNumber = Integer.parseInt(resChannelNumberTextField.getText());
            ControlModule controlModule = Model.getControlModules().get(controlModuleAddress);
            if (controlModule != null) {
                controlModule.refreshObjectState(channelNumber, statusCode);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }
}

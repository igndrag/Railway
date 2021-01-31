package NATrain.UI.controlModuleRedactor;

import NATrain.сontrolModules.ControlModule;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CMEditorController {

    private int chCount;
    private ControlModule controlModule;
    private TableView<ControlModule> tableView;
    private ObservableList<String> observableList;

    @FXML
    private Label CMTypeLabel;
    @FXML
    private Button saveButton;

    public void initialize(ControlModule controlModule, TableView<ControlModule> tableView) {
        this.tableView = tableView;
        this.controlModule = controlModule;
        CMTypeLabel.setText(controlModule.toString());

    }

    @FXML
    private void saveAndClose() {
        tableView.refresh();
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
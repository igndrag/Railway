package NATrain.UI.controlModuleRedactor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CMNavigatorController {
    private Stage primaryStage;

    @FXML
    private Pane previewPane;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn typeColumn;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn addressColumn;
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {

    }

}

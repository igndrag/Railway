package NATrain.UI.controlModuleRedactor;
import NATrain.model.Model;
import NATrain.remoteControlModules.ControlModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class CMNavigatorController {

    private Stage primaryStage;
    private ObservableList<ControlModule> controlModules;

    @FXML
    private Pane previewPane;
    @FXML
    private TableView<ControlModule> tableView;
    @FXML
    private TableColumn<ControlModule, String> typeColumn;
    @FXML
    private TableColumn<ControlModule, Integer> addressColumn;
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
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        editButton.setDisable(true);
        deleteButton.setDisable(true);

        controlModules = FXCollections.observableArrayList(Model.getControlModules().values());
        controlModules.sort(Comparator.comparing(ControlModule::getAddress));
        tableView.setItems(controlModules);

        newButton.setOnMouseClicked(event -> {
            try {
                toControlModuleCreator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteButton.setOnAction(event -> {
            ControlModule objectForDelete = tableView.getSelectionModel().getSelectedItem();
            Arrays.stream(objectForDelete.getChannels()) //clear all channels references before delete control module
                    .filter(Objects::nonNull)
                    .forEach(tracksideObject -> tracksideObject.setControlModule(null));

            controlModules.remove(objectForDelete);
            Model.getControlModules().remove(objectForDelete.getAddress());
            if (controlModules.size() == 0) {
                editButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });

        editButton.setOnAction(event -> {
            try {
                toControlModuleRedactor(tableView.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        tableView.setOnMouseClicked(event -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
                refreshPreview(tableView.getSelectionModel().getSelectedItem());
                tableView.setOnMouseClicked( event1 -> {
                    refreshPreview(tableView.getSelectionModel().getSelectedItem());
                });
            }
        });
    }

    private void toControlModuleRedactor(ControlModule controlModule) throws IOException {

    }

    private void toControlModuleCreator() throws IOException{

    }

    private void refreshPreview (ControlModule controlModule) {
        previewPane.getChildren().clear();
        Group preview = new Group();
        for (int i = 0; i < controlModule.getChannels().length; i++) {
            StringBuilder chName = new StringBuilder("CH" + i + ": ");
            if (controlModule.getChannels()[i] != null) {
                chName.append(controlModule.getChannels()[i].getId());
            } else {
                chName.append("none");
            }
            Text text = new Text(chName.toString());
            text.setX(20);
            text.setY(40 + 20*i);
            preview.getChildren().add(text);
        }
        previewPane.getChildren().add(preview);
    }
}

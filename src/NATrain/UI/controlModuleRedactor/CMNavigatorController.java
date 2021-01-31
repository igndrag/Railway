package NATrain.UI.controlModuleRedactor;

import NATrain.model.Model;
import NATrain.сontrolModules.ControlModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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

        controlModules = FXCollections.observableArrayList(Model.getControlModules());
        controlModules.sort(Comparator.comparing(ControlModule::getId));
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
//            Arrays.stream(objectForDelete.getChannels()) //clear all channels references before delete control module
//                    .filter(Objects::nonNull)
//                    .forEach(tracksideObject -> tracksideObject.setControlModule(null));

            controlModules.remove(objectForDelete);
            Model.getControlModules().remove(objectForDelete);
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
            }
        });
    }

    private void toControlModuleRedactor(ControlModule controlModule) throws IOException {
        FXMLLoader loader = new FXMLLoader(CMEditorController.class.getResource("CMEditor.fxml"));
        Stage controlModuleEditor = new Stage();
        controlModuleEditor.setTitle("Control Module Editor");
        controlModuleEditor.setScene(new Scene(loader.load(), 230, 330));
        controlModuleEditor.setResizable(false);
        CMEditorController controller = loader.getController();
        controller.initialize(controlModule, tableView);
        controlModuleEditor.initModality(Modality.WINDOW_MODAL);
        controlModuleEditor.initOwner(primaryStage);
        controlModuleEditor.show();
    }

    private void toControlModuleCreator() throws IOException {
        FXMLLoader loader = new FXMLLoader(CMCreatorController.class.getResource("CMCreator.fxml"));
        Stage controlModuleCreator = new Stage();
        controlModuleCreator.setTitle("Control Module Creator");
        controlModuleCreator.setScene(new Scene(loader.load(), 300, 270));
        controlModuleCreator.setResizable(false);
        CMCreatorController controller = loader.getController();
        controller.initialize(tableView, controlModules);
        controller.setPrimaryStage(controlModuleCreator);
        controlModuleCreator.initModality(Modality.WINDOW_MODAL);
        controlModuleCreator.initOwner(primaryStage);
        controlModuleCreator.show();
    }
}

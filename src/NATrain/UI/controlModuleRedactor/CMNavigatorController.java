package NATrain.UI.controlModuleRedactor;

import NATrain.model.Model;
import NATrain.сontrolModules.ControlModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;

public class CMNavigatorController {

    private Stage primaryStage;
    private ObservableList<ControlModule> controlModules;

    @FXML
    private TableView<ControlModule> tableView;
    @FXML
    private TableColumn<ControlModule, String> typeColumn;
    @FXML
    private TableColumn<ControlModule, String> idColumn;
    @FXML
    private TableColumn<ControlModule, Integer> inputsCountColumn;
    @FXML
    private TableColumn<ControlModule, Integer> outputsCountColumn;
    @FXML
    private TableColumn<ControlModule, String> objectsColumn;

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
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("moduleType"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        inputsCountColumn.setCellValueFactory(new PropertyValueFactory<>("inputsCount"));
        outputsCountColumn.setCellValueFactory(new PropertyValueFactory<>("outputsCount"));
        objectsColumn.setCellValueFactory(new PropertyValueFactory<>("objectNames"));

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        controlModules = FXCollections.observableArrayList(Model.getControlModules().values());
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

            //clear all channels references before delete control module

            objectForDelete.getInputChannels().values().stream()
                    .filter(Objects::nonNull)
                    .forEach(tracksideObject -> tracksideObject.setModule(null));

            objectForDelete.getOutputChannels().values().stream()
                    .filter(Objects::nonNull)
                    .forEach(tracksideObject -> tracksideObject.setModule(null));

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
        controlModuleEditor.setScene(new Scene(loader.load(), 800, 390));
        controlModuleEditor.setResizable(false);
        CMEditorController controller = loader.getController();
        controller.initialize(controlModule, tableView);
        controlModuleEditor.initModality(Modality.WINDOW_MODAL);
        controlModuleEditor.initOwner(primaryStage);
        controlModuleEditor.setOnCloseRequest(event -> {
            tableView.refresh();
        });
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

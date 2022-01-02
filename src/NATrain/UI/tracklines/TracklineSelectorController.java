package NATrain.UI.tracklines;

import NATrain.UI.AppConfigController;
import NATrain.model.Model;
import NATrain.routes.Trackline;
import NATrain.routes.TrackBlockingType;
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

public class TracklineSelectorController {

    private Stage primaryStage;

    ObservableList<Trackline> tracklines;
    @FXML
    private TableView <Trackline> tracksTableView;
    @FXML
    private TableColumn <Trackline, String> idColumn;
    @FXML
    private TableColumn <Trackline, TrackBlockingType> blockingTypeColumn;
    @FXML
    private TableColumn <Trackline, Integer> blockSectionCountColumn;
    @FXML
    private TableColumn <Trackline, Boolean> bidirectionalColumn;
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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        blockingTypeColumn.setCellValueFactory(new PropertyValueFactory<>("trackBlockingType"));
        blockSectionCountColumn.setCellValueFactory(new PropertyValueFactory<>("blockSectionCount"));
        bidirectionalColumn.setCellValueFactory(new PropertyValueFactory<>("bidirectional"));

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        tracklines = FXCollections.observableArrayList(Model.getTracklines());
        tracklines.sort(Comparator.comparing(Trackline::getId));
        tracksTableView.setItems(tracklines);

        newButton.setOnMouseClicked(event -> {
            try {
                Trackline newTrackline = new Trackline("New Trackline");
                addTrack(newTrackline);
                toTrackRedactor(newTrackline);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteButton.setOnAction(event -> {
            deleteTrack(tracksTableView.getSelectionModel().getSelectedItem());
            if (tracklines.size() == 0) {
                editButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });

        editButton.setOnAction(event -> {
            try {
                toTrackRedactor(tracksTableView.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        tracksTableView.setOnMouseClicked(event -> {
            if (tracksTableView.getSelectionModel().getSelectedItem() != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            }
        });
    }

    private void toTrackRedactor(Trackline trackline) throws IOException {
        FXMLLoader loader;
        switch (AppConfigController.getLanguage()) {
            case RU:
                loader = new FXMLLoader(TracklineRedactorController.class.getResource("TracklineRedactor_RU.fxml"));
                break;
            default:
                loader = new FXMLLoader(TracklineRedactorController.class.getResource("TracklineRedactor.fxml"));
        }
        Stage trackRedactor = new Stage();
        trackRedactor.setTitle("Trackline Redactor");
        trackRedactor.setScene(new Scene(loader.load(), 500, 480));
        trackRedactor.setResizable(false);
        TracklineRedactorController controller = loader.getController();
        controller.initialize(trackline, tracksTableView);
        trackRedactor.initModality(Modality.WINDOW_MODAL);
        trackRedactor.initOwner(primaryStage);
        trackRedactor.show();

        trackRedactor.setOnCloseRequest(event -> {
            tracksTableView.refresh();
        });
    }

    private void addTrack(Trackline trackline) {
        Model.getTracklines().add(trackline);
        tracksTableView.getItems().add(trackline);
        tracksTableView.refresh();
    }

    private void deleteTrack(Trackline trackline) {
        Model.getTracklines().remove(trackline);
        tracksTableView.getItems().remove(trackline);
        tracksTableView.refresh();
    }


}

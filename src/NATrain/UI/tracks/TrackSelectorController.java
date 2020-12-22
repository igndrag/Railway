package NATrain.UI.tracks;

import NATrain.UI.mosaicRedactor.MosaicRedactorFxController;
import NATrain.model.Model;
import NATrain.routes.Track;
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

public class TrackSelectorController {

    private Stage primaryStage;

    ObservableList<Track> tracks;
    @FXML
    private TableView <Track> tracksTableView;
    @FXML
    private TableColumn <Track, String> idColumn;
    @FXML
    private TableColumn <Track, TrackBlockingType> blockingTypeColumn;
    @FXML
    private TableColumn <Track, Integer> blockSectionCountColumn;
    @FXML
    private TableColumn <Track, Boolean> bidirectionalColumn;
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

        tracks = FXCollections.observableArrayList(Model.getTracks());
        tracks.sort(Comparator.comparing(Track::getId));
        tracksTableView.setItems(tracks);

        newButton.setOnMouseClicked(event -> {
            try {
                Track newTrack = new Track("New Track");
                addTrack(newTrack);
                toTrackRedactor(newTrack);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteButton.setOnAction(event -> {
            deleteTrack(tracksTableView.getSelectionModel().getSelectedItem());
            if (tracks.size() == 0) {
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

    private void toTrackRedactor(Track track) throws IOException {
        FXMLLoader loader = new FXMLLoader(TrackRedactorController.class.getResource("TrackRedactor.fxml"));
        Stage trackRedactor = new Stage();
        trackRedactor.setTitle("Track Redactor");
        trackRedactor.setScene(new Scene(loader.load(), 465, 480));
        trackRedactor.setResizable(false);
        TrackRedactorController controller = loader.getController();
        controller.initialize(track, tracksTableView);
        trackRedactor.initModality(Modality.WINDOW_MODAL);
        trackRedactor.initOwner(primaryStage);
        trackRedactor.show();

        trackRedactor.setOnCloseRequest(event -> {
            tracksTableView.refresh();
        });
    }

    private void addTrack(Track track) {
        Model.getTracks().add(track);
        tracksTableView.getItems().add(track);
        tracksTableView.refresh();
    }

    private void deleteTrack(Track track) {
        Model.getTracks().remove(track);
        tracksTableView.getItems().remove(track);
        tracksTableView.refresh();
    }


}

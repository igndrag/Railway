package NATrain.UI.tracksideObjectRedactor;

import NATrain.model.Model;
import NATrain.trackSideObjects.TrackSection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TracksideObjectRedactorController {

    private static Stage primaryStage;

    @FXML
    private TableColumn<TrackSection, String> trackSectionIdCol;

    @FXML
    private TableColumn<TrackSection, Integer> trackSectionControlModuleCol;

    @FXML
    private TableColumn<TrackSection, Integer> trackSectionChannelCol;

    @FXML
    private TableView<TrackSection> TrackSectionsTableView;


    public static void setPrimaryStage(Stage mainStage) {
        primaryStage = mainStage;
    }

    public void initialize() {
        trackSectionIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        trackSectionChannelCol.setCellValueFactory(new PropertyValueFactory<>("channel"));
        trackSectionControlModuleCol.setCellValueFactory(new PropertyValueFactory<>("ControlModuleAddress"));

        ObservableList<TrackSection> list = FXCollections.observableArrayList(Model.getTrackSections().values());
        TrackSectionsTableView.setItems(list);
    }
}

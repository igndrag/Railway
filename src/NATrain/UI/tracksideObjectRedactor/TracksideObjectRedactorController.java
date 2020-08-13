package NATrain.UI.tracksideObjectRedactor;

import NATrain.model.Model;
import NATrain.trackSideObjects.TrackSection;
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

public class TracksideObjectRedactorController {

    private static Stage primaryStage;

    @FXML
    private Button newTrackSectionButton;

    @FXML
    private Button editTrackSectionButton;

    @FXML
    private Button deleteTrackSectionButton;

    @FXML
    private TableColumn<TrackSection, String> trackSectionIdCol;

    @FXML
    private TableColumn<TrackSection, Integer> trackSectionControlModuleCol;

    @FXML
    private TableColumn<TrackSection, Integer> trackSectionChannelCol;

    @FXML
    private TableView<TrackSection> trackSectionsTableView;


    public static void setPrimaryStage(Stage mainStage) {
        primaryStage = mainStage;
    }

    public void initialize() {
        trackSectionIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        trackSectionChannelCol.setCellValueFactory(new PropertyValueFactory<>("channel"));
        trackSectionControlModuleCol.setCellValueFactory(new PropertyValueFactory<>("ControlModuleAddress"));
        editTrackSectionButton.setDisable(true);
        deleteTrackSectionButton.setDisable(true);

        ObservableList<TrackSection> list = FXCollections.observableArrayList(Model.getTrackSections().values());
        trackSectionsTableView.setItems(list);

        newTrackSectionButton.setOnAction(event -> {
            try {
                toTrackSectionRedactor(new TrackSection(""));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteTrackSectionButton.setOnAction(event -> {
            list.remove(trackSectionsTableView.getSelectionModel().getSelectedItem());
            if (list.size() == 0) {
                editTrackSectionButton.setDisable(true);
                deleteTrackSectionButton.setDisable(true);
            }
        });



        trackSectionsTableView.setOnMouseClicked(event -> {
            if (trackSectionsTableView.getSelectionModel().getSelectedItem() != null) {
                editTrackSectionButton.setDisable(false);
                deleteTrackSectionButton.setDisable(false);
                trackSectionsTableView.setOnMouseClicked(null);
            }
        });


    }

    private void toTrackSectionRedactor (TrackSection trackSection) throws IOException {
        FXMLLoader loader = new FXMLLoader(TrackSectionRedactorController.class.getResource("trackSectionRedactor.fxml"));
        Stage trackSectionRedactor = new Stage();
        trackSectionRedactor.setTitle("Track Section Redactor");
        trackSectionRedactor.setScene(new Scene(loader.load(), 325, 200));
        trackSectionRedactor.setResizable(false);
        TrackSectionRedactorController controller = loader.getController();
        controller.init(trackSection);
        trackSectionRedactor.initModality(Modality.WINDOW_MODAL);
        trackSectionRedactor.initOwner(primaryStage);
        trackSectionRedactor.show();
    }

}

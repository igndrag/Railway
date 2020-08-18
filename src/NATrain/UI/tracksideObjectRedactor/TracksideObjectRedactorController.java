package NATrain.UI.tracksideObjectRedactor;

import NATrain.model.Model;
import NATrain.trackSideObjects.TrackSection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Comparator;

public class TracksideObjectRedactorController {

    protected static ObservableList<TrackSection> trackSectionList;

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
    protected TableView<TrackSection> trackSectionsTableView;


    public static void setPrimaryStage(Stage mainStage) {
        primaryStage = mainStage;
    }

    public void initialize() {
        trackSectionIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        trackSectionChannelCol.setCellValueFactory(new PropertyValueFactory<>("channel"));
        trackSectionControlModuleCol.setCellValueFactory(new PropertyValueFactory<>("controlModuleAddress"));
        editTrackSectionButton.setDisable(true);
        deleteTrackSectionButton.setDisable(true);


        trackSectionList = FXCollections.observableArrayList(Model.getTrackSections().values());
        trackSectionList.sort(Comparator.comparing(TrackSection::getId));
        trackSectionsTableView.setItems(trackSectionList);

        newTrackSectionButton.setOnMouseClicked(event -> {
            try {
                TrackSection trackSection = new TrackSection("New Track Section");
                toTrackSectionRedactor(trackSection);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteTrackSectionButton.setOnAction(event -> {
            TrackSection trackForDelete = trackSectionsTableView.getSelectionModel().getSelectedItem();
            if (trackForDelete.getControlModule() != null)
                trackForDelete.getControlModule().deleteTrackSideObjectFromChannel(trackForDelete.getChannel());
            trackForDelete.setControlModule(null);
            trackSectionList.remove(trackForDelete);
            Model.getTrackSections().remove(trackForDelete.getId());
            if (trackSectionList.size() == 0) {
                editTrackSectionButton.setDisable(true);
                deleteTrackSectionButton.setDisable(true);
            }
        });

        editTrackSectionButton.setOnAction(event -> {
            try {
                toTrackSectionRedactor(trackSectionsTableView.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
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
        controller.init(trackSection, trackSectionsTableView);
        trackSectionRedactor.initModality(Modality.WINDOW_MODAL);
        trackSectionRedactor.initOwner(primaryStage);
        trackSectionRedactor.show();
    }

}

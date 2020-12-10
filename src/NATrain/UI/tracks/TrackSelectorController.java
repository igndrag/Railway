package NATrain.UI.tracks;

import NATrain.routes.Track;
import NATrain.routes.TrackBlockingType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TrackSelectorController {
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
}

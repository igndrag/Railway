package NATrain.UI.tracks;

import NATrain.routes.TrackBlockSection;
import NATrain.trackSideObjects.Signal;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TrackRedactorController {
    @FXML
    private TextField trackNameTextField;
    @FXML
    private TextField blockSectionPrefixTextField;
    @FXML
    private TextField blockSectionSuffixTextField;
    @FXML
    private TextField blockSectionCountTextField;
    @FXML
    private Button deleteLastButton;
    @FXML
    private Button clearAllButton;
    @FXML
    private TextField signalPrefixTextField;
    @FXML
    private ChoiceBox<Signal> normalDirectionArrivalSignalChoiceBox;
    @FXML
    private ChoiceBox<Signal> reversedDirectionArrivalSignalChoiceBox;
    @FXML
    private TableView <TrackBlockSection> blockSectionsTableView;
    @FXML
    private TableColumn <TrackBlockSection, String> idColumn;
    @FXML
    private TableColumn <TrackBlockSection, String> normalDirectionSignalColumn;
    @FXML
    private TableColumn <TrackBlockSection, String> reversedDirectionSignalColumn;
    @FXML
    private Button saveButton;
}

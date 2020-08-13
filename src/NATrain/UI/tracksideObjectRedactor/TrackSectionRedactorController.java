package NATrain.UI.tracksideObjectRedactor;

import NATrain.trackSideObjects.TrackSection;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class TrackSectionRedactorController {

    @FXML
    private TextField trackNameTextField;
    @FXML
    private Pane trackSectionPreview;
    @FXML
    private ChoiceBox trackControlModuleChoiceBox;
    @FXML
    private ChoiceBox trackChannelChoiceBox;

    void init (TrackSection trackSection) {
            System.out.println("Track Section Redactor Initializing");
    }
}

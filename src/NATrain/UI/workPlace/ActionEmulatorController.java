package NATrain.UI.workPlace;

import NATrain.trackSideObjects.TrackSection;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;

public class ActionEmulatorController {
    @FXML
    private ToggleButton interlockTrackToggleButton;
    @FXML
    private ToggleButton unlockTrackToggleButton;
    @FXML
    private ToggleButton occupiedTrackToggleButton;
    @FXML
    private ToggleButton freeTrackToggleButton;
    @FXML
    private ChoiceBox<TrackSection> trackChoiceBox;
    @FXML
    private ChoiceBox<String> signalChoiceBox;
    @FXML
    private ChoiceBox<String> signalStateChoiceBox;
    @FXML
    private ChoiceBox<String> switchChoiceBox;
    @FXML
    private ToggleButton plusToggleButton;
    @FXML
    private ToggleButton minusToggleButton;
    @FXML
    private ToggleButton unlockSwitchToggleButton;
    @FXML
    private ToggleButton interlockSwitchToggleButton;
}

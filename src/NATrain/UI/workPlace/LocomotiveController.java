package NATrain.UI.workPlace;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.TextFlow;

public class LocomotiveController {
    @FXML
    private TextFlow log;
    @FXML
    private Slider speedSlider;
    @FXML
    private ToggleButton forwardToggleButton;
    @FXML
    private ToggleButton backwardToggleButton;
    @FXML
    private Label speedValueLabel;
    @FXML
    private ToggleButton lightToggleButton;
    @FXML
    private Button runButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button hornButton;
}

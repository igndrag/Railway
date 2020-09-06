package NATrain.UI.routeTable;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class RouteTypeSelectorController {
    @FXML
    private ToggleButton arrivalToggleButton;
    @FXML
    private ToggleButton departureToggleButton;
    @FXML
    private ToggleButton shuntingToggleButton;
    @FXML
    private ToggleButton transitToggleButton;
    @FXML
    private Button createButton;

    public void initialize() {
        ToggleGroup toggleGroup = new ToggleGroup();
        arrivalToggleButton.setToggleGroup(toggleGroup);
        departureToggleButton.setToggleGroup(toggleGroup);
        shuntingToggleButton.setToggleGroup(toggleGroup);
        transitToggleButton.setToggleGroup(toggleGroup);
    }
}

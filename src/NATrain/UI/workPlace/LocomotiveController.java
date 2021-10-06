package NATrain.UI.workPlace;

import NATrain.UI.workPlace.executors.ActionExecutor;
import NATrain.UI.workPlace.executors.RouteExecutor;
import NATrain.quads.LocomotivePanelQuad;
import NATrain.routes.Route;
import NATrain.routes.TrackBlockSection;
import NATrain.trackSideObjects.movableObjects.Autopilot;
import NATrain.trackSideObjects.movableObjects.Locomotive;
import NATrain.trackSideObjects.movableObjects.MovingDirection;
import NATrain.trackSideObjects.movableObjects.RFIDAutopilot;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.util.Optional;

public class LocomotiveController {

    @FXML
    private Label directionLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label idLabel;
    @FXML
    private ToggleButton autopilotToggleButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Pane previewPane;
    @FXML
    private Slider speedSlider;
    @FXML
    private ToggleButton forwardToggleButton;
    @FXML
    private ToggleButton backwardToggleButton;
    @FXML
    private ToggleButton frontLightToggleButton;
    @FXML
    private ToggleButton rearLightToggleButton;
    @FXML
    private Button runButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button hornButton;

    private Locomotive locomotive;
    private Autopilot autopilot;
    private LocomotivePanelQuad quad;
    private double actualSliderValue = 0;

    public Label getLocationLabel() {
        return locationLabel;
    }

    public LocomotivePanelQuad getPreview() {
        return quad;
    }

    public void init(Locomotive locomotive) {
        this.quad = new LocomotivePanelQuad();
        this.locomotive = locomotive;

        locationLabel.setText("");
        directionLabel.setText("");

        ToggleGroup toggleGroup = new ToggleGroup();
        forwardToggleButton.setToggleGroup(toggleGroup);
        backwardToggleButton.setToggleGroup(toggleGroup);
        forwardToggleButton.setSelected(true);
        idLabel.setText(locomotive.getId());
        statusLabel.setText(locomotive.getActualState().getDescription());

        frontLightToggleButton.setOnAction(event -> {
            if (frontLightToggleButton.isSelected()) {
                locomotive.setFrontLightOn();
            } else {
                locomotive.setFrontLightOff();
            }
        });

        rearLightToggleButton.setOnAction(event -> {
            if (rearLightToggleButton.isSelected()) {
                locomotive.setRearLightOn();
            } else {
                locomotive.setRearLightOff();
            }
        });

        runButton.setOnAction(event -> {
            locomotive.run();
        });

        previewPane.getChildren().add(quad.getView());
        speedSlider.setMax(8);
        speedSlider.setMin(0);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);
        speedSlider.setBlockIncrement(0.2);
        speedSlider.setMajorTickUnit(1.0);
        speedSlider.setMinorTickCount(5);
        speedSlider.setSnapToTicks(true);
        speedSlider.setOnMouseReleased(event -> {
            setLocomotiveSpeed();
        });
        speedSlider.setOnKeyReleased(event -> {
            setLocomotiveSpeed();
        });
        stopButton.setOnAction(event -> {
            locomotive.stop();
        });
        forwardToggleButton.setOnAction(event -> {
            locomotive.setMovingDirection(MovingDirection.FORWARD);
        });
        backwardToggleButton.setOnAction(event -> {
            locomotive.setMovingDirection(MovingDirection.BACKWARD);
        });

        autopilotToggleButton.setOnAction(event -> {
            if (autopilotToggleButton.isSelected()) {
                this.autopilot = new RFIDAutopilot(locomotive, this);
                checkRoutesInLocation();
                directionLabel.setText(locomotive.getForwardDirection().toString());
                runButton.setDisable(true);
                speedSlider.setDisable(true);
            } else {
                //autopilot.deactivateListeners();
                locationLabel.setText("");
                directionLabel.setText("");
            }
        });

    }

    public boolean checkRoutesInLocation() {
        Optional<Route> expectedRoute = ActionExecutor.getActiveRoutes().stream().map(RouteExecutor::getRoute).filter(route -> route.getDepartureTrackSection() == locomotive.getFrontTag().getTagLocation()).findFirst();
        if (expectedRoute.isPresent()) {
            autopilot.executeRoute(expectedRoute.get());
            return true;
        } else {
            return false;
        }
    }

    private void setLocomotiveSpeed() {
        if (actualSliderValue != speedSlider.getValue()) {
            actualSliderValue = speedSlider.getValue();
            int speed = (int) (speedSlider.getValue() * 128);
            locomotive.setSpeed(speed);
        }
    }
}



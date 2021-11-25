package NATrain.UI.workPlace;

import NATrain.quads.LocomotivePanelQuad;
import NATrain.trackSideObjects.movableObjects.*;
import NATrain.utils.Sound;
import NATrain.сontrolModules.AbstractLocomotiveModule;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.util.HashSet;
import java.util.Optional;

public class LocomotiveController {


    public ToggleButton backgroundSoundToggleButton2;
    public ToggleButton backgroundSoundToggleButton1;
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
    @FXML
    private ToggleButton engineToggleButton;

    private boolean engineStarted = false;

    private Locomotive locomotive;
    private Autopilot autopilot;
    private LocomotivePanelQuad quad;
    private double actualSliderValue = 0;

    private Sound hornSound;
    private Sound fullSpeedClapSound;
    private Sound halfSpeedClapSound;
    private Sound lowSpeedClapSound;
    private Sound actualRailClapSound;

    private Sound longBrakingSound;
    private Sound shortBrakingSound;

    private Sound dieselStartSound;
    private Sound dieselStopSound;
    private Sound dieselLowSpeedSound;
    private Sound dieselHalfSpeedSound;
    private Sound dieselFullSpeedSound;

    private Sound dieselSound;
    private Sound actualDieselSound;

    private int selectedSpeed = 0;

    public Label getLocationLabel() {
        return locationLabel;
    }

    public LocomotivePanelQuad getPreview() {
        return quad;
    }

    public void init(Locomotive locomotive) {
        dieselStartSound = new Sound("/sounds/diesel_start.wav", 0.3f);
        dieselStopSound = new Sound("/sounds/diesel_stop.wav", 0.4f);
        dieselSound = new Sound("/sounds/diesel_hh.wav", 0.4f);
        dieselLowSpeedSound = new Sound("/sounds/diesel_low.wav", 0.3f);
        dieselHalfSpeedSound = new Sound("/sounds/diesel_normal.wav", 0.2f);
        dieselFullSpeedSound = new Sound("/sounds/diesel_full.wav", 0.5f);

        hornSound = new Sound("/sounds/tifon.wav", 0.4f);
        fullSpeedClapSound = new Sound("/sounds/rail_clap_full_speed.wav", 0.8f);
        halfSpeedClapSound = new Sound("/sounds/rail_clap_normal_speed.wav", 0.8f);
        lowSpeedClapSound = new Sound("/sounds/rail_clap_low_speed.wav", 0.8f);
        longBrakingSound = new Sound("/sounds/super_restricted.wav", 0.4f);
        shortBrakingSound = new Sound("/sounds/braking.wav", 0.4f);


        this.quad = new LocomotivePanelQuad();
        this.locomotive = locomotive;

        locationLabel.setText("");
        directionLabel.setText("");

        ToggleGroup toggleGroup = new ToggleGroup();
        forwardToggleButton.setToggleGroup(toggleGroup);
        backwardToggleButton.setToggleGroup(toggleGroup);
        forwardToggleButton.setSelected(true);

        stopButton.setDisable(true);
        runButton.setDisable(true);

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
            locomotive.setSpeed(selectedSpeed);
            locomotive.run();
            playRailClack();
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

        speedSlider.setDisable(true);

        stopButton.setOnAction(event -> {
            if (actualRailClapSound != longBrakingSound && locomotive.isMoving()) {
                shortBrakingSound.play();
            }
            if (actualRailClapSound != null) {
                actualRailClapSound.stop();
            }
            if (engineStarted && actualDieselSound != null) {
                actualDieselSound.stop();
            }
            if (engineStarted) {
                actualDieselSound = dieselSound;
                actualDieselSound.loop();
            }
            locomotive.stop();
            actualRailClapSound = null;
        });

        forwardToggleButton.setOnAction(event -> {
            locomotive.stop();
            locomotive.setMovingDirection(MovingDirection.FORWARD);
        });
        backwardToggleButton.setOnAction(event -> {
            locomotive.stop();
            locomotive.setMovingDirection(MovingDirection.BACKWARD);
        });

        hornButton.setOnAction(event -> {
            hornSound.play();
        });

        engineToggleButton.setOnAction(event -> {
            if (engineToggleButton.isSelected()) {
                if (dieselStopSound.getClip().isRunning()) {
                    engineToggleButton.setSelected(false);
                } else {
                    Sound.playSoundAndLoop(dieselStartSound, dieselSound);
                    locomotive.setMovingDirection(MovingDirection.FORWARD);
                    actualDieselSound = dieselSound;
                    engineStarted = true;
                    runButton.setDisable(false);
                    stopButton.setDisable(false);
                    speedSlider.setDisable(false);
                }
            } else {
                if (dieselStopSound.getClip().isRunning()) {
                    engineToggleButton.setSelected(false);
                } else {
                    locomotive.stop();
                    actualDieselSound.stop();
                    actualDieselSound = null;
                    dieselStopSound.play();
                    engineStarted = false;
                    runButton.setDisable(true);
                    stopButton.setDisable(true);
                    speedSlider.setDisable(true);
                }
            }
        });

        autopilotToggleButton.setOnAction(event -> {
            if (autopilotToggleButton.isSelected()) {
                locomotive.setSpeed(0);
                locomotive.run();
                locomotive.setMovingDirection(MovingDirection.FORWARD);
                locomotive.setActualState(LocomotiveState.NOT_MOVING);
                if (autopilot == null) {
                    autopilot = new RFIDAutopilot(locomotive, this);
                }
                directionLabel.setText(locomotive.getForwardDirection().toString());
                forwardToggleButton.setSelected(true);
                backwardToggleButton.setSelected(false);
                forwardToggleButton.setDisable(true);
                backwardToggleButton.setDisable(true);
                runButton.setDisable(true);
                speedSlider.setDisable(true);
                autopilot.enable();
            } else {
                autopilot.disable();
                runButton.setDisable(false);
                forwardToggleButton.setDisable(false);
                backwardToggleButton.setDisable(false);
                speedSlider.setDisable(false);
                locationLabel.setText("");
                directionLabel.setText("");
            }
        });
    }

    private void setLocomotiveSpeed() {
        if (actualSliderValue != speedSlider.getValue()) {
            actualSliderValue = speedSlider.getValue();
            selectedSpeed = (int) (464 + speedSlider.getValue() * 70);
            if (locomotive.getSpeed() > selectedSpeed && selectedSpeed > Autopilot.SUPER_RESTRICTED_SPEED && locomotive.isMoving()) {
                shortBrakingSound.play();
            }
            locomotive.setSpeed(selectedSpeed);
            playRailClack();
        }
    }

    private void playRailClack() {
        if (actualRailClapSound != null) {
            actualRailClapSound.stop();
        }
        if (actualDieselSound != null) {
            actualDieselSound.stop();
        }
        if (locomotive.getSpeed() > Autopilot.FULL_SPEED) {
            if (locomotive.isMoving()) {
                actualRailClapSound = fullSpeedClapSound;
                actualRailClapSound.loop();
            }
            actualDieselSound = dieselFullSpeedSound;
            actualDieselSound.loop();
            return;
        }

        if (locomotive.getSpeed() >= Autopilot.RESTRICTED_SPEED) {
            if (locomotive.isMoving()) {
                actualRailClapSound = halfSpeedClapSound;
                actualRailClapSound.loop();
            }
            actualDieselSound = dieselHalfSpeedSound;
            actualDieselSound.loop();
            return;
        }

        if (locomotive.getSpeed() > Autopilot.SUPER_RESTRICTED_SPEED) {
            if (locomotive.isMoving()) {
                actualRailClapSound = lowSpeedClapSound;
                actualRailClapSound.loop();
            }
            actualDieselSound = dieselLowSpeedSound;
            actualDieselSound.loop();
            return;
        }

        if (locomotive.getSpeed() < Autopilot.SUPER_RESTRICTED_SPEED) {
            actualDieselSound = dieselSound;
            actualDieselSound.loop();
        }

        // if (actualRailClapSound != longBrakingSound) {
        //     actualRailClapSound = longBrakingSound;
        //     longBrakingSound.play();
        // }

    }

    public void deactivate() {
        locomotive.stop();
        if (actualRailClapSound != null) {
            actualRailClapSound.stop();
        }
        if (actualDieselSound != null) {
            actualDieselSound.stop();
        }
    }
}



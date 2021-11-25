package NATrain.UI.workPlace;

import NATrain.utils.Sound;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

public class SoundPlayer {
    private static Sound backgroundSound1 = new Sound("/sounds/pane/forest.wav", 0.2f);
    private static Sound backgroundSound2 = new Sound("/sounds/pane/rain.wav", 0.3f);
    private static Sound meteorSound = new Sound("/sounds/pane/meteor.wav", 1.0f);
    private static Sound stormSound = new Sound("/sounds/pane/storm.wav", 1.0f);
    private static Sound crossingRingSound = new Sound("/sounds/pane/crossing_ring.wav", 0.2f);
    private static Sound customSound1 = new Sound("/sounds/pane/eralash.wav", 0.8f);

    @FXML
    private ToggleButton backgroundSoundToggleButton1;
    @FXML
    private ToggleButton backgroundSoundToggleButton2;
    @FXML
    private Button meteorButton;
    @FXML
    private Button stormButton;
    @FXML
    private Button roadCrossingRingButton;
    @FXML
    private Button customSoundButton1;
    @FXML
    private Button customSoundButton2;
    @FXML
    private Button customSoundButton3;

    public void init() {
        backgroundSoundToggleButton1.setOnAction(event -> {
            if (backgroundSoundToggleButton1.isSelected()) {
                backgroundSound1.loop();
            } else {
                backgroundSound1.stop();
            }
        });

        backgroundSoundToggleButton2.setOnAction(event -> {
            if (backgroundSoundToggleButton2.isSelected()) {
                backgroundSound2.loop();
            } else {
                backgroundSound2.stop();
            }
        });

        stormButton.setOnAction(event -> {
            stormSound.play();
        });

        meteorButton.setOnAction(event -> {
            meteorSound.play();
        });

        customSoundButton1.setOnAction(event -> {
            customSound1.play();
        });

        roadCrossingRingButton.setOnAction(event -> {
            if (crossingRingSound.getClip().isRunning()) {
                crossingRingSound.stop();
            } else {
                crossingRingSound.play();
            }
        });

    }

    public void deactivate() {
        backgroundSound1.stop();
        backgroundSound2.stop();
    }
}

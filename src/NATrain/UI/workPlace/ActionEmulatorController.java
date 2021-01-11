package NATrain.UI.workPlace;

import NATrain.model.Model;
import NATrain.routes.Track;
import NATrain.routes.TrackBlockSection;
import NATrain.trackSideObjects.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class ActionEmulatorController {

    @FXML
    private ChoiceBox<Track> trackLineChoiceBox;
    @FXML
    private ChoiceBox<TrackBlockSection> blockSectionChoiceBox;
    @FXML
    private ToggleButton occupiedBlockSectionToggleButton;
    @FXML
    private ToggleButton freeBlockSectionToggleButton;
    @FXML
    private ToggleButton interlockTrackToggleButton;
    @FXML
    private ToggleButton occupiedTrackToggleButton;
    @FXML
    private ChoiceBox<TrackSection> trackChoiceBox;
    @FXML
    private ChoiceBox<Signal> signalChoiceBox;
    @FXML
    private ChoiceBox<SignalState> signalStateChoiceBox;
    @FXML
    private ChoiceBox<Switch> switchChoiceBox;
    @FXML
    private ToggleButton plusToggleButton;
    @FXML
    private ToggleButton minusToggleButton;
    @FXML

    public void initialize() {
        trackChoiceBox.setItems(FXCollections.observableArrayList(Model.getTrackSections().values()));
        switchChoiceBox.setItems(FXCollections.observableArrayList(Model.getSwitches().values()));
        signalChoiceBox.setItems(FXCollections.observableArrayList(Model.getSignals().values()));
        trackLineChoiceBox.setItems(FXCollections.observableArrayList(Model.getTracks()));

        ToggleGroup toggleGroup = new ToggleGroup();
        plusToggleButton.setToggleGroup(toggleGroup);
        minusToggleButton.setToggleGroup(toggleGroup);

        toggleGroup = new ToggleGroup();
        freeBlockSectionToggleButton.setToggleGroup(toggleGroup);
        occupiedBlockSectionToggleButton.setToggleGroup(toggleGroup);

        signalChoiceBox.setOnAction(event -> {
            if (!signalChoiceBox.getSelectionModel().isEmpty()) {
                Signal signal = signalChoiceBox.getSelectionModel().getSelectedItem();
                signalStateChoiceBox.setItems(FXCollections.observableArrayList(signal.getApprovedSignals()));
                signalStateChoiceBox.setValue(signal.getSignalState());
            }
        });

        signalStateChoiceBox.setOnAction(event -> {
            if (!signalChoiceBox.getSelectionModel().isEmpty() && !signalStateChoiceBox.getSelectionModel().isEmpty()) {
                Signal signal = signalChoiceBox.getSelectionModel().getSelectedItem();
                signal.setSignalState(signalStateChoiceBox.getSelectionModel().getSelectedItem());
            }
        });

        trackChoiceBox.setOnAction(event -> {
            if (!trackChoiceBox.getSelectionModel().isEmpty()) {
                TrackSection trackSection = trackChoiceBox.getSelectionModel().getSelectedItem();
                interlockTrackToggleButton.setSelected(trackSection.isInterlocked());
                if (trackSection.getVacancyState() == TrackSectionState.OCCUPIED)
                    occupiedTrackToggleButton.setSelected(true);
                else
                    occupiedTrackToggleButton.setSelected(false); //button isn't selected for undefined state
            }

        });

        trackLineChoiceBox.setOnAction(event -> {
            if (!trackLineChoiceBox.getSelectionModel().isEmpty()) {
                blockSectionChoiceBox.setItems(FXCollections.observableArrayList(trackLineChoiceBox.getValue().getBlockSections()));
            }
        });

        blockSectionChoiceBox.setOnAction(event -> {
            if (!blockSectionChoiceBox.getSelectionModel().isEmpty()) {
                if (blockSectionChoiceBox.getValue().getSection().getVacancyState() == TrackSectionState.FREE) {
                    freeBlockSectionToggleButton.setSelected(true);
                } else {
                    occupiedBlockSectionToggleButton.setSelected(true);
                }
            }
        });

        freeBlockSectionToggleButton.setOnAction(event -> {
            if (!blockSectionChoiceBox.getSelectionModel().isEmpty()) {
                blockSectionChoiceBox.getValue().getSection().setVacancyState(TrackSectionState.FREE);
            }
        });

        occupiedBlockSectionToggleButton.setOnAction(event -> {
            if (!blockSectionChoiceBox.getSelectionModel().isEmpty()) {
                blockSectionChoiceBox.getValue().getSection().setVacancyState(TrackSectionState.OCCUPIED);
            }
        });

        interlockTrackToggleButton.setOnMouseClicked(event -> {
            if (!trackChoiceBox.getSelectionModel().isEmpty()) {
                TrackSection trackSection = trackChoiceBox.getSelectionModel().getSelectedItem();
                trackSection.setInterlocked(interlockTrackToggleButton.isSelected());
            }
        });

        occupiedTrackToggleButton.setOnMouseClicked(event -> {
            if (!trackChoiceBox.getSelectionModel().isEmpty()) {
                TrackSection trackSection = trackChoiceBox.getSelectionModel().getSelectedItem();
                if (occupiedTrackToggleButton.isSelected()) {
                    trackSection.setVacancyState(TrackSectionState.OCCUPIED);
                } else {
                    trackSection.setVacancyState(TrackSectionState.FREE);
                }
            }
        });

        switchChoiceBox.setOnAction(event -> {
            if (!switchChoiceBox.getSelectionModel().isEmpty()) {
                Switch aSwitch = switchChoiceBox.getSelectionModel().getSelectedItem();
                plusToggleButton.setSelected(aSwitch.getSwitchState() == SwitchState.PLUS);
                minusToggleButton.setSelected(aSwitch.getSwitchState() == SwitchState.MINUS);
            }
        });

        plusToggleButton.setOnMouseClicked(event -> {
            if (!switchChoiceBox.getSelectionModel().isEmpty()) {
                Switch aSwitch = switchChoiceBox.getSelectionModel().getSelectedItem();
                aSwitch.setSwitchState(SwitchState.PLUS);
            }
        });

        minusToggleButton.setOnMouseClicked(event -> {
            if (!switchChoiceBox.getSelectionModel().isEmpty()) {
                Switch aSwitch = switchChoiceBox.getSelectionModel().getSelectedItem();
                aSwitch.setSwitchState(SwitchState.MINUS);
            }
        });
    }

    @FXML
    protected void setAllSectionsFree() {
        Model.getTrackSections().values().forEach(trackSection -> trackSection.setVacancyState(TrackSectionState.FREE));
    }

    @FXML
    protected void setAllSignalClosed() {
        Model.getSignals().values().forEach(Signal::close);
    }
}

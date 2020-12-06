package NATrain.UI.workPlace;

import NATrain.model.Model;
import NATrain.trackSideObjects.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class ActionEmulatorController {

    @FXML
    private Button allClosedButton;
    @FXML
    private Button allFreeButton;
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

    private ToggleButton interlockSwitchToggleButton;

    public void initialize() {
        trackChoiceBox.setItems(FXCollections.observableArrayList(Model.getTrackSections().values()));
        switchChoiceBox.setItems(FXCollections.observableArrayList(Model.getSwitches().values()));
        signalChoiceBox.setItems(FXCollections.observableArrayList(Model.getSignals().values()));

        ToggleGroup toggleGroup = new ToggleGroup();
        plusToggleButton.setToggleGroup(toggleGroup);
        minusToggleButton.setToggleGroup(toggleGroup);

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

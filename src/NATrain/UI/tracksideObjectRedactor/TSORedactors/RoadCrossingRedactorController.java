package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.model.Model;
import NATrain.routes.Trackline;
import NATrain.routes.TracklineBlockSection;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.customObjects.RoadCrossing;
import NATrain.trackSideObjects.customObjects.Servo;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalType;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;

import java.util.stream.Collectors;

public class RoadCrossingRedactorController extends TracksideObjectRedactorController {

    @FXML
    private ChoiceBox<Signal> firstSignalChoiceBox;
    @FXML
    private ChoiceBox<Signal> secondSignalChoiceBox;
    @FXML
    private ChoiceBox<Servo> firstServoChoiceBox;
    @FXML
    private ChoiceBox<Servo> secondServoChoiceBox;
    @FXML
    private ChoiceBox<TrackSection> leftSectionChoiceBox;
    @FXML
    private ChoiceBox<TrackSection> rightSectionChoiceBox;
    @FXML
    private CheckBox leftSectionOnTracklineCheckBox;
    @FXML
    private ChoiceBox<Trackline> tracklineChoiceBox;
    @FXML
    private CheckBox rightSectionOnTracklineCheckBox;
    @FXML
    private Button saveButton;

    private RoadCrossing roadCrossing;

    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        this.roadCrossing = (RoadCrossing) tracksideObject;
        textField.setText(roadCrossing.getId());
        this.tableView = tableView;
        this.observableList = observableList;
        this.initialName = roadCrossing.getId();

        leftSectionOnTracklineCheckBox.setDisable(true);
        rightSectionOnTracklineCheckBox.setDisable(true);

        ObservableList<Signal> signals = FXCollections.observableArrayList(Model.getSignals().values().stream().filter(signal -> signal.getSignalType() == SignalType.ROAD_CROSSING_SIGNAL).collect(Collectors.toList()));
        signals.add(Signal.EMPTY_SIGNAL);

        firstSignalChoiceBox.setItems(signals);
        secondSignalChoiceBox.setItems(signals);

        if (roadCrossing.getFirstSignal() != null) {
            firstSignalChoiceBox.getSelectionModel().select(roadCrossing.getFirstSignal());
        } else {
            firstSignalChoiceBox.getSelectionModel().select(Signal.EMPTY_SIGNAL);
        }

        if (roadCrossing.getSecondSignal() != null) {
            secondSignalChoiceBox.getSelectionModel().select(roadCrossing.getSecondSignal());
        } else {
            secondSignalChoiceBox.getSelectionModel().select(Signal.EMPTY_SIGNAL);
        }

        ObservableList<Servo> servos = FXCollections.observableArrayList(Model.getServos().values());
        servos.add(Servo.EMPTY_SERVO);
        firstServoChoiceBox.setItems(servos);
        secondServoChoiceBox.setItems(servos);

        if (roadCrossing.getFirstServo() != null) {
            firstServoChoiceBox.getSelectionModel().select(roadCrossing.getFirstServo());
        } else {
            firstServoChoiceBox.getSelectionModel().select(Servo.EMPTY_SERVO);
        }

        if (roadCrossing.getSecondServo() != null) {
            secondServoChoiceBox.getSelectionModel().select(roadCrossing.getSecondServo());
        } else {
            secondServoChoiceBox.getSelectionModel().select(Servo.EMPTY_SERVO);
        }

        ObservableList<Trackline> tracklines = FXCollections.observableArrayList(Model.getTracklines());
        ObservableList<TrackSection> sections = FXCollections.observableArrayList(Model.getTrackSections().values());
        sections.add(TrackSection.EMPTY_TRACK_SECTION);
        tracklineChoiceBox.setItems(tracklines);
        leftSectionChoiceBox.setItems(sections);
        rightSectionChoiceBox.setItems(sections);
        TrackSection leftSection = roadCrossing.getLeftSection();
        TrackSection rightSection = roadCrossing.getRightSection();

        if (leftSection instanceof TracklineBlockSection) {
            leftSectionOnTracklineCheckBox.setDisable(false);
            leftSectionOnTracklineCheckBox.setSelected(true);
            tracklineChoiceBox.setValue(((TracklineBlockSection) leftSection).getTrack());
            leftSectionChoiceBox.setItems(FXCollections.observableArrayList(((TracklineBlockSection) leftSection).getTrack().getBlockSections()));
            leftSectionChoiceBox.getSelectionModel().select(leftSection);
        } else if (leftSection != null) {
            leftSectionChoiceBox.getSelectionModel().select(leftSection);
        } else {
            leftSectionChoiceBox.getSelectionModel().select(TrackSection.EMPTY_TRACK_SECTION);
        }

        if (rightSection instanceof TracklineBlockSection) {
            rightSectionOnTracklineCheckBox.setDisable(false);
            rightSectionOnTracklineCheckBox.setSelected(true);
            tracklineChoiceBox.setValue(((TracklineBlockSection) rightSection).getTrack());
            rightSectionChoiceBox.setItems(FXCollections.observableArrayList(((TracklineBlockSection) rightSection).getTrack().getBlockSections()));
            rightSectionChoiceBox.getSelectionModel().select(rightSection);
        } else if (rightSection != null) {
            rightSectionChoiceBox.getSelectionModel().select(rightSection);
        } else {
            rightSectionChoiceBox.getSelectionModel().select(TrackSection.EMPTY_TRACK_SECTION);
        }

        tracklineChoiceBox.setOnAction(event -> {
            leftSectionOnTracklineCheckBox.setDisable(false);
            rightSectionOnTracklineCheckBox.setDisable(false);
        });

        leftSectionOnTracklineCheckBox.setOnAction(event -> {
            if (leftSectionOnTracklineCheckBox.isSelected()) {
                leftSectionChoiceBox.setItems(FXCollections.observableArrayList(tracklineChoiceBox.getValue().getBlockSections()));
                leftSectionChoiceBox.getSelectionModel().select(0);
            } else {
                leftSectionChoiceBox.setItems(sections);
                leftSectionChoiceBox.getSelectionModel().select(TrackSection.EMPTY_TRACK_SECTION);
            }
        });

        rightSectionOnTracklineCheckBox.setOnAction(event -> {
            if (rightSectionOnTracklineCheckBox.isSelected()) {
                rightSectionChoiceBox.setItems(FXCollections.observableArrayList(tracklineChoiceBox.getValue().getBlockSections()));
                rightSectionChoiceBox.getSelectionModel().select(0);
            } else {
                rightSectionChoiceBox.setItems(sections);
                rightSectionChoiceBox.getSelectionModel().select(TrackSection.EMPTY_TRACK_SECTION);
            }
        });
        if (!roadCrossing.getId().equals(RoadCrossing.INITIAL_ROAD_CROSSING_NAME)) {
            textField.setDisable(true);
        } else {
            textField.setOnMouseClicked(event -> {
                textField.selectAll();
            });
        }

        saveButton.setOnAction(this::saveAndClose);
    }

    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        if (!isNameValid(Model.getRoadCrossings(), RoadCrossing.INITIAL_ROAD_CROSSING_NAME)) {
            return;
        }
        roadCrossing.setId(textField.getText());
        roadCrossing.setFirstSignal(firstSignalChoiceBox.getValue());
        roadCrossing.setSecondSignal(secondSignalChoiceBox.getValue());
        roadCrossing.setFirstServo(firstServoChoiceBox.getValue());
        roadCrossing.setSecondServo(secondServoChoiceBox.getValue());
        roadCrossing.setLeftSection(leftSectionChoiceBox.getValue());
        roadCrossing.setRightSection(rightSectionChoiceBox.getValue());

        updateModelAndClose(Model.getRoadCrossings(), roadCrossing);
    }
}


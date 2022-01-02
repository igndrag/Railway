package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.model.Model;
import NATrain.routes.Trackline;
import NATrain.routes.TracklineBlockSection;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.customObjects.CustomObjectType;
import NATrain.trackSideObjects.customObjects.PolarityChanger;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;

public class PolarityChangerRedactorController extends TracksideObjectRedactorController {
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
    private ChoiceBox<Switch> switchChoiceBox;
    @FXML
    private Button saveButton;

    private PolarityChanger polarityChanger;

    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        this.polarityChanger = (PolarityChanger) tracksideObject;
        textField.setText(polarityChanger.getId());
        this.tableView = tableView;
        this.observableList = observableList;
        this.initialName = polarityChanger.getId();

        leftSectionOnTracklineCheckBox.setDisable(true);
        rightSectionOnTracklineCheckBox.setDisable(true);

        ObservableList<Switch> switches = FXCollections.observableArrayList(Model.getSwitches().values());
        switches.add(Switch.EMPTY_SWITCH);

        ObservableList<Trackline> tracklines = FXCollections.observableArrayList(Model.getTracklines());
        ObservableList<TrackSection> sections = FXCollections.observableArrayList(Model.getTrackSections().values());
        sections.add(TrackSection.EMPTY_TRACK_SECTION);
        tracklineChoiceBox.setItems(tracklines);
        leftSectionChoiceBox.setItems(sections);
        rightSectionChoiceBox.setItems(sections);
        TrackSection leftSection = polarityChanger.getLeftTrackSection();
        TrackSection rightSection = polarityChanger.getRightTrackSection();

        switch (polarityChanger.getType()) {
            case SECTION_POLARITY_CHANGER:
            case LOCO_POLARITY_CHANGER:
                switchChoiceBox.setDisable(true);
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
                break;
            case SWITCH_POLARITY_CHANGER:
                tracklineChoiceBox.setDisable(true);
                leftSectionChoiceBox.setDisable(true);
                rightSectionChoiceBox.setDisable(true);

                switchChoiceBox.setItems(switches);
                if (polarityChanger.getAssociatedSwitch() == null) {
                    switchChoiceBox.setValue(Switch.EMPTY_SWITCH);
                } else {
                    switchChoiceBox.setValue(polarityChanger.getAssociatedSwitch());
                }
                break;
        }

        if (!polarityChanger.getId().equals(PolarityChanger.INITIAL_POLARITY_CHANGER_NAME)) {
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
        if (!isNameValid(Model.getPolarityChangers(), PolarityChanger.INITIAL_POLARITY_CHANGER_NAME)) {
            return;
        }
        polarityChanger.setId(textField.getText());

        if (polarityChanger.getType() == CustomObjectType.SECTION_POLARITY_CHANGER
            || polarityChanger.getType() == CustomObjectType.LOCO_POLARITY_CHANGER) {
            polarityChanger.setLeftTrackSection(leftSectionChoiceBox.getValue());
            polarityChanger.setRightTrackSection(rightSectionChoiceBox.getValue());
        }

        if (polarityChanger.getType() == CustomObjectType.SWITCH_POLARITY_CHANGER) {
            polarityChanger.setAssociatedSwitch(switchChoiceBox.getValue());
        }

        updateModelAndClose(Model.getPolarityChangers(), polarityChanger);
    }
}


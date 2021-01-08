package NATrain.UI.tracks;

import NATrain.UI.UIUtils;
import NATrain.model.Model;
import NATrain.routes.Track;
import NATrain.routes.TrackBlockSection;
import NATrain.routes.TrackBlockingType;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.SignalType;
import NATrain.trackSideObjects.TrackSection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TrackRedactorController {

    @FXML
    private ToggleButton oddToggleButton;
    @FXML
    private ToggleButton evenToggleButton;
    @FXML
    private Button addButton;
    @FXML
    private ChoiceBox<TrackBlockingType> blockingTypeChoiceBox;
    @FXML
    private RadioButton bidirectionalRadioButton;
    @FXML
    private TextField trackNameTextField;
    @FXML
    private TextField blockSectionPrefixTextField;
    @FXML
    private TextField blockSectionSuffixTextField;
    @FXML
    private TextField blockSectionCountTextField;
    @FXML
    private Button deleteLastButton;
    @FXML
    private Button clearAllButton;
    @FXML
    private TextField signalPrefixTextField;
    @FXML
    private ChoiceBox<Signal> normalDirectionArrivalSignalChoiceBox;
    @FXML
    private ChoiceBox<Signal> reversedDirectionArrivalSignalChoiceBox;
    @FXML
    private TableView<TrackBlockSection> blockSectionsTableView;
    @FXML
    private TableColumn<TrackBlockSection, String> idColumn;
    @FXML
    private TableColumn<TrackBlockSection, String> normalDirectionSignalColumn;
    @FXML
    private TableColumn<TrackBlockSection, String> reversedDirectionSignalColumn;
    @FXML
    private Button saveButton;

    private Track track;
    private String initialName;
    private TableView<Track> trackTableView;

    public void initialize(Track track, TableView<Track> trackTableView) {
        this.track = track;
        this.initialName = track.getId();
        this.trackTableView = trackTableView;

        ToggleGroup toggleGroup = new ToggleGroup();
        evenToggleButton.setToggleGroup(toggleGroup);
        oddToggleButton.setToggleGroup(toggleGroup);
        evenToggleButton.setSelected(true);

        trackNameTextField.setText(track.getId());
        bidirectionalRadioButton.setSelected(track.isBidirectional());
        if (!track.isBidirectional()) {
            reversedDirectionArrivalSignalChoiceBox.setDisable(true);
        }
        bidirectionalRadioButton.setOnAction(event -> {
            if (bidirectionalRadioButton.isSelected()) {
                track.setBidirectional(true);
                reversedDirectionArrivalSignalChoiceBox.setDisable(false);
            } else {
                track.setBidirectional(false);
                reversedDirectionArrivalSignalChoiceBox.getSelectionModel().clearSelection();
                reversedDirectionArrivalSignalChoiceBox.setDisable(true);
            }
        });

        blockingTypeChoiceBox.getSelectionModel().select(track.getTrackBlockingType());
        addButton.setOnAction(event -> {
            createBlockSections();
        });

        ObservableList<Signal> signals = FXCollections.observableArrayList(Model.getSignals().values());
        normalDirectionArrivalSignalChoiceBox.setItems(signals);
        normalDirectionArrivalSignalChoiceBox.getSelectionModel().select(track.getNormalDirectionArrivalSignal());
        reversedDirectionArrivalSignalChoiceBox.setItems(signals);
        reversedDirectionArrivalSignalChoiceBox.getSelectionModel().select(track.getReversedDirectionArrivalSignal());

        blockingTypeChoiceBox.setItems(FXCollections.observableArrayList(TrackBlockingType.values()));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        normalDirectionSignalColumn.setCellValueFactory(new PropertyValueFactory<>("normalDirectionSignal"));
        reversedDirectionSignalColumn.setCellValueFactory(new PropertyValueFactory<>("reversedDirectionSignal"));

        blockSectionsTableView.setItems(FXCollections.observableArrayList(track.getBlockSections()));
    }

    private void createBlockSections() {
        try {
            int sectionsCount = Integer.parseInt(blockSectionCountTextField.getText());
            boolean even;
            if (evenToggleButton.isSelected()) {
                even = true;
            } else {
                even = false;
            }
            for (int i = sectionsCount; i > 0; i --) {
                StringBuilder blockSectionName = new StringBuilder();
                blockSectionName.append(blockSectionPrefixTextField.getText());
                if (even) {
                    blockSectionName.append(2*i);}
                else {
                    blockSectionName.append(2*i -1);
                }
                blockSectionName.append(blockSectionSuffixTextField.getText());
                TrackBlockSection blockSection = new TrackBlockSection(track, new TrackSection(blockSectionName.toString()));
                if (i != sectionsCount) {
                    StringBuilder normalDirectionSignalName = new StringBuilder();
                    normalDirectionSignalName.append(signalPrefixTextField.getText());
                    if (even) {
                        normalDirectionSignalName.append(2*i);
                    } else {
                        normalDirectionSignalName.append(2*i - 1);
                    }
                    normalDirectionSignalName.append("ND");
                    blockSection.setNormalDirectionSignal(new Signal(normalDirectionSignalName.toString(), SignalType.TRACK));
                }
                if (bidirectionalRadioButton.isSelected()) {
                    if (i > 1) {
                        StringBuilder reversedDirectionSignalName = new StringBuilder();
                        reversedDirectionSignalName.append(signalPrefixTextField.getText());
                        if (even) {
                            reversedDirectionSignalName.append(2*(i - 1) - 1);
                        } else {
                            reversedDirectionSignalName.append(2*(i - 1));
                        }
                        reversedDirectionSignalName.append("RD");
                        blockSection.setBidirectional(true);
                        blockSection.setReversedDirectionSignal(new Signal(reversedDirectionSignalName.toString(), SignalType.TRACK));
                    }
                }
                blockSectionsTableView.getItems().add(blockSection);
                track.getBlockSections().add(blockSection);
            }
            blockSectionsTableView.refresh();
        } catch (NumberFormatException e) {
            UIUtils.showAlert("Incorrect Block Sections Count!");
        }
    }

    @FXML
    private void saveAndClose() {
        if (!isTrackNameValid()) {
            return;
        }
        track.setId(trackNameTextField.getText());
        track.setBidirectional(bidirectionalRadioButton.isSelected());
        track.setTrackBlockingType(blockingTypeChoiceBox.getValue());
        track.setNormalDirectionArrivalSignal(normalDirectionArrivalSignalChoiceBox.getValue());
        track.setReversedDirectionArrivalSignal(reversedDirectionArrivalSignalChoiceBox.getValue());
        trackTableView.refresh();
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void deleteLast() {
        int lastSectionIndex = track.getBlockSectionCount() - 1;
        if (lastSectionIndex > 0) {
            track.getBlockSections().remove(lastSectionIndex);
            blockSectionsTableView.getItems().remove(lastSectionIndex);
        }
    }

    @FXML
    private void deleteAll() {
        track.getBlockSections().clear();
        blockSectionsTableView.getItems().clear();
    }

    private boolean isTrackNameValid() {
        String newTrackName = trackNameTextField.getText();
        if (Model.getTracks().stream().filter(tr -> {
            return tr != track;
        }).map(Track::getId).anyMatch(name -> name.equals(newTrackName))) {
            UIUtils.showAlert(String.format("Track %s already exists.", newTrackName));
            return false;
        }
        return true;
    }
}

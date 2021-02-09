package NATrain.UI.tracks;

import NATrain.UI.UIUtils;
import NATrain.model.Model;
import NATrain.routes.RouteDirection;
import NATrain.routes.Track;
import NATrain.routes.TrackBlockSection;
import NATrain.routes.TrackBlockingType;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalLampType;
import NATrain.trackSideObjects.signals.SignalType;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.сontrolModules.OutputChannel;
import NATrain.сontrolModules.OutputChannelType;
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
        if (track.getNormalDirection() == RouteDirection.EVEN) {
            evenToggleButton.setSelected(true);
        } else {
            oddToggleButton.setSelected(true);
        }

        if (track.getBlockSections().size() > 0) {
            evenToggleButton.setDisable(true);
            oddToggleButton.setDisable(true);
        }

        evenToggleButton.setOnAction(event -> {
            if (evenToggleButton.isSelected()) {
                track.setNormalDirection(RouteDirection.EVEN);
            }
        });

        oddToggleButton.setOnAction(event -> {
            if (oddToggleButton.isSelected()) {
                track.setNormalDirection(RouteDirection.ODD);
            }
        });

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
                TrackBlockSection blockSection = new TrackBlockSection(track, blockSectionName.toString());
                if (i != sectionsCount) {
                    StringBuilder normalDirectionSignalName = new StringBuilder();
                    normalDirectionSignalName.append(signalPrefixTextField.getText());
                    if (even) {
                        normalDirectionSignalName.append(2*i);
                    } else {
                        normalDirectionSignalName.append(2*i - 1);
                    }
                    normalDirectionSignalName.append("ND");
                    Signal normalDirectionSignal = new Signal(normalDirectionSignalName.toString(), SignalType.TRACK);
                    switch (blockingTypeChoiceBox.getValue()) {
                        case AUTOMATIC_THREE_SIGNAL_BLOCKING: {
                            normalDirectionSignal.getLamps().put(SignalLampType.RED_LAMP,
                                    new OutputChannel(OutputChannelType.TRACK_SIGNAL_LAMP_OUTPUT, normalDirectionSignal, SignalLampType.RED_LAMP));
                            normalDirectionSignal.getLamps().put(SignalLampType.YELLOW_LAMP,
                                    new OutputChannel(OutputChannelType.TRACK_SIGNAL_LAMP_OUTPUT, normalDirectionSignal, SignalLampType.YELLOW_LAMP));
                            normalDirectionSignal.getLamps().put(SignalLampType.GREEN_LAMP,
                                    new OutputChannel(OutputChannelType.TRACK_SIGNAL_LAMP_OUTPUT, normalDirectionSignal, SignalLampType.GREEN_LAMP));
                            break;
                        }
                    }
                    blockSection.setNormalDirectionSignal(normalDirectionSignal);
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
                        Signal reversedDirectionSignal = new Signal(reversedDirectionSignalName.toString(), SignalType.TRACK);
                        switch (blockingTypeChoiceBox.getValue()) {
                            case AUTOMATIC_THREE_SIGNAL_BLOCKING: {
                                reversedDirectionSignal.getLamps().put(SignalLampType.RED_LAMP,
                                        new OutputChannel(OutputChannelType.TRACK_SIGNAL_LAMP_OUTPUT, reversedDirectionSignal, SignalLampType.RED_LAMP));
                                reversedDirectionSignal.getLamps().put(SignalLampType.YELLOW_LAMP,
                                        new OutputChannel(OutputChannelType.TRACK_SIGNAL_LAMP_OUTPUT, reversedDirectionSignal, SignalLampType.YELLOW_LAMP));
                                reversedDirectionSignal.getLamps().put(SignalLampType.GREEN_LAMP,
                                        new OutputChannel(OutputChannelType.TRACK_SIGNAL_LAMP_OUTPUT, reversedDirectionSignal, SignalLampType.GREEN_LAMP));
                                break;
                            }
                        }
                        blockSection.setReversedDirectionSignal(reversedDirectionSignal);
                    }
                }
                blockSectionsTableView.getItems().add(blockSection);
                track.getBlockSections().add(blockSection);
             }
            track.getBlockSections().get(track.getBlockSectionCount() - 1).setLastInNormalDirection(true);
            track.getBlockSections().get(0).setLastInReversedDirection(true);
            blockSectionsTableView.refresh();
            evenToggleButton.setDisable(true);
            oddToggleButton.setDisable(true);
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
        } else {
            evenToggleButton.setDisable(false);
            oddToggleButton.setDisable(false);
        }
    }

    @FXML
    private void deleteAll() {
        track.getBlockSections().clear();
        blockSectionsTableView.getItems().clear();
        evenToggleButton.setDisable(false);
        oddToggleButton.setDisable(false);
    }

    private boolean isTrackNameValid() {
        String newTrackName = trackNameTextField.getText();
        if (Model.getTracks().stream().filter(tr -> tr != track).map(Track::getId).anyMatch(name -> name.equals(newTrackName))) {
            UIUtils.showAlert(String.format("Track %s already exists.", newTrackName));
            return false;
        }
        return true;
    }
}

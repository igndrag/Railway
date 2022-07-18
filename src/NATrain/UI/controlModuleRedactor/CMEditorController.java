package NATrain.UI.controlModuleRedactor;

import NATrain.UI.UIUtils;
import NATrain.model.Model;
import NATrain.routes.Trackline;
import NATrain.trackSideObjects.*;
import NATrain.trackSideObjects.customObjects.OnOffObject;
import NATrain.trackSideObjects.customObjects.PolarityChanger;
import NATrain.trackSideObjects.customObjects.Servo;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalLampType;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.utils.UtilFunctions;
import NATrain.сontrolModules.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class CMEditorController {

    @FXML
    private ChoiceBox<Trackline> inputTrackChoiceBox;
    @FXML
    private ChoiceBox<Trackline> outputTrackChoiceBox;
    @FXML
    private TableView<InputChannel> inputsTableView;
    @FXML
    private TableColumn<InputChannel, Integer> inputNumberColumn;
    @FXML
    private TableColumn<InputChannel, String> inputTypeColumn;
    @FXML
    private TableColumn<InputChannel, String> inputObjectColumn;
    @FXML
    private TableView<OutputChannel> outputsTableView;
    @FXML
    private TableColumn<OutputChannel, Integer> outputNumberColumn;
    @FXML
    private TableColumn<OutputChannel, String> outputTypeColumn;
    @FXML
    private TableColumn<OutputChannel, SignalLampType> lampColumn;
    @FXML
    private TableColumn<OutputChannel, String> outputObjectColumn;
    @FXML
    private Button addInputButton;
    @FXML
    private Button deleteInputButton;
    @FXML
    private ChoiceBox<InputChannelType> inputTypeChoiceBox;
    @FXML
    private ChoiceBox<TracksideObject> inputObjectChoiceBox;
    @FXML
    private TextField inputNumberTextField;
    @FXML
    private Button addOutputButton;
    @FXML
    private ChoiceBox<OutputChannelType> outputTypeChoiceBox;
    @FXML
    private ChoiceBox<TracksideObject> outputObjectChoiceBox;
    @FXML
    private TextField outputNumberTextField;
    @FXML
    private ChoiceBox<SignalLampType> lampTypeChoiceBox;

    private ControlModule controlModule;
    private TableView<ControlModule> tableView;
    private ObservableList<InputChannel> inputs;
    private ObservableList<OutputChannel> outputs;

    @FXML
    private Label CMTypeLabel;

    public void initialize(ControlModule controlModule, TableView<ControlModule> tableView) {
        this.tableView = tableView;
        this.controlModule = controlModule;
        CMTypeLabel.setText(controlModule.toString());
        inputs = FXCollections.observableArrayList(controlModule.getInputChannels().values());
        outputs = FXCollections.observableArrayList(controlModule.getOutputChannels().values());
        ObservableList<TracksideObject> trackSections = FXCollections.observableArrayList(Model.getTrackSections().values());
        ObservableList<TracksideObject> stationTracks = FXCollections.observableArrayList(Model.getStationTracks().values());
        ObservableList<TracksideObject> switches = FXCollections.observableArrayList(Model.getSwitches().values());
        ObservableList<TracksideObject> signals = FXCollections.observableArrayList(Model.getSignals().values());
        ObservableList<Trackline> tracklines = FXCollections.observableArrayList(Model.getTracklines());
        ObservableList<TracksideObject> servos = FXCollections.observableArrayList(Model.getServos().values());
        ObservableList<TracksideObject> polarityChangers = FXCollections.observableArrayList(Model.getPolarityChangers().values());
        ObservableList<TracksideObject> onOffObjects = FXCollections.observableArrayList(Model.getOnOffObjects().values());

        inputTypeChoiceBox.setItems(FXCollections.observableArrayList(InputChannelType.values()));
        inputTypeChoiceBox.getItems().remove(InputChannelType.ON_OFF_SELF_CHECK);

        inputTrackChoiceBox.setItems(tracklines);
        inputTrackChoiceBox.setDisable(true);

        inputTypeChoiceBox.setOnAction(event -> {
            switch (inputTypeChoiceBox.getValue()) {
                case SWITCH_PLUS:
                case SWITCH_MINUS:
                    inputObjectChoiceBox.setItems(switches);
                    inputTrackChoiceBox.getSelectionModel().clearSelection();
                    inputTrackChoiceBox.setDisable(true);
                    break;
                case TRACK_SECTION:
                    inputObjectChoiceBox.setItems(trackSections);
                    inputTrackChoiceBox.setDisable(true);
                    break;
                case STATION_TRACK:
                    inputObjectChoiceBox.setItems(stationTracks);
                    inputTrackChoiceBox.setDisable(true);
                    break;
                case BLOCK_SECTION:
                    inputTrackChoiceBox.setDisable(false);
                    inputObjectChoiceBox.getItems().clear();
                    break;
            }
        });

        inputsTableView.setItems(inputs);
        inputNumberColumn.setCellValueFactory(new PropertyValueFactory<>("chNumber"));
        inputTypeColumn.setCellValueFactory(new PropertyValueFactory<>("channelType"));
        inputObjectColumn.setCellValueFactory(new PropertyValueFactory<>("tracksideObject"));

        inputTrackChoiceBox.setOnAction(event -> {
            if (!inputTrackChoiceBox.getSelectionModel().isEmpty()) {
                inputObjectChoiceBox.setItems(FXCollections.observableArrayList(inputTrackChoiceBox.getValue().getBlockSections()));
            }
        });

        outputTypeChoiceBox.setItems(FXCollections.observableArrayList(OutputChannelType.values()));
        lampTypeChoiceBox.setDisable(true);

        outputTrackChoiceBox.setItems(tracklines);
        outputTrackChoiceBox.setDisable(true);

        outputTypeChoiceBox.setOnAction(event -> {
            switch (outputTypeChoiceBox.getValue()) {
                case SWITCH_TO_PLUS:
                case SWITCH_TO_MINUS:
                    lampTypeChoiceBox.getSelectionModel().clearSelection();
                    lampTypeChoiceBox.setDisable(true);
                    outputObjectChoiceBox.setItems(switches);
                    outputTrackChoiceBox.getSelectionModel().clearSelection();
                    outputTrackChoiceBox.setDisable(true);
                    break;
                case SIGNAL_LAMP_OUTPUT:
                    outputObjectChoiceBox.setItems(signals);
                    lampTypeChoiceBox.setDisable(false);
                    outputTrackChoiceBox.getSelectionModel().clearSelection();
                    outputTrackChoiceBox.setDisable(true);
                    lampTypeChoiceBox.getSelectionModel().clearSelection();
                    break;
                case TRACK_SIGNAL_LAMP_OUTPUT:
                    outputObjectChoiceBox.getItems().clear();
                    lampTypeChoiceBox.getItems().clear();
                    lampTypeChoiceBox.setDisable(false);
                    outputTrackChoiceBox.setDisable(false);
                    break;
                case SERVO:
                    lampTypeChoiceBox.getSelectionModel().clearSelection();
                    lampTypeChoiceBox.setDisable(true);
                    outputObjectChoiceBox.setItems(servos);
                    outputTrackChoiceBox.getSelectionModel().clearSelection();
                    outputTrackChoiceBox.setDisable(true);
                    break;
                case TOGGLE:
                    lampTypeChoiceBox.getSelectionModel().clearSelection();
                    lampTypeChoiceBox.setDisable(true);
                    outputObjectChoiceBox.setItems(polarityChangers);
                    outputTrackChoiceBox.getSelectionModel().clearSelection();
                    outputTrackChoiceBox.setDisable(true);
                    break;
                case ON_OFF:
                    lampTypeChoiceBox.getSelectionModel().clearSelection();
                    lampTypeChoiceBox.setDisable(true);
                    outputObjectChoiceBox.setItems(onOffObjects);
                    outputTrackChoiceBox.getSelectionModel().clearSelection();
                    outputTrackChoiceBox.setDisable(true);
                    break;
            }
        });

        outputTrackChoiceBox.setOnAction(event -> {
            if (!outputTrackChoiceBox.getSelectionModel().isEmpty()) {
                outputObjectChoiceBox.setItems(FXCollections.observableArrayList(outputTrackChoiceBox.getValue().getSignals()));
            }
        });

        outputObjectChoiceBox.setOnAction(event -> {
            if (!outputTypeChoiceBox.getSelectionModel().isEmpty()) {
                if (outputTypeChoiceBox.getValue() == OutputChannelType.SIGNAL_LAMP_OUTPUT || outputTypeChoiceBox.getValue() == OutputChannelType.TRACK_SIGNAL_LAMP_OUTPUT) {
                    if (!outputObjectChoiceBox.getSelectionModel().isEmpty()) {
                        lampTypeChoiceBox.setItems(FXCollections.observableArrayList(
                                ((Signal) outputObjectChoiceBox.getValue()).getLamps().keySet()));
                    }
                }
            }
        });

        outputsTableView.setItems(outputs);
        outputNumberColumn.setCellValueFactory(new PropertyValueFactory<>("chNumber"));
        outputTypeColumn.setCellValueFactory(new PropertyValueFactory<>("channelType"));
        lampColumn.setCellValueFactory(new PropertyValueFactory<>("lampType"));
        outputObjectColumn.setCellValueFactory(new PropertyValueFactory<>("tracksideObject"));
    }

    @FXML
    private void addInputChannel() {
        if (inputTypeChoiceBox.getSelectionModel().isEmpty() || inputObjectChoiceBox.getSelectionModel().isEmpty()) {
            return;
        }
        Integer chNumber = UtilFunctions.parseIfPositiveNumeric(inputNumberTextField.getText());
        if (0 > chNumber || chNumber >= controlModule.getInputsCount()) {
            UIUtils.showAlert("Invalid channel number.");
            return;
        }

        TracksideObject object = inputObjectChoiceBox.getValue();
        InputChannel inputChannel = null;
        switch (inputTypeChoiceBox.getValue()) {
            case TRACK_SECTION:
            case BLOCK_SECTION:
            case STATION_TRACK:
                TrackSection trackSection = (TrackSection) object;
                inputChannel = new InputChannel(InputChannelType.TRACK_SECTION, trackSection);
                break;
            case SWITCH_PLUS:
                inputChannel = ((Switch) object).getPlusInputChannel();
                break;
            case SWITCH_MINUS:
                inputChannel = ((Switch) object).getMinusInputChannel();
                break;
        }

        if (inputsTableView.getItems().contains(inputChannel)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(String.format("This channel is already configured on %d channel.", chNumber));
            alert.setContentText("Replace channel number?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                inputChannel.setChNumber(chNumber);
                inputNumberTextField.clear();
                inputsTableView.getItems().sort(Comparator.comparingInt(InputChannel::getChNumber));
            }
            return;
        }

        if (controlModule.getInputChannels().containsKey(chNumber)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(String.format("The channel %d is already configured.", chNumber));
            alert.setContentText("Replace?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                InputChannel channelForDelete = controlModule.getInputChannels().get(chNumber);
                channelForDelete.setModule(null);
                channelForDelete.setChNumber(-1);
                inputsTableView.getItems().remove(channelForDelete);
            } else {
                return;
            }
        }

        if (inputChannel != null && inputChannel.getModule() != null && inputChannel.getModule() != controlModule) { //configured to another module
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(String.format("This channel is already configured on %d channel in %s module.", chNumber, inputChannel.getModule().getId()));
            alert.setContentText("Replace module?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                inputChannel.getModule().getInputChannels().remove(inputChannel.getChNumber());
            } else {
                return;
            }
        }
        if (inputChannel != null) {
        inputChannel.setChNumber(chNumber);
        inputChannel.setModule(controlModule);}
        inputsTableView.getItems().add(inputChannel);
        inputsTableView.getItems().sort(Comparator.comparingInt(InputChannel::getChNumber));
        controlModule.getInputChannels().put(chNumber, inputChannel);
        chNumber++;
        inputNumberTextField.setText(chNumber.toString());
        inputObjectChoiceBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void addOutputChannel() {
        if (outputTypeChoiceBox.getSelectionModel().isEmpty()
                || outputObjectChoiceBox.getSelectionModel().isEmpty()
                || outputTypeChoiceBox.getValue() == OutputChannelType.SIGNAL_LAMP_OUTPUT && lampTypeChoiceBox.getSelectionModel().isEmpty()
        ) {
            return;
        }
        Integer chNumber = UtilFunctions.parseIfPositiveNumeric(outputNumberTextField.getText());
        if (0 > chNumber || chNumber >= controlModule.getOutputsCount()) {
            UIUtils.showAlert("Invalid channel number.");
            return;
        }

        TracksideObject object = outputObjectChoiceBox.getValue();
        OutputChannel outputChannel = null;
        switch (outputTypeChoiceBox.getValue()) {
            case SIGNAL_LAMP_OUTPUT:
            case TRACK_SIGNAL_LAMP_OUTPUT:
                Map<SignalLampType, OutputChannel> lamps = ((Signal) object).getLamps();
                switch (lampTypeChoiceBox.getValue()) {
                    case YELLOW_LAMP:
                        outputChannel = lamps.get(SignalLampType.YELLOW_LAMP);
                        break;
                    case GREEN_LAMP:
                        outputChannel = lamps.get(SignalLampType.GREEN_LAMP);
                        break;
                    case RED_LAMP:
                        outputChannel = lamps.get(SignalLampType.RED_LAMP);
                        break;
                    case SECOND_YELLOW_LAMP:
                        outputChannel = lamps.get(SignalLampType.SECOND_YELLOW_LAMP);
                        break;
                    case SECOND_GREEN_LAMP:
                        outputChannel = lamps.get(SignalLampType.SECOND_GREEN_LAMP);
                        break;
                    case BLUE_LAMP:
                        outputChannel = lamps.get(SignalLampType.BLUE_LAMP);
                        break;
                    case WHITE_LAMP:
                        outputChannel = lamps.get(SignalLampType.WHITE_LAMP);
                        break;
                    case GREEN_LINE:
                        outputChannel = lamps.get(SignalLampType.GREEN_LINE);
                        break;
                }
                break;
            case SWITCH_TO_PLUS:
                outputChannel = ((Switch) object).getPlusOutputChannel();
                break;
            case SWITCH_TO_MINUS:
                outputChannel = ((Switch) object).getMinusOutputChannel();
                break;
            case SERVO:
                outputChannel = ((Servo) object).getOutputChannel();
                break;
            case ON_OFF:
                outputChannel = ((OnOffObject) object).getOutputChannel();
                break;
            case TOGGLE:
                outputTypeChoiceBox.setValue(OutputChannelType.ON_OFF);
                if (object instanceof PolarityChanger) {
                    PolarityChanger polarityChanger = (PolarityChanger) object;
                    checkOutputAndAdd(chNumber, polarityChanger.getNormalPolarityOutput());
                    checkOutputAndAdd(chNumber + 1, polarityChanger.getReversedPolarityOutput());
                }
                return;
        }
        checkOutputAndAdd(chNumber, outputChannel);
    }

    private void checkOutputAndAdd(Integer chNumber, OutputChannel outputChannel) {
        if (outputsTableView.getItems().contains(outputChannel)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(String.format("This channel is already configured on %d channel.", chNumber));
            alert.setContentText("Replace channel number?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                outputChannel.setChNumber(chNumber);
                outputNumberTextField.clear();
                outputsTableView.getItems().sort(Comparator.comparingInt(OutputChannel::getChNumber));
            }
            return;
        }

        if (controlModule.getOutputChannels().containsKey(chNumber)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(String.format("The channel %d is already configured.", chNumber));
            alert.setContentText("Replace?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                OutputChannel channelForDelete = controlModule.getOutputChannels().get(chNumber);
                channelForDelete.setModule(null);
                channelForDelete.setChNumber(-1);
                outputsTableView.getItems().remove(channelForDelete);
            } else {
                return;
            }
        }

        if (outputChannel.getModule() != null && outputChannel.getModule() != controlModule) { //configured to another module
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(String.format("This channel is already configured on %d channel in %s module.", chNumber, outputChannel.getModule().getId()));
            alert.setContentText("Replace module?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                outputChannel.getModule().getOutputChannels().remove(outputChannel.getChNumber()); //remove channel from previous module
            } else {
                return;
            }
        }
        outputChannel.setChNumber(chNumber);
        outputChannel.setModule(controlModule);
        outputsTableView.getItems().add(outputChannel);
        outputsTableView.getItems().sort(Comparator.comparingInt(OutputChannel::getChNumber));
        controlModule.getOutputChannels().put(chNumber, outputChannel);
        chNumber++;
        outputNumberTextField.setText(chNumber.toString());
        //outputObjectChoiceBox.getSelectionModel().clearSelection();
        lampTypeChoiceBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void deleteInputChannel() {
        InputChannel channelForDelete = inputsTableView.getSelectionModel().getSelectedItem();
        if (channelForDelete != null) {
            inputsTableView.getItems().remove(channelForDelete);
            controlModule.getInputChannels().remove(channelForDelete.getChNumber());
            channelForDelete.setChNumber(-1);
            channelForDelete.setModule(null);
        }
    }

    @FXML
    private void deleteOutputChannel() {
        OutputChannel channelForDelete = outputsTableView.getSelectionModel().getSelectedItem();
        if (channelForDelete != null) {
            outputsTableView.getItems().remove(channelForDelete);
            controlModule.getOutputChannels().remove(channelForDelete.getChNumber());
            channelForDelete.setChNumber(-1);
            channelForDelete.setModule(null);
        }
    }
}
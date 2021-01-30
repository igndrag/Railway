package NATrain.UI.controlModuleRedactor;

import NATrain.model.Model;
import NATrain.remoteControlModules.RemoteControlModule;
import NATrain.remoteControlModules.SignalControlModule;
import NATrain.remoteControlModules.SwitchControlModule;
import NATrain.remoteControlModules.TrackControlModule;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import NATrain.trackSideObjects.TracksideObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CMEditorController {

    private int chCount;
    private RemoteControlModule controlModule;
    private TableView<RemoteControlModule> tableView;
    private ObservableList<String> observableList;

    private CheckBox[] checkBoxes;
    private ChoiceBox<String>[] choiceBoxes;
    private Map<String, ? extends TracksideObject> modelMap;

    @FXML
    private Label CMTypeLabel;
    @FXML
    private CheckBox checkBoxCh0;
    @FXML
    private CheckBox checkBoxCh1;
    @FXML
    private CheckBox checkBoxCh2;
    @FXML
    private CheckBox checkBoxCh3;
    @FXML
    private CheckBox checkBoxCh4;
    @FXML
    private CheckBox checkBoxCh5;
    @FXML
    private CheckBox checkBoxCh6;
    @FXML
    private CheckBox checkBoxCh7;
    @FXML
    private ChoiceBox<String> choiceBoxCh0;
    @FXML
    private ChoiceBox<String> choiceBoxCh1;
    @FXML
    private ChoiceBox<String> choiceBoxCh2;
    @FXML
    private ChoiceBox<String> choiceBoxCh3;
    @FXML
    private ChoiceBox<String> choiceBoxCh4;
    @FXML
    private ChoiceBox<String> choiceBoxCh5;
    @FXML
    private ChoiceBox<String> choiceBoxCh6;
    @FXML
    private ChoiceBox<String> choiceBoxCh7;
    @FXML
    private Button saveButton;

    public void initialize(RemoteControlModule controlModule, TableView<RemoteControlModule> tableView) {
        checkBoxes = new CheckBox[]{checkBoxCh0, checkBoxCh1, checkBoxCh2, checkBoxCh3, checkBoxCh4, checkBoxCh5, checkBoxCh6, checkBoxCh7};
        choiceBoxes = new ChoiceBox[]{choiceBoxCh0, choiceBoxCh1, choiceBoxCh2, choiceBoxCh3, choiceBoxCh4, choiceBoxCh5, choiceBoxCh6, choiceBoxCh7};
        chCount = controlModule.getChannels().length;
        this.tableView = tableView;
        this.controlModule = controlModule;

        if (controlModule instanceof TrackControlModule) {
            modelMap = Model.getTrackSections();
            CMTypeLabel.setText("Track Control Module");
            observableList = FXCollections.observableArrayList(Model.getTrackSections().values().stream().filter(trackSection -> trackSection.getChannel() == null).map(TrackSection::getId).collect(Collectors.toList()));
        } else if (controlModule instanceof SwitchControlModule) {
            modelMap = Model.getSwitches();
            CMTypeLabel.setText("Switch Control Module");
            observableList = FXCollections.observableArrayList(Model.getSwitches().values().stream().filter(aSwitch -> aSwitch.getChannel() == null).map(Switch::getId).collect(Collectors.toList()));
        } else if (controlModule instanceof SignalControlModule) {
            modelMap = Model.getSignals();
            CMTypeLabel.setText("Signal Control Module");
            observableList = FXCollections.observableArrayList(Model.getSignals().values().stream().filter(signal -> signal.getChannel() == null).map(Signal::getId).collect(Collectors.toList()));
        }
        observableList.add("none");
        for (int i = 0; i < choiceBoxes.length; i++) {
            initChannel(choiceBoxes[i], checkBoxes[i], i);
        }
    }

    private void initChannel(ChoiceBox<String> choiceBox, CheckBox checkBox, int channel) {
        if (channel < chCount) {
            choiceBox.setItems(observableList);
            if (controlModule.getChannels()[channel] != null) {
                String selectedName = controlModule.getChannels()[channel].getId();
                checkBox.setSelected(true);
                if (!choiceBox.getItems().contains(selectedName))
                    choiceBox.getItems().add(selectedName);
                choiceBox.setValue(selectedName);
            } else {
                choiceBox.setValue("none");
                choiceBox.setDisable(true);
            }

            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    choiceBox.setDisable(false);
                } else {
                    choiceBox.setValue("none");
                    choiceBox.setDisable(true);
                }
            });
        } else {
            checkBox.setDisable(true);
            choiceBox.setDisable(true);
        }
    }

    @FXML
    private void saveAndClose() {
        Map<Integer, String> objectsForConfig = new HashMap<>();
        ArrayList<Integer> channelsForClear = new ArrayList<>();
        for (int i = 0; i < chCount; i++) {
            String selectedId = choiceBoxes[i].getSelectionModel().getSelectedItem();
            if (!selectedId.equals("none")) {
                if (objectsForConfig.containsValue(selectedId)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText(String.format("It's impossible to set %s in two ore more channels", choiceBoxes[i].getSelectionModel().getSelectedItem()));
                    alert.show();
                    return;
                } else {
                    objectsForConfig.put(i, choiceBoxes[i].getSelectionModel().getSelectedItem());
                }
            } else {
                channelsForClear.add(i);
            }
        }

        objectsForConfig.forEach((channel, tracksideObjectName) -> {
            TracksideObject tracksideObject = modelMap.get(tracksideObjectName);
         //   tracksideObject.setControlModule(controlModule);
            tracksideObject.setChannel(channel);
            controlModule.setTrackSideObjectOnChannel(tracksideObject, channel);
        });

        channelsForClear.forEach(channel -> {
            if (controlModule.getChannels()[channel] != null) {
                controlModule.getChannels()[channel].setControlModule(null);
                controlModule.getChannels()[channel] = null;
            }
        });
        tableView.refresh();
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

}
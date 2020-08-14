package NATrain.UI.tracksideObjectRedactor;

import NATrain.model.Model;
import NATrain.remoteControlDevice.ControlModule;
import NATrain.trackSideObjects.TrackSection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Comparator;

public class TrackSectionRedactorController { //make generic class

    @FXML
    private TextField textField;
    @FXML
    private Pane trackSectionPreview; // just for future
    @FXML
    private ChoiceBox<String> controlModuleChoiceBox;
    @FXML
    private ChoiceBox<String> channelChoiceBox;

    private TableView<TrackSection> tableView;

    private TrackSection trackSection;

    void init (TrackSection track, TableView<TrackSection> tableView) {
        this.tableView = tableView;
        this.trackSection = track;
        TracksideObjectRedactorController.initializeControlModuleCheckBoxes(trackSection, controlModuleChoiceBox, channelChoiceBox);
        textField.setText(trackSection.getId());
        if (Model.getTrackSections().containsKey(trackSection.getId()))
            textField.setDisable(true);
        else {
            textField.setOnMouseClicked(event -> {
                textField.selectAll();
            });
        }
    }

    @FXML
    private void saveAndClose(ActionEvent actionEvent) {

        if (textField.getText().equals("") || textField.getText().equals("New Track Section")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please type track section name");
            a.show();
            return;
        }

        if (!textField.isDisabled() && Model.getTrackSections().containsKey(trackSection.getId())) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(String.format("Track section with name %s already exists!", textField.getText()));
            a.show();
            return;
        }

        trackSection.setId(textField.getText());
        if (!controlModuleChoiceBox.getValue().equals("none")) {
            ControlModule controlModule = Model.getTrackControlModules().get(Integer.parseInt(controlModuleChoiceBox.getValue()));
            if (!channelChoiceBox.getValue().equals("none")) {
                trackSection.setControlModule(controlModule);
                controlModule.setTrackSideObjectOnChannel(trackSection, Integer.parseInt(channelChoiceBox.getValue()));
                trackSection.setChannel(Integer.parseInt(channelChoiceBox.getValue()));
            }
        }

        Model.getTrackSections().putIfAbsent(trackSection.getId(), trackSection);
        if (!TracksideObjectRedactorController.trackSectionList.contains(trackSection)) {
            TracksideObjectRedactorController.trackSectionList.add(trackSection);
        }
        TracksideObjectRedactorController.trackSectionList.sort(Comparator.comparing(TrackSection::getId));
        Stage thisStage = (Stage)textField.getScene().getWindow();
        tableView.refresh();
        thisStage.close();
    }
}

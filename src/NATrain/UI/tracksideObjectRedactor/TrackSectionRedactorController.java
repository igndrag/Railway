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

    private TableView<TrackSection> tableView;

    private TrackSection trackSection;

    private Boolean edit = false;

    private String initialName;

    void init(TrackSection track, TableView<TrackSection> tableView) {
        this.tableView = tableView;
        this.trackSection = track;

        if (Model.getTrackSections().containsKey(track.getId())) {
            initialName = track.getId();
            edit = true;
        } else {
            edit = false;
        }
        textField.setText(trackSection.getId());
        textField.setOnMouseClicked(event -> {
            textField.selectAll();
        });
    }

    @FXML
    private void saveAndClose(ActionEvent actionEvent) {
        String newTrackName = textField.getText();
        trackSection.setId(newTrackName);

        if (newTrackName.equals("") || newTrackName.equals("New Track Section")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please type track section name");
            a.show();
            return;
        }

        if ((!newTrackName.equals(initialName) || !edit) && Model.getTrackSections().containsKey(newTrackName)) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(String.format("Track section with name %s is already exists!", textField.getText()));
            a.show();
            return;
        }

        if (edit && !textField.getText().equals(initialName)) {
            Model.getTrackSections().remove(initialName);
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

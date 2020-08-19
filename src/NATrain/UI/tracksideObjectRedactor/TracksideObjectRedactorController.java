package NATrain.UI.tracksideObjectRedactor;

import NATrain.UI.tracksideObjectRedactor.TSORedactor;
import NATrain.model.Model;
import NATrain.trackSideObjects.TrackSection;
import NATrain.trackSideObjects.TracksideObject;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.Map;

public abstract class TracksideObjectRedactorController implements TSORedactor {

    @FXML
    protected TextField textField;
    @FXML
    protected Pane preview; // just for future

    protected ObservableList<TracksideObject> observableList;

    protected TableView<TracksideObject> tableView;

    protected Boolean edit = false;

    protected String initialName;


    protected <T extends TracksideObject> void initTextField (Map <String, T> modelMap, T tracksideObject) {
        if (modelMap.containsKey(tracksideObject.getId())) {
            initialName = tracksideObject.getId();
            edit = true;
        } else {
            edit = false;
        }
        textField.setText(tracksideObject.getId());
        textField.setOnMouseClicked(event -> {
            textField.selectAll();
        });
    }

    protected <T extends TracksideObject> void updateModelAndClose(Map<String,T> modelMap, T trackSideObject) {
        if (edit && !textField.getText().equals(initialName)) {
            modelMap.remove(initialName);
        }

        modelMap.putIfAbsent(trackSideObject.getId(), (T)trackSideObject);
        if (!observableList.contains(trackSideObject)) {
            observableList.add(trackSideObject);
        }

        observableList.sort(Comparator.comparing(TracksideObject::getId));
        Stage thisStage = (Stage) textField.getScene().getWindow();
        tableView.refresh();
        thisStage.close();
    }

    protected boolean isNameValid() {
        String newName = textField.getText();
        if (newName.equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please type correct name");
            a.show();
            return false;
        }

        if ((!newName.equals(initialName) || !edit) && Model.getTrackSections().containsKey(newName)) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(String.format("Trackside object with name %s is already exists!", textField.getText()));
            a.show();
            return false;
        }
        return true;
    }
}

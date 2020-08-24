package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.model.Model;
import NATrain.trackSideObjects.TrackSection;
import NATrain.trackSideObjects.TracksideObject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class TrackSectionRedactorController extends TracksideObjectRedactorController { //make generic class

    private TrackSection trackSection;

    @Override
    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        this.trackSection = (TrackSection) tracksideObject;
        this.tableView = tableView;
        this.observableList = observableList;
        initTextField(Model.getTrackSections(), trackSection);
    }

    @FXML
    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        trackSection.setId(textField.getText());
        if (!isNameValid(Model.getTrackSections(), TrackSection.INITIAL_TRACK_SECTION_NAME))
            return;
        updateModelAndClose(Model.getTrackSections(), trackSection);
    }
}

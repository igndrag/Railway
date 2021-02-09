package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.model.Model;
import NATrain.routes.ArrivalDepartureTrack;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.trackSideObjects.TracksideObject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;

public class TrackSectionRedactorController extends TracksideObjectRedactorController {

    @FXML
    private CheckBox arrivalDepartureTrackChoiceBox; //make generic class
    private TrackSection trackSection;

    @Override
    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        this.trackSection = (TrackSection) tracksideObject;
        this.tableView = tableView;
        this.observableList = observableList;
        initTextField(Model.getTrackSections(), trackSection);
        arrivalDepartureTrackChoiceBox.setSelected(trackSection instanceof ArrivalDepartureTrack);
    }

    @FXML
    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        trackSection.setId(textField.getText());
        if (!isNameValid(Model.getTrackSections(), TrackSection.INITIAL_TRACK_SECTION_NAME))
            return;
        if (arrivalDepartureTrackChoiceBox.isSelected()) {

        }

        updateModelAndClose(Model.getTrackSections(), trackSection);
    }
}

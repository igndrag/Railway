package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.UIUtils;
import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.model.Model;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.utils.UtilFunctions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ArrivalDepartureTrackRedactorController extends TracksideObjectRedactorController {

    @FXML
    private ChoiceBox<Signal> evenSignalChoiceBox;
    @FXML
    private ChoiceBox<Signal> oddSignalChoiceBox;
    @FXML
    private TextField lengthTextField;


    private TrackSection trackSection;

    @Override
    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        if (tracksideObject != TrackSection.EMPTY_TRACK_SECTION) {
            this.trackSection = (TrackSection) tracksideObject;
            initTextField(Model.getTrackSections(), trackSection);
        } else {
            textField.setText(TrackSection.INITIAL_TRACK_SECTION_NAME);
        }
        this.tableView = tableView;
        this.observableList = observableList;
        lengthTextField.setText("" + trackSection.getLength());
    }

    @FXML
    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        trackSection.setId(textField.getText());
        if (!isNameValid(Model.getTrackSections(), TrackSection.INITIAL_TRACK_SECTION_NAME)) {
            return;
        }
        int length = UtilFunctions.parseIfPositiveNumeric(lengthTextField.getText());
        if (length == -1) {
            UIUtils.showAlert("Incorrect length.");
            return;
        } else {
            trackSection.setLength(length);
        }
        updateModelAndClose(Model.getTrackSections(), trackSection);
    }
}

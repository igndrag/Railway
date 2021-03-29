package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.UIUtils;
import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.model.Model;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.utils.UtilFunctions;
import NATrain.сontrolModules.InputChannel;
import NATrain.сontrolModules.InputChannelType;
import NATrain.сontrolModules.TrackSectionInputChannel;
import NATrain.сontrolModules.TrackSectionInputType;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TrackSectionRedactorController extends TracksideObjectRedactorController {

    @FXML
    private TextField inputsCountTextField;
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
        if (trackSection.getSubsections().size() > 0) {
           inputsCountTextField.setText(trackSection.getSubsections().size() + "");
           inputsCountTextField.setDisable(true);
        }

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
        int subsectionsCount = UtilFunctions.parseIfPositiveNumeric(inputsCountTextField.getText());
        if (subsectionsCount == -1) {
            UIUtils.showAlert("Incorrect inputs count.");
            return;
        }
        for (int i = 1; i <= subsectionsCount; i++) {
            trackSection.getSubsections().add(new TrackSectionInputChannel(i, TrackSectionInputType.SUBSECTION, trackSection));
        }
        updateModelAndClose(Model.getTrackSections(), trackSection);
    }
}

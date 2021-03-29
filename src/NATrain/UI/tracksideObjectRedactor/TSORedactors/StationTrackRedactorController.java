package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.UIUtils;
import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.model.Model;
import NATrain.routes.StationTrack;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.utils.UtilFunctions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.stream.Collectors;

public class StationTrackRedactorController extends TracksideObjectRedactorController {

    public TextField centralElementCountTextField;
    public RadioButton evenBorderCheckBox;
    public RadioButton oddBorderCheckBox;
    public RadioButton centralElementCheckBox;
    @FXML
    private ChoiceBox<Signal> evenSignalChoiceBox;
    @FXML
    private ChoiceBox<Signal> oddSignalChoiceBox;
    @FXML
    private TextField lengthTextField;


    private StationTrack stationTrack;

    @Override
    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        if (tracksideObject != TrackSection.EMPTY_TRACK_SECTION) {
            this.stationTrack = (StationTrack) tracksideObject;
            initTextField(Model.getStationTracks(), stationTrack);
        } else {
            textField.setText(StationTrack.INITIAL_STATION_TRACK_NAME);
        }
        this.tableView = tableView;
        this.observableList = observableList;
        lengthTextField.setText("" + stationTrack.getLength());
        evenSignalChoiceBox.setItems(FXCollections.observableArrayList(
                Model.getSignals().values().stream().filter(Signal::isEven).collect(Collectors.toList())));
        oddSignalChoiceBox.setItems(FXCollections.observableArrayList(
                Model.getSignals().values().stream().filter(Signal::isOdd).collect(Collectors.toList())));
    }

    @FXML
    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        stationTrack.setId(textField.getText());
        if (!isNameValid(Model.getStationTracks(), StationTrack.INITIAL_STATION_TRACK_NAME)) {
            return;
        }
        int length = UtilFunctions.parseIfPositiveNumeric(lengthTextField.getText());
        if (length == -1) {
            UIUtils.showAlert("Incorrect length.");
            return;
        } else {
            stationTrack.setLength(length);
        }
        stationTrack.setEvenSignal(evenSignalChoiceBox.getValue());
        stationTrack.setOddSignal(oddSignalChoiceBox.getValue());
        updateModelAndClose(Model.getStationTracks(), stationTrack);
    }
}

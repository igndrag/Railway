package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.model.Model;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.SignalState;
import NATrain.trackSideObjects.SignalType;
import NATrain.trackSideObjects.TracksideObject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class SignalRedactorController extends TracksideObjectRedactorController {

    @FXML
    private CheckBox redLampCheckBox;
    @FXML
    private ToggleButton trimmerToggleButton;
    @FXML
    private ToggleButton trackToggleButton;
    @FXML
    private Pane signalPreview; // just for future

    private Signal signal;

    @Override
    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        signal = (Signal) tracksideObject;
        this.tableView = tableView;
        this.observableList = observableList;
        initTextField(Model.getSignals(), signal);

        ToggleGroup toggleGroup = new ToggleGroup();
        trimmerToggleButton.setToggleGroup(toggleGroup);
        trackToggleButton.setToggleGroup(toggleGroup);

        switch (signal.getSignalType()) {
            case TRIMMER:
                trimmerToggleButton.setSelected(true);
                break;
            case TRACK:
                trackToggleButton.setSelected(true);
                redLampCheckBox.setDisable(true);
                break;
        }
        trimmerToggleButton.setOnAction(event -> redLampCheckBox.setDisable(false));
        trackToggleButton.setOnAction(event -> redLampCheckBox.setDisable(true));
    }

    @FXML
    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        signal.setId(textField.getText());
        if (!isNameValid(Model.getSignals(), Signal.INITIAL_SIGNAL_NAME))
            return;
        if (trimmerToggleButton.isSelected())
            signal.setSignalType(SignalType.TRIMMER);
        else
            signal.setSignalType(SignalType.TRACK);

        if (redLampCheckBox.isSelected()) {
            signal.setClosedSignalState(SignalState.RED);
        }

        updateModelAndClose(Model.getSignals(), signal);
    }
}

package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.model.Model;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TracksideObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;

import java.util.Map;
import java.util.stream.Collectors;

public class SwitchRedactorController extends TracksideObjectRedactorController {

    @FXML
    private ChoiceBox<String> paredSwitchChoiceBox;
    @FXML
    private RadioButton paredCheckbox;
    @FXML
    private ToggleButton normalInitialPositionToggleButton;
    @FXML
    private ToggleButton switchedInitialPositionToggleButton;
    @FXML
    private Pane switchPreview; // just for future

    private Switch mySwitch;

    @Override
    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        mySwitch = (Switch) tracksideObject;
        this.tableView = tableView;
        this.observableList = observableList;

        paredCheckbox.setSelected(mySwitch.isPared());
        paredCheckbox.setOnMouseClicked(event -> {
            paredSwitchChoiceBox.setDisable(!paredCheckbox.isSelected());
        });

        paredSwitchChoiceBox.setDisable(!paredCheckbox.isSelected());
        paredSwitchChoiceBox.setItems(FXCollections.observableArrayList(Model.getSwitches().keySet()));
        paredSwitchChoiceBox.getItems().add("none");
        if (!mySwitch.isPared()) {
            paredSwitchChoiceBox.setValue("none");
        } else {
            paredSwitchChoiceBox.setValue(mySwitch.getParedSwitch().getId());
        }
    }

    protected void init(TracksideObject tracksideObject, Map<String, ? extends TracksideObject> modelMap, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        Switch mySwitch = (Switch) tracksideObject;

    }

    @FXML
    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        mySwitch.setId(textField.getText());
        if (!isNameValid())
            return;

        // put logic here for another TSO types
        updateModelAndClose(Model.getSwitches(), mySwitch);
    }
}

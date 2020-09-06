package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.trackSideObjects.SwitchState;
import NATrain.model.Model;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TracksideObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

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
        initTextField(Model.getSwitches(), mySwitch);

        paredCheckbox.setSelected(mySwitch.isPared());
        paredCheckbox.setOnMouseClicked(event -> {
            paredSwitchChoiceBox.setDisable(!paredCheckbox.isSelected());
        });

        ToggleGroup toggleGroup = new ToggleGroup();
        normalInitialPositionToggleButton.setToggleGroup(toggleGroup);
        switchedInitialPositionToggleButton.setToggleGroup(toggleGroup);

        if (mySwitch.getNormalState() == SwitchState.PLUS) {
            normalInitialPositionToggleButton.setSelected(true);
        } else {
            switchedInitialPositionToggleButton.setSelected(true);
        }

        paredSwitchChoiceBox.setDisable(!paredCheckbox.isSelected());
        paredSwitchChoiceBox.setItems(FXCollections.observableArrayList(Model.getSwitches().values().stream().filter(mySwitch -> !mySwitch.isPared() && mySwitch != this.mySwitch).map(Switch::getId).collect(Collectors.toList())));
        paredSwitchChoiceBox.getItems().add("none");
        if (!mySwitch.isPared()) {
            paredSwitchChoiceBox.setValue("none");
        } else {
            paredSwitchChoiceBox.getItems().add(mySwitch.getParedSwitch().getId());
            paredSwitchChoiceBox.setValue(mySwitch.getParedSwitch().getId());
        }
    }

    @FXML
    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        mySwitch.setId(textField.getText());
        if (!isNameValid(Model.getSwitches(), Switch.INITIAL_SWITCH_NAME))
            return;
        if (normalInitialPositionToggleButton.isSelected())
            mySwitch.setNormalState(SwitchState.PLUS);
        else
            mySwitch.setNormalState(SwitchState.MINUS);
        if (!paredCheckbox.isSelected() || paredSwitchChoiceBox.getValue().equals("none")) {
            if (mySwitch.isPared()) {
                mySwitch.getParedSwitch().setParedSwitch(null);
            }
            mySwitch.setParedSwitch(null);
        } else {
            if (mySwitch.isPared() && !mySwitch.getParedSwitch().getId().equals(paredSwitchChoiceBox.getValue())) {
                mySwitch.getParedSwitch().setParedSwitch(null);
            }
            Model.getSwitches().get(paredSwitchChoiceBox.getValue()).setParedSwitch(mySwitch);
            mySwitch.setParedSwitch(Model.getSwitches().get(paredSwitchChoiceBox.getValue()));
        }
        updateModelAndClose(Model.getSwitches(), mySwitch);
    }
}

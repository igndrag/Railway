package NATrain.UI.routeTable;

import NATrain.model.Model;
import NATrain.routes.Route;
import NATrain.trackSideObjects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Comparator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;


public class DepartureRouteEditorController {

    private Route departureRoute;
    private ConcurrentHashMap<Switch, SwitchState> switchStatePositionsMap;
    private String initialName;

    @FXML
    private TextField descriptionTextField;
    @FXML
    private ChoiceBox<Signal> signalChoiceBox;
    @FXML
    private ChoiceBox<TrackSection> TVDS1ChoiceBox;
    @FXML
    private ChoiceBox<TrackSection> TVDS2ChoiceBox;
    @FXML
    private CheckBox maneuverCheckBox;
    @FXML
    private ListView<TrackSection> allTrackListView;
    @FXML
    private ListView<TrackSection> selectedTrackListView;
    @FXML
    private ChoiceBox<Switch> switchChoiceBox;
    @FXML
    private ToggleButton minusToggleButton;
    @FXML
    private ToggleButton plusToggleButton;
    @FXML
    private ListView<String> switchPositionListView;
    @FXML
    private Button addSwitchPositionButton;
    @FXML
    private Button deleteSwitchPositionButton;
    @FXML
    private HBox previewHBox;

    public void initialize(Route departureRoute) {
        this.departureRoute = departureRoute;
        switchStatePositionsMap = new ConcurrentHashMap<>(departureRoute.getSwitchStatePositions());
        initialName = departureRoute.getDescription();
        descriptionTextField.setText(initialName);

        ObservableList<String> switchPositionsObservableList = FXCollections.observableArrayList();
        departureRoute.getSwitchStatePositions().forEach((sw, sp) -> {
            StringBuilder stringBuilder = new StringBuilder();
            if (sp == SwitchState.PLUS)
                stringBuilder.append("+");
            else
                stringBuilder.append("-");
            stringBuilder.append(sw.getId());
            switchPositionsObservableList.add(stringBuilder.toString());
        });
        switchPositionListView.setItems(switchPositionsObservableList);

        signalChoiceBox.setItems(FXCollections.observableArrayList(Model.getSignals().values()));
        ObservableList<TrackSection> trackObservableList = FXCollections.observableArrayList(Model.getTrackSections().values());
        TVDS1ChoiceBox.setItems(trackObservableList);
        TVDS2ChoiceBox.setItems(trackObservableList);

        if (departureRoute.getSignal() != null)
            signalChoiceBox.setValue(departureRoute.getSignal());

        ToggleGroup toggleGroup = new ToggleGroup();
        plusToggleButton.setToggleGroup(toggleGroup);
        minusToggleButton.setToggleGroup(toggleGroup);
        plusToggleButton.setSelected(true);

        switchChoiceBox.setItems(FXCollections.observableArrayList(Model.getSwitches().values()));

        addSwitchPositionButton.setOnAction(event -> {
            if (!switchChoiceBox.getSelectionModel().isEmpty()) {
                SwitchState switchState;
                String switchName = switchChoiceBox.getSelectionModel().getSelectedItem().getId();
                StringBuilder stringBuilder = new StringBuilder();
                if (plusToggleButton.isSelected()) {
                    stringBuilder.append("+");
                    switchState = SwitchState.PLUS;
                } else {
                    stringBuilder.append("-");
                    switchState = SwitchState.MINUS;
                }
                stringBuilder.append(switchName);
                switchPositionListView.getItems().add(stringBuilder.toString());
                switchChoiceBox.getItems().remove(switchChoiceBox.getSelectionModel().getSelectedItem());
                switchStatePositionsMap.put(Model.getSwitches().get(switchName), switchState);
            }
        });

        deleteSwitchPositionButton.setOnAction(event -> {
            if (!switchPositionListView.getSelectionModel().isEmpty()) {
                String switchNameWithPosition = switchPositionListView.getSelectionModel().getSelectedItem();
                String switchName = switchNameWithPosition.substring(1);
                switchPositionListView.getItems().remove(switchNameWithPosition);
                Switch aSwitch = Model.getSwitches().get(switchName);
                switchStatePositionsMap.remove(aSwitch);
                switchChoiceBox.getItems().add(aSwitch);
                switchChoiceBox.getItems().sort(Comparator.comparing(TracksideObject::getId));
            }
        });

        ObservableList<TrackSection> allTrackSections = FXCollections.observableArrayList(Model.getTrackSections().values());
        allTrackSections.sort(Comparator.comparing(TracksideObject::getId));
        allTrackListView.setItems(allTrackSections);

        ObservableList<String> selectedTrackSections = FXCollections.observableArrayList();
        departureRoute.getOccupationalOrder().stream().map(TracksideObject::getId).forEach(trackName -> {
            allTrackSections.remove(trackName);
            selectedTrackSections.add(trackName);
        });

    }

    @FXML
    private void swipeRight() {
        if (!allTrackListView.getSelectionModel().isEmpty()) {
            TrackSection trackSection = allTrackListView.getSelectionModel().getSelectedItem();
            allTrackListView.getItems().remove(trackSection);
            selectedTrackListView.getItems().add(trackSection);
        }
    }

    @FXML
    private void swipeLeft() {
        if (!selectedTrackListView.getSelectionModel().isEmpty()) {
            TrackSection trackSection = selectedTrackListView.getSelectionModel().getSelectedItem();
            selectedTrackListView.getItems().remove(trackSection);
            allTrackListView.getItems().add(trackSection);
            allTrackListView.getItems().sort(Comparator.comparing(TracksideObject::getId));
        }
    }

    @FXML
    private void saveAndClose() {
        if (isInputValid()) {
            departureRoute.setDescription(descriptionTextField.getText());
            departureRoute.setSignal(signalChoiceBox.getValue());
            departureRoute.setSwitchStatePositions(switchStatePositionsMap);
            departureRoute.setWithManeuver(maneuverCheckBox.isSelected());
            departureRoute.setTVDS1(TVDS1ChoiceBox.getValue());
            departureRoute.setTVDS2(TVDS2ChoiceBox.getValue());
            ConcurrentLinkedDeque<TrackSection> occupationalOrder = new ConcurrentLinkedDeque<>(selectedTrackListView.getItems());
            departureRoute.setOccupationalOrder(occupationalOrder);
            departureRoute.setDestinationTrackSection(occupationalOrder.getLast());
            Model.getRouteTable().add(departureRoute);
            Stage thisStage = (Stage) descriptionTextField.getScene().getWindow();
            thisStage.close();
            RouteTableController.routeObservableList.add(departureRoute);
        }
    }

    private boolean isInputValid() {
        if (descriptionTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please, type correct route description!");
            alert.show();
            return false;
        }

        if (signalChoiceBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please, choice signal for open the route!");
            alert.show();
            return false;
        }

        if (TVDS1ChoiceBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please, choice first track vacancy detection section (TVDS1)!");
            alert.show();
            return false;
        }

        if (TVDS2ChoiceBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please, choice second track vacancy detection section (TVDS2)!");
            alert.show();
            return false;
        }

        if (TVDS1ChoiceBox.getValue().equals(TVDS2ChoiceBox.getValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please, choice different two track vacancy detection sections!");
            alert.show();
            return false;
        }

        if (selectedTrackListView.getItems().size() < 2) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please, create occupational order of at least two track sections!");
            alert.show();
            return false;
        }

        return true;
    }


}



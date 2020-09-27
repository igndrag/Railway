package NATrain.UI.routeTable;

import NATrain.model.Model;
import NATrain.routes.DepartureRoute;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.SwitchState;
import NATrain.trackSideObjects.TrackSection;
import NATrain.trackSideObjects.TracksideObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Comparator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


public class DepartureRouteEditorController {

    private DepartureRoute departureRoute;
    private ConcurrentHashMap<Switch, SwitchState> switchStatePositionsMap;
    private String initialName;

    @FXML
    private TextField descriptionTextField;
    @FXML
    private ChoiceBox<String> signalChoiceBox;
    @FXML
    private ChoiceBox<String> TVDS1ChoiceBox;
    @FXML
    private ChoiceBox<String> TVDS2ChoiceBox;
    @FXML
    private CheckBox maneuverCheckBox;
    @FXML
    private ListView<String> allTrackListView;
    @FXML
    private ListView<String> selectedTrackListView;
    @FXML
    private ChoiceBox<String> switchChoiceBox;
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

    public void initialize(DepartureRoute departureRoute) {
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

        signalChoiceBox.setItems(FXCollections.observableArrayList(Model.getSignals().keySet()));
        ObservableList<String> trackObservableList = FXCollections.observableArrayList(Model.getTrackSections().keySet());
        TVDS1ChoiceBox.setItems(trackObservableList);
        TVDS2ChoiceBox.setItems(trackObservableList);

        if (departureRoute.getSignal() != null)
            signalChoiceBox.setValue(departureRoute.getSignal().getId());

        ToggleGroup toggleGroup = new ToggleGroup();
        plusToggleButton.setToggleGroup(toggleGroup);
        minusToggleButton.setToggleGroup(toggleGroup);
        plusToggleButton.setSelected(true);

        switchChoiceBox.setItems(FXCollections.observableArrayList(Model.getSwitches().keySet()));

        addSwitchPositionButton.setOnAction(event -> {
            if (!switchChoiceBox.getSelectionModel().isEmpty()) {
                SwitchState switchState;
                String switchName = switchChoiceBox.getSelectionModel().getSelectedItem();
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
                String switchName = switchPositionListView.getSelectionModel().getSelectedItem();
                switchPositionListView.getItems().remove(switchName);
                switchStatePositionsMap.remove(Model.getSwitches().get(switchName));
            }
        });

        ObservableList<String> allTrackSections = FXCollections.observableArrayList(Model.getTrackSections().keySet());
        allTrackSections.sort(Comparator.naturalOrder());
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
            String trackName = allTrackListView.getSelectionModel().getSelectedItem();
            allTrackListView.getItems().remove(trackName);
            selectedTrackListView.getItems().add(trackName);
        }
    }

    @FXML
    private void swipeLeft() {
        if (!selectedTrackListView.getSelectionModel().isEmpty()) {
            String trackName = selectedTrackListView.getSelectionModel().getSelectedItem();
            selectedTrackListView.getItems().remove(trackName);
            allTrackListView.getItems().add(trackName);
            allTrackListView.getItems().sort(Comparator.naturalOrder());
        }
    }

    @FXML
    private void saveAndClose() {
        if (isInputValid()) {
            departureRoute.setDescription(descriptionTextField.getText());
            departureRoute.setSignal(Model.getSignals().get(signalChoiceBox.getValue()));
            departureRoute.setSwitchStatePositions(switchStatePositionsMap);
            departureRoute.setWithManeuver(maneuverCheckBox.isSelected());
            departureRoute.setTVDS1(Model.getTrackSections().get(TVDS1ChoiceBox.getValue()));
            departureRoute.setTVDS2(Model.getTrackSections().get(TVDS2ChoiceBox.getValue()));
            ConcurrentLinkedQueue<TrackSection> occupationalOrder = new ConcurrentLinkedQueue<>();
            selectedTrackListView.getItems().stream().map(trackName -> Model.getTrackSections().get(trackName)).forEach(occupationalOrder::add);
            departureRoute.setOccupationalOrder(occupationalOrder);
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



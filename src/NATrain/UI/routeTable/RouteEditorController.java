package NATrain.UI.routeTable;

import NATrain.model.Model;
import NATrain.routes.*;
import NATrain.trackSideObjects.*;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.switches.SwitchState;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Comparator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;


public class RouteEditorController {
    private Route route;
    private RouteType selectedRouteType;
    private ConcurrentHashMap<Switch, SwitchState> switchStatePositionsMap;
    private String initialName;

    @FXML
    private ToggleButton arrivalToggleButton;
    @FXML
    private ToggleButton departureToggleButton;
    @FXML
    private ToggleButton shuntingToggleButton;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private ChoiceBox<Signal> signalChoiceBox;
    @FXML
    private ChoiceBox<Signal> nextSignalChoiceBox;
    @FXML
    private ChoiceBox<TrackSection> departureChoiceBox;
    @FXML
    private ChoiceBox<Track> destinationTrackLineChoiceBox;
    @FXML
    private ChoiceBox<TrackBlockSection> TVDS1ChoiceBox;
    @FXML
    private ChoiceBox<TrackBlockSection> TVDS2ChoiceBox;
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

    public void initialize(Route route) {
        this.route = route;

        ToggleGroup routeTypeToggleGroup = new ToggleGroup();

        arrivalToggleButton.setToggleGroup(routeTypeToggleGroup);
        departureToggleButton.setToggleGroup(routeTypeToggleGroup);
        shuntingToggleButton.setToggleGroup(routeTypeToggleGroup);

        arrivalToggleButton.setOnAction(event -> {
            selectArrivalRouteType();
        });


        departureToggleButton.setOnAction(event -> {
            selectDepartureRouteType();
        });

        shuntingToggleButton.setOnAction(event -> {
            selectShuntingRouteType();
        });

        switch (route.getRouteType()) {
            case ARRIVAL:
                arrivalToggleButton.setSelected(true);
                selectArrivalRouteType();
                break;
            case DEPARTURE:
                selectDepartureRouteType();
                departureToggleButton.setSelected(true);
                break;
            default:
                selectShuntingRouteType();
                shuntingToggleButton.setSelected(true);
                break;
        }

        switchStatePositionsMap = new ConcurrentHashMap<>(route.getSwitchStatePositions());
        initialName = route.getDescription();
        descriptionTextField.setText(initialName);

        switchChoiceBox.setItems(FXCollections.observableArrayList(Model.getSwitches().values()));

        ObservableList<String> switchPositionsObservableList = FXCollections.observableArrayList();
        route.getSwitchStatePositions().forEach((sw, sp) -> {
            StringBuilder stringBuilder = new StringBuilder();
            if (sp == SwitchState.PLUS)
                stringBuilder.append("+");
            else
                stringBuilder.append("-");
            stringBuilder.append(sw.getId());
            switchPositionsObservableList.add(stringBuilder.toString());
            switchChoiceBox.getItems().remove(sw);
        });

        switchPositionListView.setItems(switchPositionsObservableList);

        ToggleGroup switchPositionToggleGroup = new ToggleGroup();
        plusToggleButton.setToggleGroup(switchPositionToggleGroup);
        minusToggleButton.setToggleGroup(switchPositionToggleGroup);
        plusToggleButton.setSelected(true);

        addSwitchPositionButton.setOnAction(event -> {
            if (!switchChoiceBox.getSelectionModel().isEmpty()) {
                SwitchState switchState;
                Switch aSwitch = switchChoiceBox.getSelectionModel().getSelectedItem();
                StringBuilder stringBuilder = new StringBuilder();
                if (plusToggleButton.isSelected()) {
                    stringBuilder.append("+");
                    switchState = SwitchState.PLUS;
                } else {
                    stringBuilder.append("-");
                    switchState = SwitchState.MINUS;
                }
                stringBuilder.append(aSwitch.getId());
                switchPositionListView.getItems().add(stringBuilder.toString());
                switchChoiceBox.getItems().remove(switchChoiceBox.getSelectionModel().getSelectedItem());
                switchStatePositionsMap.put(aSwitch, switchState);
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

        maneuverCheckBox.setSelected(route.getWithManeuver());

        signalChoiceBox.setItems(FXCollections.observableArrayList(Model.getSignals().values()));
        nextSignalChoiceBox.setItems(FXCollections.observableArrayList(Model.getSignals().values()));

        if (route.getSignal() != null) {
            signalChoiceBox.setValue(route.getSignal());
        }

        if (route.getNextSignal() != null) {
            nextSignalChoiceBox.setValue(route.getNextSignal());
        }

        ObservableList<Track> TrackObservableList = FXCollections.observableArrayList(Model.getTracks());
        TrackObservableList.sort(Comparator.comparing(Track::getId));
        destinationTrackLineChoiceBox.setItems(TrackObservableList);
        if (route.getDestinationTrack() != null) {
            destinationTrackLineChoiceBox.getSelectionModel().select(route.getDestinationTrack());
            TVDS1ChoiceBox.setItems(FXCollections.observableArrayList(destinationTrackLineChoiceBox.getValue().getBlockSections()));
            TVDS2ChoiceBox.setItems(FXCollections.observableArrayList(destinationTrackLineChoiceBox.getValue().getBlockSections()));
        }
        destinationTrackLineChoiceBox.setOnAction(event -> {
            TVDS1ChoiceBox.setItems(FXCollections.observableArrayList(destinationTrackLineChoiceBox.getValue().getBlockSections()));
            TVDS2ChoiceBox.setItems(FXCollections.observableArrayList(destinationTrackLineChoiceBox.getValue().getBlockSections()));
        });

        if (route.getTVDS1() != null) {
            TVDS1ChoiceBox.getSelectionModel().select(route.getTVDS1());
        }
        if (route.getTVDS2() != null) {
            TVDS2ChoiceBox.getSelectionModel().select(route.getTVDS2());
        }

        ObservableList<TrackSection> arrivalDepartureTrackObservableList = FXCollections.observableArrayList(
                Model.getTrackSections()
                        .values()
                        .stream()
                        .filter(trackSection -> trackSection instanceof StationTrack)
                        .collect(Collectors.toList()));
        departureChoiceBox.setItems(arrivalDepartureTrackObservableList);
        if (route.getDepartureTrackSection() != null) {
            departureChoiceBox.getSelectionModel().select(route.getDepartureTrackSection());
        }
        ObservableList<TrackSection> allTrackSections = FXCollections.observableArrayList(Model.getTrackSections().values());
        allTrackSections.sort(Comparator.comparing(TracksideObject::getId));
        allTrackListView.setItems(allTrackSections);
        route.getOccupationalOrder().forEach(trackSection -> {
            allTrackListView.getSelectionModel().select(trackSection);
            swipeRight();
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
            route.setRouteType(selectedRouteType);
            route.setDescription(descriptionTextField.getText());
            route.setSignal(signalChoiceBox.getValue());
            route.setNextSignal(nextSignalChoiceBox.getValue());
            route.setSwitchStatePositions(switchStatePositionsMap);
            route.setWithManeuver(maneuverCheckBox.isSelected());
            route.setTVDS1(TVDS1ChoiceBox.getValue());
            route.setTVDS2(TVDS2ChoiceBox.getValue());
            route.setDepartureTrackSection(departureChoiceBox.getValue());
            ConcurrentLinkedDeque<TrackSection> occupationalOrder = new ConcurrentLinkedDeque<>(selectedTrackListView.getItems());
            switch (selectedRouteType) {
                case DEPARTURE:
                    route.setDestinationTrack(TVDS1ChoiceBox.getValue().getTrack());
                    route.setDestinationTrackSection(TVDS1ChoiceBox.getValue());
                    break;
                case ARRIVAL:
                case SHUNTING:
                    route.setDestinationTrackSection(occupationalOrder.getLast());
                    break;
            }
            route.setOccupationalOrder(occupationalOrder);

            Model.getRouteTable().add(route);
            Stage thisStage = (Stage) descriptionTextField.getScene().getWindow();
            thisStage.close();
            if (!RouteTableController.getRouteObservableList().contains(route)) {
                RouteTableController.getRouteObservableList().add(route);
            }
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

        if (!nextSignalChoiceBox.isDisable() && nextSignalChoiceBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please, choice next signal in route!");
            alert.show();
            return false;
        }

        if (!TVDS1ChoiceBox.isDisable() && TVDS1ChoiceBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please, choice first track vacancy detection section (TVDS1)!");
            alert.show();
            return false;
        }

        if (!TVDS2ChoiceBox.isDisable() && TVDS2ChoiceBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please, choice second track vacancy detection section (TVDS2)!");
            alert.show();
            return false;
        }

        if (!TVDS1ChoiceBox.isDisable() && !TVDS2ChoiceBox.isDisable() && TVDS1ChoiceBox.getValue().equals(TVDS2ChoiceBox.getValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please, choice different two track vacancy detection sections!");
            alert.show();
            return false;
        }

        if (selectedTrackListView.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please, create occupational order at least of one track sections!");
            alert.show();
            return false;
        }

        return true;
    }


    private void selectDepartureRouteType() {
        selectedRouteType = RouteType.DEPARTURE;
        TVDS1ChoiceBox.setDisable(false);
        TVDS2ChoiceBox.setDisable(false);
        nextSignalChoiceBox.getSelectionModel().clearSelection();
        nextSignalChoiceBox.setDisable(true);
        destinationTrackLineChoiceBox.setDisable(false);
    }

    private void selectArrivalRouteType() {
        selectedRouteType = RouteType.ARRIVAL;
        TVDS1ChoiceBox.getSelectionModel().clearSelection();
        TVDS1ChoiceBox.setDisable(true);
        TVDS2ChoiceBox.getSelectionModel().clearSelection();
        TVDS2ChoiceBox.setDisable(true);
        nextSignalChoiceBox.setDisable(false);
        destinationTrackLineChoiceBox.setDisable(true);
    }

    private void selectShuntingRouteType() {
        selectedRouteType = RouteType.SHUNTING;
        TVDS1ChoiceBox.getSelectionModel().clearSelection();
        TVDS1ChoiceBox.setDisable(true);
        TVDS2ChoiceBox.getSelectionModel().clearSelection();
        TVDS2ChoiceBox.setDisable(true);
        nextSignalChoiceBox.getSelectionModel().clearSelection();
        nextSignalChoiceBox.setDisable(true);
        destinationTrackLineChoiceBox.setDisable(true);
    }


}



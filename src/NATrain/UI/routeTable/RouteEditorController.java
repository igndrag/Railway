package NATrain.UI.routeTable;

import NATrain.UI.UIUtils;
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

import static NATrain.routes.RouteDirection.ODD;


public class RouteEditorController {


    private Route route;
    private RouteType selectedRouteType;
    private ConcurrentHashMap<Switch, SwitchState> switchStatePositionsMap;
    private String initialName;


    @FXML
    private ChoiceBox<StationTrack> departureTrackChoiceBox;
    @FXML
    private ChoiceBox<StationTrack> arrivalTrackChoiceBox;
    @FXML
    private ToggleButton evenToggleButton;
    @FXML
    private ToggleButton oddToggleButton;
    @FXML
    private RadioButton reversedRadioButton;
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
    private ChoiceBox<Track> trackLineChoiceBox;
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


        ToggleGroup routeDirectionToggleGroup = new ToggleGroup();

        evenToggleButton.setToggleGroup(routeDirectionToggleGroup);
        oddToggleButton.setToggleGroup(routeDirectionToggleGroup);

        if (route.getRouteDirection() == RouteDirection.EVEN) {
            evenToggleButton.setSelected(true);
        }

        if (route.getRouteDirection() == RouteDirection.ODD) {
            oddToggleButton.setSelected(true);
        }

        arrivalTrackChoiceBox.setItems(FXCollections.observableArrayList(Model.getStationTracks().values()));
        departureTrackChoiceBox.setItems(FXCollections.observableArrayList(Model.getStationTracks().values()));

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

        if (route.getSignal() != null) {
            signalChoiceBox.setValue(route.getSignal());
        }

        ObservableList<Track> TrackObservableList = FXCollections.observableArrayList(Model.getTracks());
        TrackObservableList.sort(Comparator.comparing(Track::getId));
        trackLineChoiceBox.setItems(TrackObservableList);

        if (route.getDestinationTrackSection() instanceof TrackBlockSection) {
            Track track = ((TrackBlockSection) route.getDestinationTrackSection()).getTrack();
                trackLineChoiceBox.getSelectionModel().select(track);
            }
        if (route.getDepartureTrackSection() instanceof TrackBlockSection) {
            Track track = ((TrackBlockSection) route.getDepartureTrackSection()).getTrack();
                trackLineChoiceBox.getSelectionModel().select(track);
        }

        if (route.getDestinationTrackSection() instanceof StationTrack) {
            StationTrack stationTrack = (StationTrack) route.getDestinationTrackSection();
            arrivalTrackChoiceBox.getSelectionModel().select(stationTrack);
        }

        if (route.getDepartureTrackSection() instanceof StationTrack) {
            StationTrack stationTrack = (StationTrack) route.getDepartureTrackSection();
            departureTrackChoiceBox.getSelectionModel().select(stationTrack);
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
            route.setSwitchStatePositions(switchStatePositionsMap);
            route.setWithManeuver(maneuverCheckBox.isSelected());
            if (!trackLineChoiceBox.getSelectionModel().isEmpty()) {
                Track destinationTrack = trackLineChoiceBox.getValue();
                if (reversedRadioButton.isSelected()) {
                    route.setTVDS1(destinationTrack.getBlockSections().get(destinationTrack.getBlockSections().size() - 1));
                    route.setTVDS2(destinationTrack.getBlockSections().get(destinationTrack.getBlockSections().size() - 2));
                } else {
                    route.setTVDS1(destinationTrack.getBlockSections().get(0));
                    route.setTVDS2(destinationTrack.getBlockSections().get(1));
                }
            }

            route.setRouteDirection(evenToggleButton.isSelected() ? RouteDirection.EVEN : ODD);

            if (!trackLineChoiceBox.getSelectionModel().isEmpty()) {
                route.setDestinationTrackLine(trackLineChoiceBox.getValue());
            }

            ConcurrentLinkedDeque<TrackSection> occupationalOrder = new ConcurrentLinkedDeque<>(selectedTrackListView.getItems());
            switch (selectedRouteType) {
                case DEPARTURE:
                    route.setDestinationTrackSection(route.getTVDS1());
                    route.setDepartureTrackSection(departureTrackChoiceBox.getValue());
                    break;
                case ARRIVAL:
                    route.setDestinationTrackSection(arrivalTrackChoiceBox.getValue());
                    Track track = trackLineChoiceBox.getValue();
                    if (route.getRouteDirection() == track.getNormalDirection()) {
                        route.setDepartureTrackSection(track.getBlockSections().get(0));
                    } else {
                        route.setDepartureTrackSection(track.getBlockSections().get(track.getBlockSections().size() - 1));
                    }
                    break;
                case SHUNTING:
                    if (arrivalTrackChoiceBox.getSelectionModel().isEmpty()) {
                        route.setDestinationTrackSection(occupationalOrder.getLast());
                    } else {
                        route.setDestinationTrackSection(arrivalTrackChoiceBox.getValue());
                    }
                    if (departureTrackChoiceBox.getSelectionModel().isEmpty()) {
                        route.setDepartureTrackSection(occupationalOrder.getFirst());
                    } else {
                        route.setDepartureTrackSection(departureTrackChoiceBox.getValue());
                    }
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

        if (!evenToggleButton.isSelected() && !oddToggleButton.isSelected()) {
            UIUtils.showAlert("Please, choice route direction!");
            return false;
        }

        if (signalChoiceBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please, choice signal for open the route!");
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
        arrivalTrackChoiceBox.setDisable(true);
        departureTrackChoiceBox.setDisable(false);
        trackLineChoiceBox.setDisable(false);
    }

    private void selectArrivalRouteType() {
        selectedRouteType = RouteType.ARRIVAL;
        arrivalTrackChoiceBox.setDisable(false);
        trackLineChoiceBox.setDisable(false);
        departureTrackChoiceBox.setDisable(true);
    }

    private void selectShuntingRouteType() {
        selectedRouteType = RouteType.SHUNTING;
        arrivalTrackChoiceBox.setDisable(false);
        departureTrackChoiceBox.setDisable(false);
        trackLineChoiceBox.setDisable(true);
    }


}



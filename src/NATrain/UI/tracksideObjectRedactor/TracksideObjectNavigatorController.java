package NATrain.UI.tracksideObjectRedactor;

import NATrain.UI.AppConfigController;
import NATrain.UI.tracksideObjectRedactor.TSORedactors.*;
import NATrain.connectionService.MQTTConnectionService;
import NATrain.model.Model;
import NATrain.routes.StationTrack;
import NATrain.trackSideObjects.*;
import NATrain.trackSideObjects.customObjects.*;
import NATrain.trackSideObjects.movableObjects.Locomotive;
import NATrain.trackSideObjects.movableObjects.MovableObjectType;
import NATrain.trackSideObjects.movableObjects.Wagon;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalType;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;

public class TracksideObjectNavigatorController {

    @FXML
    private TableView<TracksideObject> switchTableView;
    @FXML
    private TableColumn<Switch, String> switchIdCol;
    @FXML
    private TableColumn<Switch, Integer> switchControlModuleCol;
    @FXML
    private Button newSwitchButton;
    @FXML
    private Button editSwitchButton;
    @FXML
    private Button deleteSwitchButton;
    @FXML
    private Button newTrackSectionButton;
    @FXML
    private Button editTrackSectionButton;
    @FXML
    private Button deleteTrackSectionButton;
    @FXML
    private TableColumn<TrackSection, String> trackSectionIdCol;
    @FXML
    private TableColumn<TrackSection, Integer> trackSectionLengthCol;
    @FXML
    private TableColumn<TrackSection, String> trackSectionControlModuleCol;
    @FXML
    protected TableView<TracksideObject> trackSectionsTableView;
    @FXML
    private TableView<TracksideObject> signalTableView;
    @FXML
    private TableColumn<Signal, String> signalIdCol;
    @FXML
    private TableColumn<Signal, String> signalControlModuleCol;
    @FXML
    private Button newSignalButton;
    @FXML
    private Button editSignalButton;
    @FXML
    private Button deleteSignalButton;

    @FXML
    private TableView<TracksideObject> stationTrackTableView;
    @FXML
    private TableColumn<StationTrack, String> stationTrackIdCol;
    @FXML
    private TableColumn<StationTrack, Integer> stationTrackLengthCol;
    @FXML
    private TableColumn<StationTrack, String> stationTrackControlModuleCol;
    @FXML
    private TableColumn<StationTrack, Signal> stationTrackEvenSignalCol;
    @FXML
    private TableColumn<StationTrack, Signal> stationTrackOddSignalCol;

    @FXML
    private Button newStationTrackButton;
    @FXML
    private Button editStationTrackButton;
    @FXML
    private Button deleteStationTrackButton;
    @FXML
    private TableView<TracksideObject> locomotiveTableView;
    @FXML
    private TableColumn <Locomotive, String> locomotiveIdCol;
    @FXML
    private TableColumn <Locomotive, String> locomotiveControlModuleCol;
    @FXML
    private Button newLocomotiveButton;
    @FXML
    private Button editLocomotiveButton;
    @FXML
    private Button deleteLocomotiveButton;

    @FXML
    private TableView<TracksideObject> wagonsTableView;
    @FXML
    private TableColumn<Wagon,String> wagonIdCol;
    @FXML
    private TableColumn<Wagon, MovableObjectType> wagonTypeCol;
    @FXML
    private Button newWagonButton;
    @FXML
    private Button editWagonButton;
    @FXML
    private Button deleteWagonButton;

    @FXML
    private TableView<TracksideObject> customObjectTableView;
    @FXML
    private TableColumn<AbstractCustomObject, String> customObjectIdCol;
    @FXML
    private TableColumn<AbstractCustomObject, CustomObjectType> customObjectTypeCol;
    @FXML
    private Button newCustomButton;
    @FXML
    private Button editCustomButton;
    @FXML
    private Button deleteCustomButton;

    protected ObservableList<TracksideObject> trackSectionList;
    protected ObservableList<TracksideObject> stationTrackList;
    protected ObservableList<TracksideObject> switchList;
    protected ObservableList<TracksideObject> signalList;
    protected ObservableList<TracksideObject> locomotiveList;
    protected ObservableList<TracksideObject> wagonList;
    protected ObservableList<TracksideObject> customObjectList;

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage mainStage) {
        primaryStage = mainStage;
    }

    public void initialize() {
        initTrackSectionsTab();
        initSwitchTab();
        initSignalTab();
        initStationTrackTab();
        initLocomotiveTab();
        initWagonTab();
        initCustomObjectsTab();
    }

    protected void toTrackSectionRedactor (TrackSection trackSection) throws IOException {
        FXMLLoader loader;
        switch (AppConfigController.getLanguage()) {
            case RU:
                loader = new FXMLLoader(TrackSectionRedactorController.class.getResource("TrackSectionRedactor_RU.fxml"));
                break;
            default:
                loader = new FXMLLoader(TrackSectionRedactorController.class.getResource("TrackSectionRedactor.fxml"));
        }
        Stage trackSectionRedactor = new Stage();
        trackSectionRedactor.setTitle("Trackline Section Redactor");
        trackSectionRedactor.setScene(new Scene(loader.load(), 220, 240));
        trackSectionRedactor.setResizable(false);
        TrackSectionRedactorController controller = loader.getController();
        controller.init(trackSection, trackSectionsTableView, trackSectionList);
        trackSectionRedactor.initModality(Modality.WINDOW_MODAL);
        trackSectionRedactor.initOwner(primaryStage);
        trackSectionRedactor.show();
    }

    private void toSwitchRedactor (Switch mySwitch) throws IOException {
        FXMLLoader loader;
        switch (AppConfigController.getLanguage()) {
            case RU:
                loader = new FXMLLoader(SwitchRedactorController.class.getResource("SwitchRedactor_RU.fxml"));
                break;
            default:
                loader = new FXMLLoader(SwitchRedactorController.class.getResource("SwitchRedactor.fxml"));
        }
        Stage switchRedactor = new Stage();
        switchRedactor.setTitle("Switch Redactor");
        switchRedactor.setScene(new Scene(loader.load(), 330, 260));
        switchRedactor.setResizable(false);
        SwitchRedactorController controller = loader.getController();
        controller.init(mySwitch, switchTableView, switchList);
        switchRedactor.initModality(Modality.WINDOW_MODAL);
        switchRedactor.initOwner(primaryStage);
        switchRedactor.show();
    }

    private void toSignalRedactor (Signal signal) throws IOException {
        FXMLLoader loader;
        switch (AppConfigController.getLanguage()) {
            case RU:
                loader = new FXMLLoader(SignalRedactorController.class.getResource("SignalRedactor_RU.fxml"));
                break;
            default:
                loader = new FXMLLoader(SignalRedactorController.class.getResource("SignalRedactor.fxml"));
        }
        Stage signalRedactor = new Stage();
        signalRedactor.setTitle("Signal Redactor");
        signalRedactor.setScene(new Scene(loader.load(), 350, 220));
        signalRedactor.setResizable(false);
        SignalRedactorController controller = loader.getController();
        controller.init(signal, signalTableView, signalList);
        signalRedactor.initModality(Modality.WINDOW_MODAL);
        signalRedactor.initOwner(primaryStage);
        signalRedactor.show();
    }

    private void toStationTrackRedactor(StationTrack stationTrack) throws IOException {
        FXMLLoader loader;
        switch (AppConfigController.getLanguage()) {
            case RU:
                loader = new FXMLLoader(StationTrackRedactorController.class.getResource("StationTrackRedactor_RU.fxml"));
                break;
            default:
                loader = new FXMLLoader(StationTrackRedactorController.class.getResource("StationTrackRedactor.fxml"));
        }
        Stage stationTrackRedactor = new Stage();
        stationTrackRedactor.setTitle("Station Trackline Redactor");
        stationTrackRedactor.setScene(new Scene(loader.load(), 220, 350));
        stationTrackRedactor.setResizable(false);
        StationTrackRedactorController controller = loader.getController();
        controller.init(stationTrack, stationTrackTableView, stationTrackList);
        stationTrackRedactor.initModality(Modality.WINDOW_MODAL);
        stationTrackRedactor.initOwner(primaryStage);
        stationTrackRedactor.show();
    }


    private void toLocomotiveRedactor(Locomotive locomotive, boolean edit) throws IOException {
        FXMLLoader loader;
        switch (AppConfigController.getLanguage()) {
            case RU:
                loader = new FXMLLoader(LocomotiveRedactorController.class.getResource("LocomotiveRedactor_RU.fxml"));
                break;
            default:
                loader = new FXMLLoader(LocomotiveRedactorController.class.getResource("LocomotiveRedactor.fxml"));
        }
        Stage locomotiveRedactor = new Stage();
        locomotiveRedactor.setTitle("Locomotive Redactor");
        locomotiveRedactor.setScene(new Scene(loader.load(), 290, 330));
        locomotiveRedactor.setResizable(false);
        LocomotiveRedactorController controller = loader.getController();
        controller.init(locomotive, locomotiveTableView, locomotiveList);
        controller.edit = edit;
        locomotiveRedactor.initModality(Modality.WINDOW_MODAL);
        locomotiveRedactor.initOwner(primaryStage);
        locomotiveRedactor.show();
    }

    private void toWagonRedactor(Wagon wagon, boolean edit) throws IOException {
        FXMLLoader loader;
        switch (AppConfigController.getLanguage()) {
            case RU:
                loader = new FXMLLoader(WagonRedactorController.class.getResource("WagonRedactor_RU.fxml"));
                break;
            default:
                loader = new FXMLLoader(WagonRedactorController.class.getResource("WagonRedactor.fxml"));
        }
        Stage wagonRedactor = new Stage();
        wagonRedactor.setTitle("Wagon Redactor");
        wagonRedactor.setScene(new Scene(loader.load(), 270, 320));
        wagonRedactor.setResizable(false);
        WagonRedactorController controller = loader.getController();
        controller.init(wagon, wagonsTableView, wagonList);
        controller.edit = edit;
        wagonRedactor.initModality(Modality.WINDOW_MODAL);
        wagonRedactor.initOwner(primaryStage);
        wagonRedactor.show();
    }

    protected void toServoRedactor(Servo servo, boolean edit) throws IOException {
        FXMLLoader loader;
        switch (AppConfigController.getLanguage()) {
            case RU:
               // loader = new FXMLLoader(WagonRedactorController.class.getResource("WagonRedactor_RU.fxml"));
               // break;
            default:
                loader = new FXMLLoader(WagonRedactorController.class.getResource("ServoRedactor.fxml"));
        }
        Stage servoRedactor = new Stage();
        servoRedactor.setTitle("Servo Redactor");
        servoRedactor.setScene(new Scene(loader.load(), 300, 290));
        servoRedactor.setResizable(false);
        OnOffObjectRedactorController controller = loader.getController();
        controller.init(servo, customObjectTableView, customObjectList);
        controller.edit = edit;
        servoRedactor.initModality(Modality.WINDOW_MODAL);
        servoRedactor.initOwner(primaryStage);
        servoRedactor.show();
        servoRedactor.setOnCloseRequest(event -> {
            if (MQTTConnectionService.getClient().isConnected()) {
                  MQTTConnectionService.disconnect();
                //System.out.println("MQTT Service disconnected");
            }
        });
    }

    protected void toGateRedactor(Gate gate, boolean edit) throws IOException {
        FXMLLoader loader;
        switch (AppConfigController.getLanguage()) {
            case RU:
              //  loader = new FXMLLoader(GateRedactorController.class.getResource("GateRedactor_RU.fxml"));
              //  break;
            default:
                loader = new FXMLLoader(GateRedactorController.class.getResource("GateRedactor.fxml"));
        }
        Stage gateRedactor = new Stage();
        gateRedactor.setTitle("Gate Redactor");
        gateRedactor.setScene(new Scene(loader.load(), 200, 240));
        gateRedactor.setResizable(false);
        GateRedactorController controller = loader.getController();
        controller.init(gate, customObjectTableView, customObjectList);
        controller.edit = edit;
        gateRedactor.initModality(Modality.WINDOW_MODAL);
        gateRedactor.initOwner(primaryStage);
        gateRedactor.show();
    }

    protected void toPolarityChangerRedactor(PolarityChanger polarityChanger, boolean edit) throws IOException {
        FXMLLoader loader;
        switch (AppConfigController.getLanguage()) {
            case RU:
                //  loader = new FXMLLoader(GateRedactorController.class.getResource("GateRedactor_RU.fxml"));
                //  break;
            default:
                loader = new FXMLLoader(PolarityChangerRedactorController.class.getResource("PolarityChangerRedactor.fxml"));
        }
        Stage polarityChangerRedactor = new Stage();
        polarityChangerRedactor.setTitle("Polarity Changer Redactor");
        polarityChangerRedactor.setScene(new Scene(loader.load(), 290, 320));
        polarityChangerRedactor.setResizable(false);
        PolarityChangerRedactorController controller = loader.getController();
        controller.init(polarityChanger, customObjectTableView, customObjectList);
        controller.edit = edit;
        polarityChangerRedactor.initModality(Modality.WINDOW_MODAL);
        polarityChangerRedactor.initOwner(primaryStage);
        polarityChangerRedactor.show();
    }

    protected void toRoadCrossingRedactor(RoadCrossing roadCrossing, boolean edit) throws IOException {
        FXMLLoader loader;
        switch (AppConfigController.getLanguage()) {
            case RU:
                //  loader = new FXMLLoader(GateRedactorController.class.getResource("GateRedactor_RU.fxml"));
                //  break;
            default:
                loader = new FXMLLoader(RoadCrossingRedactorController.class.getResource("RoadCrossingRedactor.fxml"));
        }
        Stage roadCrossingRedactor = new Stage();
        roadCrossingRedactor.setTitle("Road Crossing Redactor");
        roadCrossingRedactor.setScene(new Scene(loader.load(), 360, 370));
        roadCrossingRedactor.setResizable(false);
        RoadCrossingRedactorController controller = loader.getController();
        controller.init(roadCrossing, customObjectTableView, customObjectList);
        controller.edit = edit;
        roadCrossingRedactor.initModality(Modality.WINDOW_MODAL);
        roadCrossingRedactor.initOwner(primaryStage);
        roadCrossingRedactor.show();
    }

    protected void toOnOffObjectRedactor(OnOffObject onOffObject, boolean edit) throws IOException {
        FXMLLoader loader;
        switch (AppConfigController.getLanguage()) {
            case RU:
                //  loader = new FXMLLoader(GateRedactorController.class.getResource("GateRedactor_RU.fxml"));
                //  break;
            default:
                loader = new FXMLLoader(OnOffObjectRedactorController.class.getResource("OnOffObjectRedactor.fxml"));
        }
        Stage onOffObjectRedactorController = new Stage();
        onOffObjectRedactorController.setTitle("ON/OFF Object Redactor");
        onOffObjectRedactorController.setScene(new Scene(loader.load(), 200, 120));
        onOffObjectRedactorController.setResizable(false);
        OnOffObjectRedactorController controller = loader.getController();
        controller.init(onOffObject, customObjectTableView, customObjectList);
        controller.edit = edit;
        onOffObjectRedactorController.initModality(Modality.WINDOW_MODAL);
        onOffObjectRedactorController.initOwner(primaryStage);
        onOffObjectRedactorController.show();
    }

    public void initTrackSectionsTab() {
        trackSectionIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        trackSectionControlModuleCol.setCellValueFactory(new PropertyValueFactory<>("modules"));
        trackSectionLengthCol.setCellValueFactory(new PropertyValueFactory<>("length"));
        editTrackSectionButton.setDisable(true);
        deleteTrackSectionButton.setDisable(true);

        trackSectionList = FXCollections.observableArrayList(Model.getTrackSections().values());
        trackSectionList.sort(Comparator.comparing(TracksideObject::getId));
        trackSectionsTableView.setItems(trackSectionList);

        newTrackSectionButton.setOnMouseClicked(event -> {
            try {
                TrackSection trackSection = new TrackSection(TrackSection.INITIAL_TRACK_SECTION_NAME);
                toTrackSectionRedactor(trackSection);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteTrackSectionButton.setOnAction(event -> {
            TracksideObject objectForDelete = trackSectionsTableView.getSelectionModel().getSelectedItem();
          //  if (objectForDelete.getControlModule() != null)
          //      objectForDelete.getControlModule().deleteTrackSideObjectFromChannel(objectForDelete.getChannel());
          //  objectForDelete.setControlModule(null);
            trackSectionList.remove(objectForDelete);
            Model.getTrackSections().remove(objectForDelete.getId());
            if (trackSectionList.size() == 0) {
                editTrackSectionButton.setDisable(true);
                deleteTrackSectionButton.setDisable(true);
            }
        });

        editTrackSectionButton.setOnAction(event -> {
            try {
                toTrackSectionRedactor((TrackSection) trackSectionsTableView.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        trackSectionsTableView.setOnMouseClicked(event -> {
            if (trackSectionsTableView.getSelectionModel().getSelectedItem() != null) {
                editTrackSectionButton.setDisable(false);
                deleteTrackSectionButton.setDisable(false);
            }
        });

    }

    public void initSwitchTab() {
        switchIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        switchControlModuleCol.setCellValueFactory(new PropertyValueFactory<>("modules"));
        editSwitchButton.setDisable(true);
        deleteSwitchButton.setDisable(true);

        switchList = FXCollections.observableArrayList(Model.getSwitches().values());
        switchList.sort(Comparator.comparing(TracksideObject::getId));
        switchTableView.setItems(switchList);

        newSwitchButton.setOnMouseClicked(event -> {
            try {
                Switch mySwitch = new Switch(Switch.INITIAL_SWITCH_NAME);
                toSwitchRedactor(mySwitch);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteSwitchButton.setOnAction(event -> {
            Switch objectForDelete = (Switch) switchTableView.getSelectionModel().getSelectedItem();
            //if (objectForDelete.getControlModule() != null)
            //    objectForDelete.getControlModule().deleteTrackSideObjectFromChannel(objectForDelete.getChannel());
            //objectForDelete.setControlModule(null);
            if (objectForDelete.isPared()) {
                objectForDelete.getParedSwitch().setParedSwitch(null);
            }

            switchList.remove(objectForDelete);
            Model.getSwitches().remove(objectForDelete.getId());
            if (switchList.size() == 0) {
                editSwitchButton.setDisable(true);
                deleteSwitchButton.setDisable(true);
            }
        });

        editSwitchButton.setOnAction(event -> {
            try {
                toSwitchRedactor((Switch)switchTableView.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        switchTableView.setOnMouseClicked(event -> {
            if (switchTableView.getSelectionModel().getSelectedItem() != null) {
                editSwitchButton.setDisable(false);
                deleteSwitchButton.setDisable(false);
            }
        });

    }

    public void initSignalTab() {
        signalIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        signalControlModuleCol.setCellValueFactory(new PropertyValueFactory<>("modules"));
        editSignalButton.setDisable(true);
        deleteSignalButton.setDisable(true);
        signalList = FXCollections.observableArrayList(Model.getSignals().values());
        signalList.sort(Comparator.comparing(TracksideObject::getId));
        signalTableView.setItems(signalList);

        newSignalButton.setOnMouseClicked(event -> {
            try {
                Signal signal = new Signal(Signal.INITIAL_SIGNAL_NAME, SignalType.EMPTY_SIGNAL);
                toSignalRedactor(signal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteSignalButton.setOnAction(event -> {
            Signal objectForDelete = (Signal) signalTableView.getSelectionModel().getSelectedItem();
          // if (objectForDelete.getControlModule() != null)
          //      objectForDelete.getControlModule().deleteTrackSideObjectFromChannel(objectForDelete.getChannel());
          //  objectForDelete.setControlModule(null);

            signalList.remove(objectForDelete);
            Model.getSignals().remove(objectForDelete.getId());
            if (signalList.size() == 0) {
                editSignalButton.setDisable(true);
                deleteSignalButton.setDisable(true);
            }
        });

        editSignalButton.setOnAction(event -> {
            try {
                toSignalRedactor((Signal)signalTableView.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        signalTableView.setOnMouseClicked(event -> {
            if (signalTableView.getSelectionModel().getSelectedItem() != null) {
                editSignalButton.setDisable(false);
                deleteSignalButton.setDisable(false);
            }
        });
    }

    public void initStationTrackTab() {
        stationTrackIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        stationTrackLengthCol.setCellValueFactory(new PropertyValueFactory<>("length"));
        stationTrackControlModuleCol.setCellValueFactory(new PropertyValueFactory<>("modules"));
        stationTrackEvenSignalCol.setCellValueFactory(new PropertyValueFactory<>("evenSignal"));
        stationTrackOddSignalCol.setCellValueFactory(new PropertyValueFactory<>("oddSignal"));
        editStationTrackButton.setDisable(true);
        deleteStationTrackButton.setDisable(true);

        stationTrackList = FXCollections.observableArrayList(Model.getStationTracks().values());
        stationTrackList.sort(Comparator.comparing(TracksideObject::getId));
        stationTrackTableView.setItems(stationTrackList);

        newStationTrackButton.setOnMouseClicked(event -> {
            try {
                StationTrack stationTrack = new StationTrack(StationTrack.INITIAL_STATION_TRACK_NAME);
                toStationTrackRedactor(stationTrack);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteStationTrackButton.setOnAction(event -> {
            TracksideObject objectForDelete = stationTrackTableView.getSelectionModel().getSelectedItem();
            //  if (objectForDelete.getControlModule() != null)
            //      objectForDelete.getControlModule().deleteTrackSideObjectFromChannel(objectForDelete.getChannel());
            //  objectForDelete.setControlModule(null);
            stationTrackList.remove(objectForDelete);
            Model.getStationTracks().remove(objectForDelete.getId());
            if (stationTrackList.size() == 0) {
                editStationTrackButton.setDisable(true);
                deleteStationTrackButton.setDisable(true);
            }
        });

        editStationTrackButton.setOnAction(event -> {
            try {
                toStationTrackRedactor((StationTrack) stationTrackTableView.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        stationTrackTableView.setOnMouseClicked(event -> {
            if (stationTrackTableView.getSelectionModel().getSelectedItem() != null) {
                editStationTrackButton.setDisable(false);
                deleteStationTrackButton.setDisable(false);
            }
        });

    }

    public void initLocomotiveTab() {
        locomotiveIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        locomotiveControlModuleCol.setCellValueFactory(new PropertyValueFactory<>("modules"));
        editLocomotiveButton.setDisable(true);
        deleteLocomotiveButton.setDisable(true);
        locomotiveList = FXCollections.observableArrayList(Model.getLocomotives().values());
        locomotiveList.sort(Comparator.comparing(TracksideObject::getId));
        locomotiveTableView.setItems(locomotiveList);

        newLocomotiveButton.setOnMouseClicked(event -> {
            try {
                Locomotive locomotive = new Locomotive(Locomotive.INITIAL_LOCOMOTIVE_NAME);
                toLocomotiveRedactor(locomotive, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteLocomotiveButton.setOnAction(event -> {
            Locomotive objectForDelete = (Locomotive) locomotiveTableView.getSelectionModel().getSelectedItem();
            // if (objectForDelete.getControlModule() != null)
            //      objectForDelete.getControlModule().deleteTrackSideObjectFromChannel(objectForDelete.getChannel());
            //  objectForDelete.setControlModule(null);

            locomotiveList.remove(objectForDelete);
            if (objectForDelete.getFrontTag() != null) {
                Model.getTags().values().remove(objectForDelete.getFrontTag());
            }

            if (objectForDelete.getRearTag() != null) {
                Model.getTags().values().remove(objectForDelete.getRearTag());
            }

            Model.getLocomotives().remove(objectForDelete.getId());
            if (locomotiveList.size() == 0) {
                editLocomotiveButton.setDisable(true);
                deleteLocomotiveButton.setDisable(true);
            }
        });

        editLocomotiveButton.setOnAction(event -> {
            try {
                toLocomotiveRedactor((Locomotive) locomotiveTableView.getSelectionModel().getSelectedItem(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        locomotiveTableView.setOnMouseClicked(event -> {
            if (locomotiveTableView.getSelectionModel().getSelectedItem() != null) {
                editLocomotiveButton.setDisable(false);
                deleteLocomotiveButton.setDisable(false);
                }
        });
    }

    public void initWagonTab() {
        wagonIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        wagonTypeCol.setCellValueFactory(new PropertyValueFactory<>("movableObjectType"));
        editWagonButton.setDisable(true);
        deleteWagonButton.setDisable(true);
        wagonList = FXCollections.observableArrayList(Model.getWagons().values());
        wagonList.sort(Comparator.comparing(TracksideObject::getId));
        wagonsTableView.setItems(wagonList);

        newWagonButton.setOnMouseClicked(event -> {
            try {
                Wagon wagon = new Wagon(Wagon.INITIAL_WAGON_NAME);
                toWagonRedactor(wagon, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteWagonButton.setOnAction(event -> {
            Wagon objectForDelete = (Wagon) wagonsTableView.getSelectionModel().getSelectedItem();
            // if (objectForDelete.getControlModule() != null)
            //      objectForDelete.getControlModule().deleteTrackSideObjectFromChannel(objectForDelete.getChannel());
            //  objectForDelete.setControlModule(null);

            wagonList.remove(objectForDelete);
            if (objectForDelete.getFrontTag() != null) {
                Model.getTags().values().remove(objectForDelete.getFrontTag());
            }

            if (objectForDelete.getRearTag() != null) {
                Model.getTags().values().remove(objectForDelete.getRearTag());
            }

            objectForDelete.deallocateMovableObject();

            Model.getWagons().remove(objectForDelete.getId());
            if (wagonList.size() == 0) {
                editWagonButton.setDisable(true);
                deleteWagonButton.setDisable(true);
            }
        });

        editWagonButton.setOnAction(event -> {
            try {
                toWagonRedactor((Wagon) wagonsTableView.getSelectionModel().getSelectedItem(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        wagonsTableView.setOnMouseClicked(event -> {
            if (wagonsTableView.getSelectionModel().getSelectedItem() != null) {
                editWagonButton.setDisable(false);
                deleteWagonButton.setDisable(false);
            }
        });
    }

    public void initCustomObjectsTab() {
        customObjectIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customObjectTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        editCustomButton.setDisable(true);
        deleteCustomButton.setDisable(true);
        customObjectList = FXCollections.observableArrayList(Model.getServos().values());
        customObjectList.addAll(Model.getGates().values());
        customObjectList.addAll(Model.getPolarityChangers().values());
        customObjectList.addAll(Model.getRoadCrossings().values());
        customObjectList.addAll(Model.getOnOffObjects().values());
        customObjectTableView.setItems(customObjectList);

        newCustomButton.setOnMouseClicked(event -> {
            try {
                toCustomObjectSelector();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteCustomButton.setOnAction(event -> {
            AbstractCustomObject objectForDelete = (AbstractCustomObject) customObjectTableView.getSelectionModel().getSelectedItem();
            switch (objectForDelete.getType()) {
                case SERVO:
                    //Servo servo = (Servo) objectForDelete;
                    Model.getServos().remove(objectForDelete.getId());
                    break;
                case GATE:
                    Model.getGates().remove(objectForDelete.getId());
                    break;
                case SECTION_POLARITY_CHANGER:
                case SWITCH_POLARITY_CHANGER:
                case LOCO_POLARITY_CHANGER:
                    Model.getPolarityChangers().remove(objectForDelete.getId());
                    break;
                case ROAD_CROSSING:
                    Model.getRoadCrossings().remove(objectForDelete.getId());
                    break;
                case ON_OFF_OBJECT:
                    Model.getOnOffObjects().remove(objectForDelete.getId());
                    break;
            }
            customObjectList.remove(objectForDelete);
            // if (objectForDelete.getControlModule() != null)
            //      objectForDelete.getControlModule().deleteTrackSideObjectFromChannel(objectForDelete.getChannel());
            //  objectForDelete.setControlModule(null);

            if (customObjectList.size() == 0) {
                editCustomButton.setDisable(true);
                deleteCustomButton.setDisable(true);
            }
        });

        editCustomButton.setOnAction(event -> {
            try {
                AbstractCustomObject objectForEdit = (AbstractCustomObject) customObjectTableView.getSelectionModel().getSelectedItem();
                switch (objectForEdit.getType()) {
                    case SERVO:
                        toServoRedactor((Servo)objectForEdit, true);
                        break;
                    case GATE:
                        toGateRedactor((Gate)objectForEdit, true);
                        break;
                    case SWITCH_POLARITY_CHANGER:
                    case SECTION_POLARITY_CHANGER:
                    case LOCO_POLARITY_CHANGER:
                        toPolarityChangerRedactor((PolarityChanger)objectForEdit, true);
                        break;
                    case ROAD_CROSSING:
                        toRoadCrossingRedactor((RoadCrossing) objectForEdit, true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        customObjectTableView.setOnMouseClicked(event -> {
            if (customObjectTableView.getSelectionModel().getSelectedItem() != null) {
                editCustomButton.setDisable(false);
                deleteCustomButton.setDisable(false);
            }
        });
    }

    private void toCustomObjectSelector()  throws IOException{
        FXMLLoader loader;
        switch (AppConfigController.getLanguage()) {
            case RU:
                //loader = new FXMLLoader(CustomObjectSelectorController.class.getResource("SwitchRedactor_RU.fxml"));
                //break;
            default:
                loader = new FXMLLoader(CustomObjectSelectorController.class.getResource("CustomObjectSelector.fxml"));
        }
        Stage customObjectSelector = new Stage();
        customObjectSelector.setTitle("Custom Object Selector");
        customObjectSelector.setScene(new Scene(loader.load(), 220, 380));
        customObjectSelector.setResizable(false);
        CustomObjectSelectorController controller = loader.getController();
        controller.init(this);
        customObjectSelector.initModality(Modality.WINDOW_MODAL);
        customObjectSelector.initOwner(primaryStage);
        customObjectSelector.show();
    }
}

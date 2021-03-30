package NATrain.UI.tracksideObjectRedactor;

import NATrain.UI.tracksideObjectRedactor.TSORedactors.*;
import NATrain.model.Model;
import NATrain.routes.StationTrack;
import NATrain.trackSideObjects.*;
import NATrain.trackSideObjects.locomotives.Locomotive;
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

    protected ObservableList<TracksideObject> trackSectionList;
    protected ObservableList<TracksideObject> stationTrackList;
    protected ObservableList<TracksideObject> switchList;
    protected ObservableList<TracksideObject> signalList;
    protected ObservableList<TracksideObject> locomotiveList;

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
    }

    protected void toTrackSectionRedactor (TrackSection trackSection) throws IOException {
        FXMLLoader loader = new FXMLLoader(TrackSectionRedactorController.class.getResource("TrackSectionRedactor.fxml"));
        Stage trackSectionRedactor = new Stage();
        trackSectionRedactor.setTitle("Track Section Redactor");
        trackSectionRedactor.setScene(new Scene(loader.load(), 240, 220));
        trackSectionRedactor.setResizable(false);
        TrackSectionRedactorController controller = loader.getController();
        controller.init(trackSection, trackSectionsTableView, trackSectionList);
        trackSectionRedactor.initModality(Modality.WINDOW_MODAL);
        trackSectionRedactor.initOwner(primaryStage);
        trackSectionRedactor.show();
    }

    private void toSwitchRedactor (Switch mySwitch) throws IOException {
        FXMLLoader loader = new FXMLLoader(SwitchRedactorController.class.getResource("SwitchRedactor.fxml"));
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
        FXMLLoader loader = new FXMLLoader(SignalRedactorController.class.getResource("SignalRedactor.fxml"));
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
        FXMLLoader loader = new FXMLLoader(StationTrackRedactorController.class.getResource("StationTrackRedactor.fxml"));
        Stage stationTrackRedactor = new Stage();
        stationTrackRedactor.setTitle("Station Track Redactor");
        stationTrackRedactor.setScene(new Scene(loader.load(), 220, 350));
        stationTrackRedactor.setResizable(false);
        StationTrackRedactorController controller = loader.getController();
        controller.init(stationTrack, stationTrackTableView, stationTrackList);
        stationTrackRedactor.initModality(Modality.WINDOW_MODAL);
        stationTrackRedactor.initOwner(primaryStage);
        stationTrackRedactor.show();
    }


    private void toLocomotiveRedactor(Locomotive locomotive) throws IOException {
        FXMLLoader loader = new FXMLLoader(LocomotiveRedactorController.class.getResource("LocomotiveRedactor.fxml"));
        Stage locomotiveRedactor = new Stage();
        locomotiveRedactor.setTitle("Locomotive Redactor");
        locomotiveRedactor.setScene(new Scene(loader.load(), 240, 220));
        locomotiveRedactor.setResizable(false);
        LocomotiveRedactorController controller = loader.getController();
        controller.init(locomotive, locomotiveTableView, locomotiveList);
        locomotiveRedactor.initModality(Modality.WINDOW_MODAL);
        locomotiveRedactor.initOwner(primaryStage);
        locomotiveRedactor.show();
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
                toLocomotiveRedactor(locomotive);
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
            Model.getLocomotives().remove(objectForDelete.getId());
            if (signalList.size() == 0) {
                editLocomotiveButton.setDisable(true);
                deleteLocomotiveButton.setDisable(true);
            }
        });

        editLocomotiveButton.setOnAction(event -> {
            try {
                toLocomotiveRedactor((Locomotive) locomotiveTableView.getSelectionModel().getSelectedItem());
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

}

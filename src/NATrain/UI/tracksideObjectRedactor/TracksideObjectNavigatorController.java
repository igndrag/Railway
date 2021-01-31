package NATrain.UI.tracksideObjectRedactor;

import NATrain.UI.tracksideObjectRedactor.TSORedactors.SignalRedactorController;
import NATrain.UI.tracksideObjectRedactor.TSORedactors.SwitchRedactorController;
import NATrain.UI.tracksideObjectRedactor.TSORedactors.TrackSectionRedactorController;
import NATrain.model.Model;
import NATrain.trackSideObjects.*;
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

    protected ObservableList<TracksideObject> trackSectionList;
    protected ObservableList<TracksideObject> switchList;
    protected ObservableList<TracksideObject> signalList;
    private static Stage primaryStage;

    @FXML
    private TableView<TracksideObject> switchTableView;
    @FXML
    private TableColumn<Switch, String> switchIdCol;
    @FXML
    private TableColumn<Switch, Integer> switchControlModuleCol;
    @FXML
    private TableColumn<Switch, Integer> switchChannelCol;
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
    private TableColumn<TrackSection, Integer> trackSectionControlModuleCol;
    @FXML
    private TableColumn<TrackSection, Integer> trackSectionChannelCol;
    @FXML
    protected TableView<TracksideObject> trackSectionsTableView;
    @FXML
    private TableView<TracksideObject> signalTableView;
    @FXML
    private TableColumn<Signal, Integer> signalIdCol;
    @FXML
    private TableColumn<Signal, Integer> signalControlModuleCol;
    @FXML
    private TableColumn<Signal, Integer> signalChannelCol;
    @FXML
    private Button newSignalButton;
    @FXML
    private Button editSignalButton;
    @FXML
    private Button deleteSignalButton;

    public static void setPrimaryStage(Stage mainStage) {
        primaryStage = mainStage;
    }

    public void initialize() {
        initTrackSectionsTab();
        initSwitchTab();
        initSignalTab();
    }

    private void toTrackSectionRedactor (TrackSection trackSection) throws IOException {
        FXMLLoader loader = new FXMLLoader(TrackSectionRedactorController.class.getResource("TrackSectionRedactor.fxml"));
        Stage trackSectionRedactor = new Stage();
        trackSectionRedactor.setTitle("Track Section Redactor");
        trackSectionRedactor.setScene(new Scene(loader.load(), 325, 200));
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

    public void initTrackSectionsTab() {
        trackSectionIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        trackSectionChannelCol.setCellValueFactory(new PropertyValueFactory<>("channel"));
        trackSectionControlModuleCol.setCellValueFactory(new PropertyValueFactory<>("controlModuleAddress"));
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
                trackSectionsTableView.setOnMouseClicked(null);
            }
        });

    }

    public void initSwitchTab() {
        switchIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        switchChannelCol.setCellValueFactory(new PropertyValueFactory<>("channel"));
        switchControlModuleCol.setCellValueFactory(new PropertyValueFactory<>("controlModuleAddress"));
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
                switchTableView.setOnMouseClicked(null);
            }
        });

    }

    public void initSignalTab() {
        signalIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        signalChannelCol.setCellValueFactory(new PropertyValueFactory<>("channel"));
        signalControlModuleCol.setCellValueFactory(new PropertyValueFactory<>("controlModuleAddress"));
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
                signalTableView.setOnMouseClicked(null);
            }
        });
    }
}

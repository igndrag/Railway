package NATrain.UI.workPlace;

import NATrain.UI.NavigatorFxController;
import NATrain.UI.workPlace.executors.ActionExecutor;
import NATrain.UI.workPlace.executors.AbstractRouteExecutor;
import NATrain.UI.workPlace.executors.RouteExecutor;
import NATrain.UI.workPlace.executors.RouteStatus;
import NATrain.connectionService.MQTTConnectionService;
import NATrain.model.Model;
import NATrain.quads.*;
import NATrain.routes.Track;
import NATrain.trackSideObjects.*;
import NATrain.trackSideObjects.locomotives.Locomotive;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalState;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.switches.SwitchState;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.trackSideObjects.trackSections.TrackSectionState;
import NATrain.сontrolModules.AbstractModule;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;
import java.util.List;

public class WorkPlaceController {




    public static WorkPlaceController getActiveController() {
        return activeController;
    }

    private static WorkPlaceController activeController;

    private static boolean activeMode = false;

    @FXML
    private BorderPane mainPane;
    @FXML
    private RadioMenuItem actionEmulatorRadioMenuItem;
    @FXML
    private RadioMenuItem locomotiveControllerRadioMenuItem;
    @FXML
    private RadioMenuItem connectionServiceEmulatorRadioMenuItem;
    @FXML
    private TextFlow log;
    @FXML
    private Label timeLabel;
    @FXML
    private TableView<RouteExecutor> routeStatusTableView;
    @FXML
    private TableColumn<RouteExecutor, String> routeIdColumn;
    @FXML
    private TableColumn<RouteExecutor, RouteStatus> routeStatusColumn;
    @FXML
    private Button routeCancellationButton;
    @FXML
    private Button sendConfigsButton;
    @FXML
    private Button updateButton;
    @FXML
    private ScrollPane workArea;

    private Stage primaryStage;

    private static GridPane gridPane;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static boolean isActiveMode() {
        return activeMode;
    }

    public static void setInactiveMode() {
        activeMode = false;
    }

    private static int rows;
    private static int columns;

    public void initialize() {
        rows = Model.getMainGrid().length;
        columns = Model.getMainGrid()[0].length;

        activeMode = true;
        activeController = this;
        MQTTConnectionService.connect();

        Model.getStationTracks().values().forEach(TrackSection::updateVacancyState);
        Model.getTrackSections().values().forEach(TrackSection::updateVacancyState);
        Model.getSwitches().values().forEach(aSwitch -> aSwitch.setSwitchState(SwitchState.PLUS));// TODO change it to global request for tests on real model
        Model.getTracks().forEach(track -> {
            track.getBlockSections().forEach(blockSection -> {
                if (blockSection.getNormalDirectionSignal() != null) {
                    blockSection.getNormalDirectionSignal().init();
                }
                if (blockSection.getReversedDirectionSignal() != null) {
                    blockSection.getReversedDirectionSignal().init();
                }
                blockSection.updateVacancyState();
                if (blockSection.getNormalDirectionSignal() != Signal.EMPTY_SIGNAL) {
                    if (!blockSection.isLastInNormalDirection()) {
                        blockSection.getNormalDirectionSignal().setSignalState(SignalState.GREEN);
                    } else {
                        blockSection.getNormalDirectionSignal().setSignalState(SignalState.YELLOW);
                    }
                }
                if (blockSection.getReversedDirectionSignal() != Signal.EMPTY_SIGNAL) {
                    blockSection.getReversedDirectionSignal().setSignalState(SignalState.NOT_LIGHT);
                }
            });
        });

        Model.getSignals().values().forEach(signal -> {
            signal.init();
            signal.close();
        });

        Model.getSwitches().values().forEach(Switch::init);

        Model.refreshAll();

        routeStatusTableView.setItems(ActionExecutor.getActiveRoutes());
        routeIdColumn.setCellValueFactory(new PropertyValueFactory<>("routeDescription"));
        routeStatusColumn.setCellValueFactory(new PropertyValueFactory<>("routeStatus"));

        AbstractRouteExecutor.setWorkPlaceController(this);

        NavigatorFxController.showGridLines = false;
        gridPane = new GridPane();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Pane quadPane = new Pane();
                Group quadView;
                Quad quad = Model.getMainGrid()[j][i];
                quad.getView().setOnMouseClicked(null); // clear actions from mosaic redactor
                if (quad.getType() == QuadType.EMPTY_QUAD) {
                    quadView = new Group();
                    Rectangle emptyBackground = new Rectangle(90, 80);
                    emptyBackground.setFill(AbstractQuad.DEFAULT_BACKGROUND_COLOR);
                    quadView.getChildren().add(emptyBackground);
                } else {
                    quad.setGridLineVisible(false);
                    quad.activateListeners();
                    quadView = quad.getView();
                    configQuadView(quad, i, j);
                }
                quadPane.getChildren().add(quadView);
                gridPane.add(quadPane, i, j);
            }
        }

        Model.getTracks().forEach(Track::activateSignalListeners);

        workArea.setContent(gridPane);

        Platform.runLater(() -> {
            int number = 0;
            for (Locomotive locomotive : Model.getLocomotives().values()) {
                try {
                    showLocomotiveController(locomotive, number);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                number++;
            }
            try {
                showMovableObjectsLocator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sendConfigsButton.setOnAction(event -> {
            if (Model.getTags().keySet().size() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("98:"); //set configs command code
                sb.append(String.format("%03d",Model.getTags().size()));
                for (RFIDTag tag : Model.getTags().values()) {
                    sb.append(":");
                    sb.append(String.format("%10d", tag.getDecUid()));
                }
                MqttMessage message = new MqttMessage(sb.toString().getBytes());
                message.setQos(1);
              //message.setRetained(true);
                try {
                    MQTTConnectionService.getClient().publish("NATrain/system/", message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });

        updateButton.setOnAction(event -> {
            Model.getSignals().values().forEach(signal -> {
                signal.setSignalState(signal.getSignalState());
            });
            Model.getTracks().forEach(track -> {
                track.getBlockSections().forEach(blockSection -> {
                    Signal normalDirectionSignal = blockSection.getNormalDirectionSignal();
                    Signal reversedDirectionSignal = blockSection.getReversedDirectionSignal();
                    if (normalDirectionSignal != null && normalDirectionSignal != Signal.EMPTY_SIGNAL) {
                        normalDirectionSignal.setSignalState(normalDirectionSignal.getSignalState());
                    }
                    if (reversedDirectionSignal != null && reversedDirectionSignal != Signal.EMPTY_SIGNAL) {
                        reversedDirectionSignal.setSignalState(reversedDirectionSignal.getSignalState());
                    }
                });
            });
        });

        //   ConnectionService connectionService = new ConnectionService("COM5");
        //   connectionService.start();

        log("Work Place initialized");
        log("Good Lock!!!");
    }


    private void configQuadView(Quad quad, int i, int j) {
        List<ControlAction> availableActions = quad.getAvailableActions();
        if (availableActions.size() > 0) {
            ContextMenu contextMenu = new ContextMenu();
            availableActions.forEach(controlAction -> {
                MenuItem menuItem = new MenuItem(controlAction.getDescription());
                menuItem.setOnAction(event -> {
                    ActionExecutor.executeControlAction(controlAction, quad);
                });
                contextMenu.getItems().add(menuItem);
            });

            quad.getView().setOnMouseClicked(event -> {
                contextMenu.show(mainPane, event.getScreenX(), event.getScreenY());
            });
            mainPane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                contextMenu.hide();
            });
        }

        Model.refreshAll();
    }

    public static void deactivateTrackSignalAutoselectors() {
        Model.getTracks().forEach(Track::deactivateSignalListeners);
    }

    public static void deactivateTracksideObjectStateListeners() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Pane quadPane = new Pane();
                Group quadView;
                Quad quad = Model.getMainGrid()[j][i];
                quad.deactivateListeners();
            }
        }
    }

    public synchronized void log(String message) {
        //https://stackoverflow.com/questions/40822806/add-elements-on-textflow-using-external-thread-in-javafx
        Platform.runLater(() -> {
            if (log.getChildren().size() > 4) {
                log.getChildren().remove(0);
            }
            log.getChildren().add(new Text(message + System.lineSeparator()));
        });
    }

    public void refreshRouteStatusTable() {
        routeStatusTableView.refresh();
    }

    public void showActionEmulator(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(ActionEmulatorController.class.getResource("ActionEmulator.fxml"));
        Stage actionEmulator = new Stage();
        actionEmulator.setTitle("Action Emulator");
        actionEmulator.setScene(new Scene(loader.load(), 800, 160));
        ActionEmulatorController controller = loader.getController();
        actionEmulator.setY(0);
        //controller.initialize();
        actionEmulator.setOnCloseRequest(event -> {
            actionEmulatorRadioMenuItem.setSelected(false);
        });
        actionEmulator.initOwner(primaryStage);
        actionEmulator.setAlwaysOnTop(true);
        actionEmulator.show();
    }

    public void showLocomotiveController(Locomotive locomotive, int number) throws IOException {
        FXMLLoader loader = new FXMLLoader(LocomotiveController.class.getResource("LocomotiveController.fxml"));
        Stage locomotiveController = new Stage();
        locomotiveController.setTitle("Locomotive Controller");
        locomotiveController.setScene(new Scene(loader.load(), 220, 480));
        locomotiveController.setResizable(false);
        locomotiveController.setX(10 + number * 230);
        locomotiveController.setY(0);
        LocomotiveController controller = loader.getController();
        controller.init(locomotive);
        locomotiveController.setOnCloseRequest(event -> {
            locomotiveControllerRadioMenuItem.setSelected(false);
        });
        locomotiveController.initOwner(primaryStage);
        locomotiveController.setAlwaysOnTop(true);
        locomotiveController.show();
    }

    public void showMovableObjectsLocator() throws IOException {
        FXMLLoader loader = new FXMLLoader(LocomotiveController.class.getResource("Locator.fxml"));
        Stage locator = new Stage();
        locator.setTitle("Locator");
        locator.setScene(new Scene(loader.load(), 600, 400));
        locator.setResizable(false);
        locator.setY(0);
        LocatorController controller = loader.getController();
        controller.init();
        locator.initOwner(primaryStage);
        locator.setAlwaysOnTop(true);
        locator.show();
    }

    @FXML
    private void cancelSelectedRoute() {
        if (!routeStatusTableView.getSelectionModel().isEmpty()) {
            routeStatusTableView.getSelectionModel().getSelectedItem().cancelRoute();
        }
    }

}


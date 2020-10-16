package NATrain.UI.workPlace;

import NATrain.UI.NavigatorFxController;
import NATrain.UI.workPlace.executors.ActionExecutor;
import NATrain.UI.workPlace.executors.RouteExecutor;
import NATrain.model.Model;
import NATrain.quads.*;
import NATrain.trackSideObjects.ControlAction;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class WorkPlaceController {


    @FXML
    private BorderPane mainPane;

    @FXML
    private RadioMenuItem actionEmulatorRadioMenuItem;
    @FXML
    private RadioMenuItem locomotiveControllerRadioMenuItem;
    @FXML
    private TextFlow log;
    @FXML
    private Label timeLabel;
    @FXML
    private TableView routeStatusTableView;
    @FXML
    private Button routeCancellationButton1;
    @FXML
    private ScrollPane workArea;


    private Stage primaryStage;

    private static GridPane gridPane;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        RouteExecutor.setWorkPlaceController(this);

        NavigatorFxController.showGridLines = false;
        gridPane = new GridPane();
        int raws = Model.getMainGrid().length;
        int columns = Model.getMainGrid()[0].length;


        for (int i = 0; i < raws; i++) {
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
        workArea.setContent(gridPane);

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
    }

    public void log(String message) {
        //https://stackoverflow.com/questions/40822806/add-elements-on-textflow-using-external-thread-in-javafx
        Platform.runLater(() -> {
            if (log.getChildren().size() > 4) {
                log.getChildren().remove(0);
            }
            log.getChildren().add(new Text(message + System.lineSeparator()));
        });
    }

    public void showActionEmulator(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(ActionEmulatorController.class.getResource("ActionEmulator.fxml"));
        Stage actionEmulator = new Stage();
        actionEmulator.setTitle("Action Emulator");
        actionEmulator.setScene(new Scene(loader.load(), 600, 160));
        ActionEmulatorController controller = loader.getController();
        //controller.initialize();
        actionEmulator.setOnCloseRequest(event -> {
            actionEmulatorRadioMenuItem.setSelected(false);
        });
        actionEmulator.show();
    }
}


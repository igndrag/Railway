package NATrain.UI.scenario;

import NATrain.model.Model;
import NATrain.routes.Route;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.Collections;

public class ScenarioNavigatorController {

    private static ObservableList<Scenario> scenarioObservableList;
    private static ObservableList<ScenarioAction> actionObservableList;

    @FXML
    private ListView scenarioListView;
    @FXML
    private ListView actionsListView;
    @FXML
    private Button createButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button upButton;
    @FXML
    private Button downButton;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void init() {
        scenarioObservableList = FXCollections.observableArrayList(Model.getScenarios().values());
    }


}

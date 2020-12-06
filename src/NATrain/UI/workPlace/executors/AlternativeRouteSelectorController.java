package NATrain.UI.workPlace.executors;

import NATrain.routes.Route;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AlternativeRouteSelectorController {
    @FXML
    private TableView<Route> alternativeRoutsTableView;
    @FXML
    private TableColumn<Route, String> routeDescriptionColumn;
    @FXML
    private TableColumn<Route, String> switchPositionsColumn;
    @FXML
    private Button okButton;

    public void initialize(ObservableList<Route> routes) {
        routeDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        switchPositionsColumn.setCellValueFactory(new PropertyValueFactory<>("SwitchPositions"));
        alternativeRoutsTableView.setItems(routes);

        okButton.setOnAction(event -> {
            if (!alternativeRoutsTableView.getSelectionModel().isEmpty()) {
                ActionExecutor.prepareRoute(alternativeRoutsTableView.getSelectionModel().getSelectedItem());
                Stage stage = (Stage) okButton.getScene().getWindow();
                stage.close();
            }
        });
    }
}

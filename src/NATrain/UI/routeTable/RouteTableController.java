package NATrain.UI.routeTable;

import NATrain.model.Model;
import NATrain.routes.Route;
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
import java.util.*;

public class RouteTableController {

    protected static ObservableList<Route> routeObservableList;
    private Stage primaryStage;

    private RouteTypeSelectorController selector = new RouteTypeSelectorController();

    @FXML
    private TableView<Route> routeTable;
    @FXML
    private TableColumn<Route, String> routeTypeColumn;
    @FXML
    private TableColumn<Route, String> descriptionColumn;
    @FXML
    private TableColumn<Route, String> signalColumn;
    @FXML
    private TableColumn<Route, String> switchPositionsColumn;
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        routeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("routeTypeName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        switchPositionsColumn.setCellValueFactory(new PropertyValueFactory<>("switchPositions"));
        signalColumn.setCellValueFactory(new PropertyValueFactory<>("signalName"));


        routeObservableList = FXCollections.observableList(new ArrayList<>(Model.getRouteTable()));
        routeTable.setItems(routeObservableList);

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        newButton.setOnMouseClicked(event -> {
            try {
                toRouteTypeSelector();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteButton.setOnAction(event -> {
            Route objectForDelete = routeTable.getSelectionModel().getSelectedItem();
            routeObservableList.remove(objectForDelete);
            Model.getRouteTable().remove(objectForDelete);
            if (routeObservableList.size() == 0) {
                editButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });

        editButton.setOnAction(event -> {
            Route selectedRoute = routeTable.getSelectionModel().getSelectedItem();
            switch (selectedRoute.getRouteType()) {
                case DEPARTURE:
                    try {
                        selector.toDepartureRouteEditor(selectedRoute);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        });

        routeTable.setOnMouseClicked(event -> {
            if (routeTable.getSelectionModel().getSelectedItem() != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
                routeTable.setOnMouseClicked(null);
            }
        });
    }

    private void toRouteTypeSelector() throws IOException{
        FXMLLoader loader = new FXMLLoader(RouteTypeSelectorController.class.getResource("RouteTypeSelector.fxml"));
        Stage routeTypeSelector = new Stage();
        routeTypeSelector.setTitle("Route Type Selector");
        routeTypeSelector.setScene(new Scene(loader.load(), 155, 185));
        routeTypeSelector.setResizable(false);
        RouteTypeSelectorController controller = loader.getController();
        controller.setRouteTableStage(primaryStage);
        routeTypeSelector.initModality(Modality.WINDOW_MODAL);
        routeTypeSelector.initOwner(primaryStage);
        routeTypeSelector.show();
    }
}

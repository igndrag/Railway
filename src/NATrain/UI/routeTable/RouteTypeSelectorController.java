package NATrain.UI.routeTable;

import NATrain.routes.DepartureRoute;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RouteTypeSelectorController {
    private Stage routeTableStage;

    @FXML
    private Button arrivalButton;
    @FXML
    private Button departureButton;
    @FXML
    private Button shuntingButton;
    @FXML
    private Button transitButton;

    public void setRouteTableStage(Stage routeTableStage) {
        this.routeTableStage = routeTableStage;
    }

    public void initialize() {
        departureButton.setOnAction(event -> {
                try {
                    toDepartureRouteEditor(new DepartureRoute("New Departure Route"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
        });
    }

    protected void toDepartureRouteEditor(DepartureRoute departureRoute) throws IOException {
        FXMLLoader loader = new FXMLLoader(DepartureRouteEditorController.class.getResource("DepartureRouteEditor.fxml"));
        Stage departureRouteEditor = new Stage();
        departureRouteEditor.setTitle("Departure Route Editor");
        departureRouteEditor.setScene(new Scene(loader.load(), 640, 400));
        departureRouteEditor.setResizable(false);
        DepartureRouteEditorController controller = loader.getController();
        controller.initialize(departureRoute);
        departureRouteEditor.initModality(Modality.WINDOW_MODAL);
        departureRouteEditor.initOwner(routeTableStage);
        departureRouteEditor.show();

        if (arrivalButton != null) { // for avoid NPE when editor called directly by RouteTableController
            Stage thisStage = (Stage) arrivalButton.getScene().getWindow();
            thisStage.close();
        }
    }


}

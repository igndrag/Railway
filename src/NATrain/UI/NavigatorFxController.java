package NATrain.UI;

import NATrain.UI.mosaicRedactor.MosaicRedactorFxController;
import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.utils.ModelMock;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigatorFxController {

    private static Stage primaryStage;
    public static boolean constructorMode = false;

    @FXML
    private Button trackSideObjectRedactorButton;

    @FXML
    private Button mosaicRedactorButton;


    public static void setPrimaryStage(Stage mainStage) {
        primaryStage = mainStage;
    }

    public void initialize() {
        ModelMock.MockModel();
    }

    public void toTrackRedactor(ActionEvent actionEvent) throws IOException {
        constructorMode = true;
        FXMLLoader loader = new FXMLLoader(MosaicRedactorFxController.class.getResource("mosaicTrackRedactor.fxml"));
        Stage trackRedactor = new Stage();
        trackRedactor.setTitle("Mosaic Redactor");
        trackRedactor.setScene(new Scene(loader.load(), 800, 600));
        trackRedactor.setResizable(true);
        //MosaicRedactorFxController controller = loader.getController();
        //controller.initialize();
        MosaicRedactorFxController.setPrimaryStage(trackRedactor);
        trackRedactor.setOnCloseRequest(event -> {
            primaryStage.show();
            constructorMode = false;
        });
        primaryStage.hide();
        trackRedactor.show();
    }


    public void toTracksideObjectRedactor(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(TracksideObjectRedactorController.class.getResource("tracksideObjectConfigurator.fxml"));
        Stage tracksideObjectRedactor = new Stage();
        tracksideObjectRedactor.setTitle("Trackside Object Redactor");
        tracksideObjectRedactor.setScene(new Scene(loader.load(), 350, 500));
        tracksideObjectRedactor.setResizable(false);
        //MosaicRedactorFxController controller = loader.getController();
        //controller.initialize();
        TracksideObjectRedactorController.setPrimaryStage(tracksideObjectRedactor);
        tracksideObjectRedactor.setOnCloseRequest(event -> {
            primaryStage.show();
        });
        primaryStage.hide();
        tracksideObjectRedactor.show();
    }
}

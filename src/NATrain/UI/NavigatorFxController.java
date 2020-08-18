package NATrain.UI;

import NATrain.UI.mosaicRedactor.MosaicRedactorFxController;
import NATrain.UI.tracksideObjectRedactor.TracksideObjectNavigatorController;
import NATrain.model.Model;
import NATrain.utils.ModelMock;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;

public class NavigatorFxController {

    private static Stage primaryStage;
    public static boolean constructorMode = false;
    public static String modelURL = "model.ntm";

    @FXML
    private Button controlButton;

    @FXML
    private Button settingButton;

    @FXML
    private Button trackSideObjectRedactorButton;

    @FXML
    private Button mosaicRedactorButton;

    public static void setPrimaryStage(Stage mainStage) {
        primaryStage = mainStage;
    }

    public void initialize() {
        constructorMode = true;
        ModelMock.MockModel();
        constructorMode = false;
    }

    public void toTrackRedactor(ActionEvent actionEvent) throws IOException {
       // Model.loadFromDisk();
        constructorMode = true;
        FXMLLoader loader = new FXMLLoader(MosaicRedactorFxController.class.getResource("mosaicTrackRedactor.fxml"));
        Stage trackRedactor = new Stage();
        MosaicRedactorFxController.setPrimaryStage(trackRedactor);
        trackRedactor.setTitle("Mosaic Redactor");
        trackRedactor.setScene(new Scene(loader.load(), 800, 600));
        trackRedactor.setResizable(true);
        //MosaicRedactorFxController controller = loader.getController();
        //controller.initialize();

        trackRedactor.setOnCloseRequest(event -> {
        //    Model.saveOnDisk();
            primaryStage.show();
            constructorMode = false;
        });
        primaryStage.hide();
        trackRedactor.show();
    }


    public void toTracksideObjectRedactor(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(TracksideObjectNavigatorController.class.getResource("tracksideObjectNavigator.fxml"));
        Stage tracksideObjectRedactor = new Stage();
        tracksideObjectRedactor.setTitle("Trackside Object Redactor");
        tracksideObjectRedactor.setScene(new Scene(loader.load(), 350, 500));
        tracksideObjectRedactor.setResizable(false);
        //MosaicRedactorFxController controller = loader.getController();
        //controller.initialize();
        TracksideObjectNavigatorController.setPrimaryStage(tracksideObjectRedactor);
        tracksideObjectRedactor.setOnCloseRequest(event -> {
            primaryStage.show();
        });
        primaryStage.hide();
        tracksideObjectRedactor.show();
    }

    @FXML
    private void saveCollection(MouseEvent mouseEvent) {
        Model.saveOnDisk();
    }

    @FXML
    private void loadCollection(MouseEvent mouseEvent) {
        Model.loadFromDisk();
    }

}

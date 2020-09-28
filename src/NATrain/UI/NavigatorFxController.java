package NATrain.UI;

import NATrain.UI.controlModuleRedactor.CMNavigatorController;
import NATrain.UI.mosaicRedactor.MosaicRedactorFxController;
import NATrain.UI.routeTable.RouteTableController;
import NATrain.UI.tracksideObjectRedactor.TracksideObjectNavigatorController;
import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.model.Model;
import NATrain.quads.AbstractQuad;
import NATrain.utils.ModelMock;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;

public class NavigatorFxController {

    private static Stage primaryStage;
    public static Boolean showGridLines = true;
    public static String modelURL = "model.ntm";

    @FXML
    private Button controlButton;

    @FXML
    private Button settingButton;

    @FXML
    private Button trackSideObjectRedactorButton;

    @FXML
    private Button mosaicRedactorButton;

    private static Timeline blinker;

    public static void setPrimaryStage(Stage mainStage) {
        primaryStage = mainStage;
    }

    public void initialize() {
        ModelMock.MockModel();
        blinker = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            AbstractQuad.blink();
                            Model.refreshAll();
                        }
                ));
        blinker.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    private void toTrackRedactor(ActionEvent actionEvent) throws IOException {
       // Model.loadFromDisk();
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
        });
        primaryStage.hide();
        trackRedactor.show();
    }

    @FXML
    private void toTracksideObjectRedactor(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(TracksideObjectNavigatorController.class.getResource("tracksideObjectNavigator.fxml"));
        Stage tracksideObjectRedactor = new Stage();
        tracksideObjectRedactor.setTitle("Trackside Object Navigator");
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
    private void toControlModuleRedactor (ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(CMNavigatorController.class.getResource("CMNavigator.fxml"));
        Stage controlModuleNavigator = new Stage();
        controlModuleNavigator.setTitle("Control Module Navigator");
        controlModuleNavigator.setScene(new Scene(loader.load(), 430, 415));
        controlModuleNavigator.setResizable(false);
        CMNavigatorController controller = loader.getController();
        //controller.initialize();
        controller.setPrimaryStage(controlModuleNavigator);
        controlModuleNavigator.setOnCloseRequest(event -> {
            primaryStage.show();
        });
        primaryStage.hide();
        controlModuleNavigator.show();
    }

    @FXML
    private void toRouteTable() throws IOException{
        FXMLLoader loader = new FXMLLoader(RouteTableController.class.getResource("RouteTable.fxml"));
        Stage routeTable = new Stage();
        routeTable.setTitle("Route Table");
        routeTable.setScene(new Scene(loader.load(), 600, 350));
        routeTable.setResizable(false);
        RouteTableController controller = loader.getController();
        controller.setPrimaryStage(routeTable);
        blinker.play();
        //controller.initialize();
        routeTable.setOnCloseRequest(event -> {
            primaryStage.show();
        });
        primaryStage.hide();
        routeTable.show();
        blinker.pause();
    }

    @FXML
    private void toWorkPlace () throws IOException {
        FXMLLoader loader = new FXMLLoader(WorkPlaceController.class.getResource("WorkPlace.fxml"));
        Stage workPlace = new Stage();
        workPlace.setTitle("Work Place");
        workPlace.setScene(new Scene(loader.load(), 800, 510));
        WorkPlaceController controller = loader.getController();
        controller.setPrimaryStage(workPlace);
        blinker.play();
        //controller.initialize();
        workPlace.setOnCloseRequest(event -> {
            primaryStage.show();
            blinker.pause();
        });
        primaryStage.hide();
        workPlace.show();
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

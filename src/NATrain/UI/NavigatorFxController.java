package NATrain.UI;

import NATrain.UI.controlModuleRedactor.CMNavigatorController;
import NATrain.UI.mosaicRedactor.MosaicRedactorFxController;
import NATrain.UI.routeTable.RouteTableController;
import NATrain.UI.tracks.TrackSelectorController;
import NATrain.UI.tracksideObjectRedactor.TracksideObjectNavigatorController;
import NATrain.UI.workPlace.Blinker;
import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.connectionService.MQTTConnectionService;
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
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NavigatorFxController {

    private static Stage primaryStage;
    public static Boolean showGridLines = true;

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

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public void initialize() {
        AppConfigController.loadConfigs();
        Model.loadFromDisk();//
        //ModelMock.MockModel();
       }

    @FXML
    private void toMosaicRedactor(ActionEvent actionEvent) throws IOException {
       // Model.loadFromDisk();
        FXMLLoader loader = new FXMLLoader(MosaicRedactorFxController.class.getResource("MosaicRedactor.fxml"));
        Stage mosaicRedactor = new Stage();
        mosaicRedactor.setTitle("Mosaic Redactor");
        mosaicRedactor.setScene(new Scene(loader.load(), 800, 600));
        mosaicRedactor.setResizable(true);
        MosaicRedactorFxController.setPrimaryStage(mosaicRedactor);
        MosaicRedactorFxController controller = loader.getController();
        controller.activateKeyListeners();
        //controller.initialize();
        mosaicRedactor.setOnCloseRequest(event -> {
        //    Model.saveOnDisk();
            primaryStage.show();
            if (controller.getSelectedQuad() != null) {
                controller.getSelectedQuad().unselect();
                MosaicRedactorFxController.unselectQuadType();
            }
        });
        primaryStage.hide();
        mosaicRedactor.show();
    }

    @FXML
    private void toTracksideObjectRedactor(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(TracksideObjectNavigatorController.class.getResource("TracksideObjectNavigator.fxml"));
        Stage tracksideObjectRedactor = new Stage();
        tracksideObjectRedactor.setTitle("Trackside Object Navigator");
        tracksideObjectRedactor.setScene(new Scene(loader.load(), 500, 500));
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
        controlModuleNavigator.setScene(new Scene(loader.load(), 1000, 415));
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
        Blinker.start(); // for preview quads!
        //controller.initialize();
        routeTable.setOnCloseRequest(event -> {
            primaryStage.show();
        });
        primaryStage.hide();
        routeTable.show();
        Blinker.stop();
    }

    @FXML
    private void toWorkPlace () throws IOException {
        FXMLLoader loader = new FXMLLoader(WorkPlaceController.class.getResource("WorkPlace.fxml"));
        Stage workPlace = new Stage();
        workPlace.setTitle("Work Place");
        workPlace.setScene(new Scene(loader.load(), 800, 510));
        WorkPlaceController controller = loader.getController();
        controller.setPrimaryStage(workPlace);
        Blinker.start();
        //controller.initialize();
        workPlace.setOnCloseRequest(event -> {
                WorkPlaceController.setActiveMode(false);
            try {
                MQTTConnectionService.getClient().disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
            primaryStage.show();
            Blinker.stop();
        });
        primaryStage.hide();
        workPlace.show();
    }

    @FXML
    public void toAppConfig() throws IOException {
        FXMLLoader loader = new FXMLLoader(AppConfigController.class.getResource("AppConfig.fxml"));
        Stage appConfig = new Stage();
        appConfig.setTitle("Application Configs");
        appConfig.setScene(new Scene(loader.load(), 450, 200));
        AppConfigController controller = loader.getController();
        controller.setPrimaryStage(appConfig);
        appConfig.setOnCloseRequest(event -> {
            primaryStage.show();
        });
        primaryStage.hide();
        appConfig.show();
    }

    @FXML
    private void toTrackSelector() throws IOException {
        FXMLLoader loader = new FXMLLoader(TrackSelectorController.class.getResource("TrackSelector.fxml"));
        Stage trackSelector = new Stage();
        trackSelector.setTitle("Track Selector");
        trackSelector.setScene(new Scene(loader.load(), 600, 300));
        TrackSelectorController controller = loader.getController();
        controller.setPrimaryStage(trackSelector);
        trackSelector.setOnCloseRequest(event -> {
            primaryStage.show();
        });
        primaryStage.hide();
        trackSelector.show();
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

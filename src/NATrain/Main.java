package NATrain;

import NATrain.UI.AppConfigController;
import NATrain.UI.Lang;
import NATrain.UI.NavigatorFxController;
import NATrain.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //  ConnectionService service = new ConnectionService("COM8");
        //  service.start();
        AppConfigController.loadConfigs();
        FXMLLoader loader;
        switch (AppConfigController.getLanguage()) {
            case RU:
                loader = new FXMLLoader(getClass().getResource("UI/Navigator_RU.fxml"));
                break;
            default:
                loader = new FXMLLoader(getClass().getResource("UI/Navigator.fxml"));
                break;
        }
        Parent root = loader.load();
        primaryStage.setTitle("NATrain");
        primaryStage.setScene(new Scene(root, 1030, 140));
        NavigatorFxController controller = loader.getController();
        NavigatorFxController.setPrimaryStage(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.show();
        controller.initialize(checkModelURLConfigured());
    }

    private static boolean checkModelURLConfigured() {
        if (AppConfigController.getModelURL() != null) {
            File expectedModelFile = new File(AppConfigController.getModelURL());
            if (expectedModelFile.exists()) {
                Model.loadFromDisk();
                return true;
            } else {
                Model.initEmptyModel();
                return false;
            }
        } else {
            Model.initEmptyModel();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


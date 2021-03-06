package NATrain;

import NATrain.UI.NavigatorFxController;
import NATrain.connectionService.MQTTConnectionService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
      //  ConnectionService service = new ConnectionService("COM8");
      //  service.start();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("UI/Navigator.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("NATrain");
        primaryStage.setScene(new Scene(root, 1030, 140));
        NavigatorFxController controller = loader.getController();
        NavigatorFxController.setPrimaryStage(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

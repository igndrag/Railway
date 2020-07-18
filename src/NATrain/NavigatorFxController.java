package NATrain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigatorFxController {

    private static Stage primaryStage;
    static boolean constructorMode = true;

    @FXML
    private Button mosaicRedactorButton;


    public static void setPrimaryStage(Stage mainStage) {
        primaryStage = mainStage;
    }

    public void toTrackRedactor(ActionEvent actionEvent) throws IOException {
        constructorMode = true;
        FXMLLoader loader = new FXMLLoader(TrackRedactorFxController.class.getResource("trackRedactor.fxml"));
        Stage trackRedactor = new Stage();
        trackRedactor.setTitle("TrackRedactor");
        trackRedactor.setScene(new Scene(loader.load(), 800, 600));
        trackRedactor.setResizable(true);
        TrackRedactorFxController controller = loader.getController();
        controller.initialize();
        TrackRedactorFxController.setPrimaryStage(trackRedactor);
        trackRedactor.setOnCloseRequest(event -> primaryStage.show());
        primaryStage.hide();
        trackRedactor.show();
    }
}

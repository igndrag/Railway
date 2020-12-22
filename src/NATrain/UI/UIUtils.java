package NATrain.UI;

import javafx.scene.control.Alert;

public class UIUtils {
    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.show();
    }
}

package NATrain.UI;

import NATrain.model.Model;
import NATrain.utils.UtilFunctions;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppConfigController {

    private Stage primaryStage;
    private static final String configURL = "config.ntc";
    public static int comPortNumber = 1;

    @FXML
    private TextField modelPathTextField;
    @FXML
    private Label testResultLabel;
    @FXML
    private ColorPicker backgroundColorPicker;
    @FXML
    private TextField portNumberTextField;
    @FXML
    private Button testConnectionButton;


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        if (Model.getModelURL() == null) {
            modelPathTextField.setText("Not selected");
        } else {
            modelPathTextField.setText(Model.getModelURL());
        }
        portNumberTextField.setText(String.valueOf(comPortNumber));
    }

    @FXML
    private void browse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Model Directory");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("NATrains model files (*.ntm)", "*.ntm");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);//Указываем текущую сцену CodeNote.mainStage
        if (file != null) {
            modelPathTextField.setText(file.getAbsolutePath());
        } else {
            UIUtils.showAlert("Wrong model location!");
        }
    }

    public static void loadConfigs() {
        try {
            Path configPath = Paths.get(configURL);
            if (configPath.toFile().exists()) {
                FileInputStream fileInputStream = new FileInputStream(configPath.toFile());
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                String modelDirectoryAddress = (String) objectInputStream.readObject();
                comPortNumber = (Integer) objectInputStream.readObject();
                objectInputStream.close();
                Model.setModelURL(modelDirectoryAddress);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Model file isn't configured, load process failed!");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveConfigs() {
        try {
            Path configPath = Paths.get(configURL);
            configPath.toFile().createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(configPath.toFile());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(modelPathTextField.getText());
            Model.setModelURL(modelPathTextField.getText());
            File expectedModelFile = new File(modelPathTextField.getText());
            if (!expectedModelFile.exists()) {
                try {
                    expectedModelFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Model.saveOnDisk();
            } else {
                Model.loadFromDisk();
            }
            int expectedPortNumber = UtilFunctions.parseIfPositiveNumeric(portNumberTextField.getText());
            if (expectedPortNumber >= 0) {
                objectOutputStream.writeObject(expectedPortNumber);
                comPortNumber = expectedPortNumber;
            } else {
                objectOutputStream.writeObject(1);
                comPortNumber = 1;
            }
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.close();
        NavigatorFxController.getPrimaryStage().show();
    }
}


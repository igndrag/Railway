package NATrain.UI;

import NATrain.model.Model;
import NATrain.utils.UtilFunctions;
import javafx.collections.FXCollections;
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
    private static Lang language = Lang.ENG;
    private static String modelURL;

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
    @FXML
    private ChoiceBox<Lang> languageChoiceBox;

    public static Lang getLanguage() {
        return language;
    }

    public static String getModelURL() {
        return modelURL;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        languageChoiceBox.setItems(FXCollections.observableArrayList(Lang.values()));
        if (modelURL == null) {
            modelPathTextField.setText("Not selected");
            languageChoiceBox.getSelectionModel().select(Lang.ENG);
        } else {
            modelPathTextField.setText(modelURL);
            languageChoiceBox.getSelectionModel().select(language);
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
                language = (Lang) objectInputStream.readObject();
                objectInputStream.close();
                modelURL = modelDirectoryAddress;
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
            modelURL = modelPathTextField.getText();
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
            objectOutputStream.writeObject(languageChoiceBox.getSelectionModel().getSelectedItem());
            language = languageChoiceBox.getValue();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.close();
        NavigatorFxController.getPrimaryStage().show();
    }
}


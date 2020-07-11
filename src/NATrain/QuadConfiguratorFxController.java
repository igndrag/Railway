package NATrain;

import NATrain.model.Model;
import NATrain.quads.AbstractQuad;
import NATrain.quads.Quad;
import NATrain.quads.BaseQuad;
import NATrain.view.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Observable;

public class QuadConfiguratorFxController {

    @FXML
    private Button saveButton;

    @FXML
    private CheckBox showDescriptionCheckBox;

    @FXML
    private CheckBox trackSectionBorderCheckBox;

    @FXML
    private ChoiceBox<String> firstTrackSectionChoiceBox;

    @FXML
    private ChoiceBox<String> secondTrackSectionChoiceBox;

    @FXML
    private ChoiceBox<String> signalChoiceBox;

    @FXML
    private ChoiceBox switchChoiceBox;

    @FXML
    private Pane quadView;
    private BaseQuad quadForConfig;
    private Stage stage;


    public void initialize(int x, int y) {
        quadForConfig = (BaseQuad) Model.getMainGrid()[y][x];
        quadForConfig.getBackground().setFill(AbstractQuad.DEFAULT_BACKGROUND_COLOR);
        quadView.getChildren().add(quadForConfig.getView());
        stage = (Stage) saveButton.getScene().getWindow();
        firstTrackSectionChoiceBox.getItems().addAll(Model.getSwitches().keySet());
        stage.setOnCloseRequest(event -> saveAndClose());
        // receive track side element lists TODO
    }

    @FXML
    private void saveAndClose() {
        quadForConfig.refresh();
        stage.close();
    }
}

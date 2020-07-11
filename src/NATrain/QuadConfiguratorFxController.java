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
import javafx.scene.Group;
import javafx.scene.Parent;
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
    private Pane parent;

    public void initialize(int x, int y) {
        quadForConfig = (BaseQuad) Model.getMainGrid()[y][x];

        if (quadForConfig.getFirstAssociatedTrack() != null)
            firstTrackSectionChoiceBox.setValue(quadForConfig.getFirstAssociatedTrack().getId());

        parent = (Pane) quadForConfig.getView().getParent();
        quadForConfig.getBackground().setFill(AbstractQuad.DEFAULT_BACKGROUND_COLOR);
        quadView.getChildren().add(quadForConfig.getView());
        stage = (Stage) saveButton.getScene().getWindow();
        firstTrackSectionChoiceBox.getItems().addAll(Model.getTrackSections().keySet());
        stage.setOnCloseRequest(event -> saveAndClose());
        firstTrackSectionChoiceBox.setOnAction(event -> {
            quadForConfig.setFirstAssociatedTrack(Model.getTrackSections().get(firstTrackSectionChoiceBox.getValue()));
            quadForConfig.refresh();
        });

        // receive track side element lists TODO
    }

    @FXML
    private void saveAndClose() {
        parent.getChildren().add(quadForConfig.getView());
        stage.close();
    }
}

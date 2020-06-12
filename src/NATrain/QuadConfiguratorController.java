package NATrain;

import NATrain.model.Model;
import NATrain.quads.Quad;
import NATrain.quads.QuadImpl;
import NATrain.view.View;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class QuadConfiguratorController {

    @FXML
    private Button saveButton;

    @FXML
    private CheckBox showDescriptionCheckBox;

    @FXML
    private CheckBox trackSectionBorderCheckBox;

    @FXML
    private ChoiceBox firstTrackSectionChoiceBox;

    @FXML
    private ChoiceBox secondTrackSectionChoiceBox;

    @FXML
    private ChoiceBox signalChoiceBox;

    @FXML
    private ChoiceBox switchChoiceBox;

    @FXML
    private Pane quadView;
    private QuadImpl quadForConfig;
    private Pane parent;
    private Stage stage;


    public void initialize(int x, int y) {
        Quad quadFromGrid = View.getMainGrid()[y][x];
        quadForConfig = Model.getNotEmptyQuads().get(quadFromGrid.getId());
        parent = (Pane) quadFromGrid.getView().getParent();
        quadView.getChildren().add(quadForConfig.getView());
        stage = (Stage) saveButton.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                saveAndClose();
            }
        });
        // receive track side element lists TODO
    }

    @FXML
    private void saveAndClose() {
        quadForConfig.refresh();
        parent.getChildren().add(quadForConfig.getView());

        stage.close();
    }
}

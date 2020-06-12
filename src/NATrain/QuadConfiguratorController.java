package NATrain;

import NATrain.model.MainModel;
import NATrain.quads.Quad;
import NATrain.quads.QuadImpl;
import NATrain.view.View;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

public class QuadConfiguratorController {

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


    public void initialize(int x, int y) {
        Quad quadFromGrid = View.getMainGrid()[y][x];
        QuadImpl quadForConfig = MainModel.getNotEmptyQuads().get(quadFromGrid.getId());
        quadView.getChildren().add(quadForConfig.getView());
    }
}

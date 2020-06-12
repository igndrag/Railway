package NATrain;

import NATrain.library.QuadType;
import NATrain.model.MainModel;
import NATrain.quads.EmptyQuad;
import NATrain.quads.Quad;
import NATrain.quads.QuadImpl;
import NATrain.utils.QuadFactory;
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


    public void initialize(int x, int y, QuadType quadType) {
        QuadImpl quadForConfig;
        Quad quadFromGrid = View.getMainGrid()[x][y];
        if (quadFromGrid instanceof EmptyQuad) {
            quadForConfig = QuadFactory.createQuad(x, y, quadType);
        } else {
            quadForConfig = MainModel.getNotEmptyQuads().get(quadFromGrid.getID());
        }
        quadView.getChildren().add(quadForConfig.getView());
    }
}

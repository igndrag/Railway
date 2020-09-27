package NATrain.UI.workPlace;

import NATrain.UI.NavigatorFxController;
import NATrain.model.Model;
import NATrain.quads.*;
import NATrain.trackSideObjects.TrackSection;
import NATrain.trackSideObjects.TracksideObject;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextFlow;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class WorkPlaceController {

    @FXML
    private TextFlow log;
    @FXML
    private Label timeLabel;
    @FXML
    private TableView routeStatusTableView;
    @FXML
    private Button routeCancellationButton1;
    @FXML
    private ScrollPane workArea;

    private static GridPane gridPane;

    public void initialize() {
        NavigatorFxController.showGridLines = false;
        gridPane = new GridPane();
        int raws = Model.getMainGrid().length;
        int columns = Model.getMainGrid()[0].length;

        for (int i = 0; i < raws; i++) {
            for (int j = 0; j < columns; j++) {
                Pane quadPane = new Pane();
                Group quadView;
                Quad quad = Model.getMainGrid()[j][i];
                if (quad.getType() == QuadType.EMPTY_QUAD) {
                    quadView = new Group();
                    Rectangle emptyBackground = new Rectangle(90, 80);
                    emptyBackground.setFill(AbstractQuad.DEFAULT_BACKGROUND_COLOR);
                    quadView.getChildren().add(emptyBackground);
                } else {
                    quad.setGridLineVisible(false);
                    quadView = quad.getView();
                    configQuadView(quadView, i, j);
                }
                quadPane.getChildren().add(quadView);
                gridPane.add(quadPane, i, j);
            }
        }
        workArea.setContent(gridPane);
    }


    private void configQuadView(Group quadView, int i, int j) {

    }

    private class quadViewUpdater implements PropertyChangeListener {
        private Quad quad;

        quadViewUpdater(Quad quad) {
            this.quad = quad;
        }

        @Override
        public void propertyChange(PropertyChangeEvent event) {
            quad.refresh();
        }

    }

}

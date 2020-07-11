package NATrain.utils;

import NATrain.model.Model;
import NATrain.quads.EmptyQuad;
import NATrain.quads.Quad;
import NATrain.trackSideObjects.Signal;
import NATrain.view.View;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;

public class ModelMock {

    public static void MockModel() {

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Model.getMainGrid()[j][i] = new EmptyQuad(i, j);
            }
        }

        Model.getSignals().add(new Signal());
    }
}
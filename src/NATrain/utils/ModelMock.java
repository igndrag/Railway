package NATrain.utils;

import NATrain.model.Model;
import NATrain.quads.EmptyQuad;
import NATrain.view.View;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;

public class ModelMock {

private static Model modelMock;

    public static Model getModelMock() {
        if (modelMock == null) {
            modelMock = new Model();
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    EmptyQuad emptyQuad = new EmptyQuad(i, j);
                }
            }
        }
        return modelMock;
    }

    public static void saveModelMock(Model model) {
        modelMock = model;
    }
}

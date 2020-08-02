package NATrain.utils;

import NATrain.quads.QuadType;
import NATrain.quads.BaseQuad;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class QuadFactory {
    private static int counter = 0;

    public static Group redactorModeQuadView = new Group();

    static {
        redactorModeQuadView.getChildren().add(QuadPainter.getQuadBoarder());
        Text text = new Text("Configuring...");
        text.setY(45);
        text.setWrappingWidth(90);
        text.setTextAlignment(TextAlignment.CENTER);
        redactorModeQuadView.getChildren().add(text);
    }

    public static BaseQuad createQuad (int x, int y, QuadType quadType) {
        if (quadType == QuadType.STQ1_1)
            return SimpleTrackQuadFactory.createQuad(x, y, quadType);
        BaseQuad resultQuad = new BaseQuad(x, y);
        resultQuad.setQuadType(quadType);
        String id = quadType.toString() + counter;
        resultQuad.setId(id);
        counter++;
        QuadPainter.paintQuadViewForType(resultQuad, quadType);
        return resultQuad;
    }


}

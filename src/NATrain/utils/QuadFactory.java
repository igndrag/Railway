package NATrain.utils;

import NATrain.quads.DTQ.DTQ1_1;
import NATrain.quads.QuadType;
import NATrain.quads.BaseQuad;
import NATrain.quads.SIQ.SIQ1_1;
import NATrain.quads.STQ.STQ1_1;
import NATrain.quads.STQ.STQ1_2;
import NATrain.quads.STQ.STQ2_1;
import NATrain.quads.STQ.STQ2_2;
import NATrain.quads.SWQ.SWQ1_1;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class QuadFactory {

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
       switch (quadType) {
           case STQ1_1:
               return new STQ1_1(x, y);
           case STQ1_2:
               return new STQ1_2(x, y);
           case STQ2_1:
               return new STQ2_1(x, y);
           case STQ2_2:
               return new STQ2_2(x, y);
           case DTQ1_1:
               return new DTQ1_1(x, y);
           case SIQ1_1:
               return new SIQ1_1(x, y);
           case SWQ1_1:
               return new SWQ1_1(x, y);
       }
        BaseQuad resultQuad = new BaseQuad(x, y);
        return resultQuad;
    }


}

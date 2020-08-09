package NATrain.utils;

import NATrain.quads.DSQ.DSQ1_1;
import NATrain.quads.DTQ.DTQ1_1;
import NATrain.quads.EmptyQuad;
import NATrain.quads.Quad;
import NATrain.quads.QuadType;
import NATrain.quads.SIQ.*;
import NATrain.quads.STQ.*;
import NATrain.quads.SWQ.*;
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

    public static Quad createQuad (int x, int y, QuadType quadType) {
       switch (quadType) {
           case STQ1_1:
               return new STQ1_1(x, y);
           case STQ1_2:
               return new STQ1_2(x, y);
           case STQ2_1:
               return new STQ2_1(x, y);
           case STQ2_2:
               return new STQ2_2(x, y);
           case STQ5_1:
               return new STQ5_1(x,y);
           case DTQ1_1:
               return new DTQ1_1(x, y);
           case SIQ1_1:
               return new SIQ1_1(x, y);
           case SWQ1_1:
               return new SWQ1_1(x, y);
           case DSQ1_1:
               return new DSQ1_1(x, y);
       }
        return new EmptyQuad(x, y);
    }


}

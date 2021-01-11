package NATrain.utils;

import NATrain.quads.AbstractQuad;
import NATrain.quads.BSQ.BSQ1_1;
import NATrain.quads.BTQ.BTQ1_1;
import NATrain.quads.DTQ.DTQ1_1;
import NATrain.quads.DTQ.DTQ1_2;
import NATrain.quads.DTQ.DTQ2_1;
import NATrain.quads.DTQ.DTQ2_2;
import NATrain.quads.EmptyQuad;
import NATrain.quads.Quad;
import NATrain.quads.QuadType;
import NATrain.quads.SIQ.*;
import NATrain.quads.STQ.*;
import NATrain.quads.SWQ.*;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;



public class QuadFactory {

    public static Group redactorModeQuadView = new Group();

    static {
        Rectangle background = new Rectangle(90, 80);
        background.setFill(AbstractQuad.DEFAULT_BACKGROUND_COLOR);
        redactorModeQuadView.getChildren().add(background);
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
           case STQ3_1:
               return new STQ3_1(x, y);
           case STQ3_2:
               return new STQ3_2(x, y);
           case STQ3_3:
               return new STQ3_3(x, y);
           case STQ3_4:
               return new STQ3_4(x, y);
           case STQ4_1:
               return new STQ4_1(x, y);
           case STQ4_2:
               return new STQ4_2(x, y);
           case STQ4_3:
               return new STQ4_3(x, y);
           case STQ4_4:
               return new STQ4_4(x, y);
           case STQ5_1:
               return new STQ5_1(x,y);
           case STQ5_2:
               return new STQ5_2(x, y);
           case STQ6_1:
               return new STQ6_1(x, y);
           case STQ6_2:
               return new STQ6_2(x, y);
           case DTQ1_1:
               return new DTQ1_1(x, y);
           case DTQ1_2:
               return new DTQ1_2(x, y);
           case DTQ2_1:
               return new DTQ2_1(x, y);
           case DTQ2_2:
               return new DTQ2_2(x ,y);
           case SIQ1_1:
               return new SIQ1_1(x, y);
           case SIQ1_2:
               return new SIQ1_2(x, y);
           case SIQ2_1:
               return new SIQ2_1(x, y);
           case SIQ2_2:
               return new SIQ2_2(x, y);
           case SIQ2_3:
               return new SIQ2_3(x, y);
           case SIQ2_4:
               return new SIQ2_4(x, y);
           case SIQ3_1:
               return new SIQ3_1(x, y);
           case SIQ3_2:
               return new SIQ3_2(x, y);
           case SIQ4_1:
               return new SIQ4_1(x, y);
           case SIQ4_2:
               return new SIQ4_2(x, y);
           case SWQ1_1:
               return new SWQ1_1(x, y);
           case SWQ1_2:
               return new SWQ1_2(x, y);
           case SWQ1_3:
               return new SWQ1_3(x, y);
           case SWQ1_4:
               return new SWQ1_4(x, y);
           case SWQ2_1:
               return new SWQ2_1(x, y);
           case SWQ2_2:
               return new SWQ2_2(x, y);
           case SWQ2_3:
               return new SWQ2_3(x, y);
           case SWQ2_4:
               return new SWQ2_4(x, y);
           case SWQ3_1:
               return new SWQ3_1(x, y);
           case SWQ3_2:
               return new SWQ3_2(x, y);
           case SWQ3_3:
               return new SWQ3_3(x, y);
           case SWQ3_4:
               return new SWQ3_4(x, y);
           case SWQ4_1:
               return new SWQ4_1(x, y);
           case SWQ4_2:
               return new SWQ4_2(x, y);
           case SWQ4_3:
               return new SWQ4_3(x, y);
           case SWQ4_4:
               return new SWQ4_4(x, y);
           case BTQ1_1:
               return new BTQ1_1(x, y);
           case BSQ1_1:
               return new BSQ1_1(x, y);
           case SIQA3_1:
               return new SIQA3_1(x, y);
           case SIQA3_2:
               return new SIQA3_2(x, y);
           case SIQA4_1:
               return new SIQA4_1(x, y);
           case SIQA4_2:
               return new SIQA4_2(x, y);
       }
        return new EmptyQuad(x, y);
    }
}

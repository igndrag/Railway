package NATrain.quads.BCQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.BlockingControlQuad;
import NATrain.quads.BlockingTrackQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BCQ1_1 extends BlockingControlQuad {

    public BCQ1_1(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.BCQ1_1;
    }

    @Override
    public void paintView() {
        firstRawElement = new Polygon(
                50, 50,
                        70 , 50,
                        70, 45,
                        80, 55,
                        70, 65,
                        70, 60,
                        50, 60,
                        50, 50
                );
        firstRawElement.setFill(BaseQuad.TRACK_DIRECTION_RAW_BACKGROUND_COLOR);
        addToQuadView(firstRawElement);
        Shape firstRawBorder = new Polyline(
                50, 50,
                        70 , 50,
                        70, 45,
                        80, 55,
                        70, 65,
                        70, 60,
                        50, 60,
                        50, 50
        );
        firstRawBorder.setStrokeWidth(2);
        addToQuadView(firstRawBorder);

        secondRawElement = new Polygon(
                40, 50,
                        20 , 50,
                        20, 45,
                        10, 55,
                        20, 65,
                        20, 60,
                        40, 60,
                        40, 50
        );
        secondRawElement.setFill(BaseQuad.TRACK_DIRECTION_RAW_BACKGROUND_COLOR);
        addToQuadView(secondRawElement);

        Shape secondRawBorder = new Polyline(
                40, 50,
                        20 , 50,
                        20, 45,
                        10, 55,
                        20, 65,
                        20, 60,
                        40, 60,
                        40, 50
        );
        secondRawBorder.setStrokeWidth(2);
        addToQuadView(secondRawBorder);

        blockSectionName = new Text("");
        blockSectionName.setTextAlignment(TextAlignment.CENTER);
        blockSectionName.setWrappingWidth(90);
        blockSectionName.setX(0);
        blockSectionName.setY(20);
        addToQuadView(blockSectionName);
    }
}

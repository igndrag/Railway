package NATrain.utils;

import NATrain.quads.AbstractQuad;
import NATrain.quads.QuadType;
import NATrain.quads.Quad;
import NATrain.quads.BaseQuad;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.stream.Stream;


public class QuadPainter {

    public static Shape getQuadBoarder() {
        Polyline border = new Polyline();
        border.getPoints().addAll(
                0.0, 80.0,
                90.0, 80.0,
                90.0, 0.0,
                0.0, 0.0,
                0.0, 80.0);
        return border;
    }

    public static void addGridLines(Group quadView) {
       //vertical lines
        Stream.iterate(0, i -> i + 10).limit(10).forEach(i -> {
            Line line = new Line(i, 0, i, 80);
            line.setStroke(AbstractQuad.GRID_LINE_COLOR);
            line.setStrokeType(StrokeType.OUTSIDE);
            quadView.getChildren().add(line);
        });
        //horizontal lines
        Stream.iterate(0, i -> i + 10).limit(9).forEach(i -> {
            Line line = new Line(0, i, 90, i);
            line.setStroke(AbstractQuad.GRID_LINE_COLOR);
            line.setStrokeType(StrokeType.OUTSIDE);
            quadView.getChildren().add(line);
        });
    }
}


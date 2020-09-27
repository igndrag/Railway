package NATrain.quads;

import NATrain.UI.NavigatorFxController;
import NATrain.trackSideObjects.ControlAction;
import NATrain.utils.QuadPainter;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractQuad implements Quad, Paintable {
    public static final Color DEFAULT_BACKGROUND_COLOR = Color.LIGHTGRAY;
    public static final Color SELECTED_BACKGROUND_COLOR = Color.LIGHTPINK;
    public static final Color CONFIGURED_ELEMENT_COLOR = Color.VIOLET;
    public static final Color OCCUPIED_ELEMENT_COLOR = Color.RED;
    public static final Color FREE_ELEMENT_COLOR = Color.GREEN;
    public static final Color UNDEFINED_ELEMENT_COLOR = Color.BLUE;
    public static final Color INTERLOCKED_ELEMENT_COLOR = Color.YELLOW;
    public static final Color ISOLATOR_ELEMENT_COLOR = Color.BROWN;
    public static Color YELLOW_BLINKER = Color.YELLOW;
    public static Color WHITE_BLINKER = Color.WHITE;

    protected String id;
    protected int x;
    protected int y;
    protected QuadType quadType;

    protected Shape background;
    protected Group quadView;
    protected Group gridLines;

    public AbstractQuad(int x, int y) {
        background = new Rectangle(90, 80);
        background.setFill(DEFAULT_BACKGROUND_COLOR);
        gridLines = new Group();
        QuadPainter.addGridLines(gridLines);
        gridLines.setVisible(NavigatorFxController.showGridLines);
        quadView = new Group();
        quadView.getChildren().add(background);
        quadView.getChildren().add(gridLines);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected void addToQuadView(Shape shape) {
        quadView.getChildren().add(shape);
    }

    @Override
    public void setGridLineVisible(Boolean show) {
        gridLines.setVisible(show);
    }

    @Override
    public void clearView() {
        quadView.getChildren().clear();
    }

    @Override
    public void select() {
        background.setFill(SELECTED_BACKGROUND_COLOR);
    }

    @Override
    public void unselect() {
        background.setFill(DEFAULT_BACKGROUND_COLOR);
    }

    @Override
    public Group getView() {
        return quadView;
    }

    @Override
    public QuadType getType() {
        return quadType;
    }

    public static void blink() {
        if (YELLOW_BLINKER == Color.YELLOW) {
            YELLOW_BLINKER = DEFAULT_BACKGROUND_COLOR;
        } else {
            YELLOW_BLINKER = Color.YELLOW;
        }

        if (WHITE_BLINKER == Color.WHITE) {
            WHITE_BLINKER = DEFAULT_BACKGROUND_COLOR;
        } else {
            WHITE_BLINKER = Color.WHITE;
        }
    }
}

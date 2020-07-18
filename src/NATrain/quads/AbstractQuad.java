package NATrain.quads;

import NATrain.NavigatorFxController;
import NATrain.utils.QuadPainter;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class AbstractQuad implements Quad{
    public static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    public static final Color SELECTED_BACKGROUND_COLOR = Color.PINK;
    public static final Color CONFIGURED_ELEMENT_COLOR = Color.VIOLET;
    public static final Color OCCUPIED_ELEMENT_COLOR = Color.RED;
    public static final Color FREE_ELEMENT_COLOR = Color.GREEN;
    public static final Color UNDEFINED_ELEMENT_COLOR = Color.BLUE;
    public static final Color INTERLOCKED_ELEMENT_COLOR = Color.YELLOW;
    public static Color BLINKER = Color.YELLOW;

    protected String id;
    protected int x;
    protected int y;

    protected Shape background;
    protected Group quadView;

    public AbstractQuad(int x, int y) {
        background = new Rectangle(90, 80);
        background.setFill(DEFAULT_BACKGROUND_COLOR);
        quadView = new Group(background);
        if (NavigatorFxController.constructorMode)
            quadView.getChildren().add(QuadPainter.getQuadBoarder());
        this.x = x;
        this.y = y;
    }

    @Override
    public void select() {
        background.setFill(SELECTED_BACKGROUND_COLOR);
    }

    @Override
    public void unselect() {
        background.setFill(DEFAULT_BACKGROUND_COLOR);
    }



}

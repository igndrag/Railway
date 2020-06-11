package NATrain.quads;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import NATrain.utils.QuadPainter;


public abstract class AbstractQuad implements Quad{

    public static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    public static final Color SELECTED_BACKGROUND_COLOR = Color.PINK;
    public static final Color CONFIGURED_ELEMENT_COLOR = Color.VIOLET;
    public static final Color OCCUPIED_ELEMENT_COLOR = Color.RED;
    public static final Color FREE_ELEMENT_COLOR = Color.GREEN;
    public static final Color UNDEFINED_ELEMENT_COLOR = Color.BLUE;

    protected TrackSection associatedTrack1;
    protected TrackSection associatedTrack2;
    protected Signal associatedSignal;
    protected Switch associatedSwitch;

    private Shape background;
    protected int x;
    protected int y;
    protected Text quadDescription;
    protected Boolean showDescription;
    protected Group quadView;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Group getView() {
        return quadView;
    }

    public Shape getBackground() {
        return background;
    }

    public AbstractQuad (int x, int y, Boolean bordered) {
        background = new Rectangle(90, 80);
        background.setFill(DEFAULT_BACKGROUND_COLOR);
        quadView = new Group(background);
        if (bordered)
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

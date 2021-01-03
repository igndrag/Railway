package NATrain.quads;

import NATrain.UI.NavigatorFxController;
import NATrain.UI.workPlace.Blinker;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.SignalState;
import NATrain.trackSideObjects.TrackSection;
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
    public static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    public static final Color SELECTED_BACKGROUND_COLOR = Color.LIGHTBLUE;
    public static final Color CONFIGURED_ELEMENT_COLOR = Color.VIOLET;
    public static final Color OCCUPIED_ELEMENT_COLOR = Color.RED;
    public static final Color FREE_ELEMENT_COLOR = Color.GREEN;
    public static final Color UNDEFINED_ELEMENT_COLOR = Color.BLUE;
    public static final Color INTERLOCKED_ELEMENT_COLOR = Color.YELLOW;
    public static final Color ISOLATOR_ELEMENT_COLOR = Color.BROWN;
    public static final Color SIGNAL_LAMP_BACKGROUND_COLOR = Color.GRAY;
    public static final Color GRID_LINE_COLOR = Color.LIGHTGRAY;
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
            YELLOW_BLINKER = SIGNAL_LAMP_BACKGROUND_COLOR;
        } else {
            YELLOW_BLINKER = Color.YELLOW;
        }

        if (WHITE_BLINKER == Color.WHITE) {
            WHITE_BLINKER = SIGNAL_LAMP_BACKGROUND_COLOR;
        } else {
            WHITE_BLINKER = Color.WHITE;
        }
    }

    protected static void refreshTrackSectionState(TrackSection associatedTrack, Shape trackSectionElement) {
        if (associatedTrack == TrackSection.EMPTY_TRACK_SECTION) {
            trackSectionElement.setFill(UNDEFINED_ELEMENT_COLOR);
        } else {
            switch (associatedTrack.getVacancyState()) {
                case UNDEFINED:
                    trackSectionElement.setFill(CONFIGURED_ELEMENT_COLOR);
                    break;
                case FREE:
                    if (associatedTrack.isInterlocked())
                        trackSectionElement.setFill(INTERLOCKED_ELEMENT_COLOR);
                    else
                        trackSectionElement.setFill(FREE_ELEMENT_COLOR);
                    break;
                case OCCUPIED:
                    trackSectionElement.setFill(OCCUPIED_ELEMENT_COLOR);
                    break;
            }
        }
    }

    protected class FirstTrackViewUpdater implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            updateFirstTrackView();
        }
    }

    protected class SecondTrackViewUpdater implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            updateSecondTrackView();
        }
    }

    protected class SwitchTrackViewUpdater implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            updateSwitchView();
        }
    }

    protected class SignalQuadViewUpdater implements PropertyChangeListener {
        Quad quad;
        public SignalQuadViewUpdater(Quad quad) {
            this.quad = quad;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            SignalState newSignalState =  (SignalState) evt.getNewValue();
            if (newSignalState.isBlinking()) {
                Blinker.registerQuad(quad);
            } else {
                Blinker.unregisterQuad(quad);
            }
            updateSignalView();
        }
    }
}

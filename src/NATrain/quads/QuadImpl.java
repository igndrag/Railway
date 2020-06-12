package NATrain.quads;

import NATrain.FxController;
import NATrain.controller.SwitchState;
import NATrain.controller.TrackSectionState;
import NATrain.library.QuadType;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import NATrain.utils.QuadPainter;

import java.util.Properties;


public class QuadImpl implements Quad, Cloneable {

    public static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    public static final Color SELECTED_BACKGROUND_COLOR = Color.PINK;
    public static final Color CONFIGURED_ELEMENT_COLOR = Color.VIOLET;
    public static final Color OCCUPIED_ELEMENT_COLOR = Color.RED;
    public static final Color FREE_ELEMENT_COLOR = Color.GREEN;
    public static final Color UNDEFINED_ELEMENT_COLOR = Color.BLUE;
    public static final Color INTERLOCKED_ELEMENT_COLOR = Color.YELLOW;
    public static Color BLINKER = Color.YELLOW;

    protected QuadType quadType;
    protected TrackSection associatedTrackOne;
    protected TrackSection associatedTrackTwo;
    protected Signal associatedSignal;
    protected Switch associatedSwitch;

    private Shape background;

    protected String id;
    protected int x;
    protected int y;

    protected Text description;
    protected Boolean showDescription = true;
    protected Shape trackOneElement;
    protected Shape trackTwoElement;
    protected Shape signalLampOneElement;
    protected Shape signalLampTwoElement;
    protected Shape switchPlusElement;
    protected Shape switchMinusElement;
    protected Group quadView;

    public void setDescription(String description) {
        this.description.setText(description);
    }

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

    public Shape getBackground() {
        return background;
    }

    public void setQuadType(QuadType quadType) {
        this.quadType = quadType;
    }

    public void setAssociatedTrackOne(TrackSection associatedTrackOne) {
        this.associatedTrackOne = associatedTrackOne;
    }

    public void setAssociatedTrackTwo(TrackSection associatedTrackTwo) {
        this.associatedTrackTwo = associatedTrackTwo;
    }

    public void setAssociatedSignal(Signal associatedSignal) {
        this.associatedSignal = associatedSignal;
    }

    public void setAssociatedSwitch(Switch associatedSwitch) {
        this.associatedSwitch = associatedSwitch;
    }

    public void setDescription(Text description) {
        this.description = description;
    }

    public void setShowDescription(Boolean showDescription) {
        this.showDescription = showDescription;
    }

    public void setTrackOneElement(Shape trackOneElement) {
        this.trackOneElement = trackOneElement;
    }

    public void setTrackTwoElement(Shape trackTwoElement) {
        this.trackTwoElement = trackTwoElement;
    }

    public void setSignalLampOneElement(Shape signalLampOneElement) {
        this.signalLampOneElement = signalLampOneElement;
    }

    public void setSignalLampTwoElement(Shape signalLampTwoElement) {
        this.signalLampTwoElement = signalLampTwoElement;
    }

    public void setSwitchPlusElement(Shape switchPlusElement) {
        this.switchPlusElement = switchPlusElement;
    }

    public void setSwitchMinusElement(Shape switchMinusElement) {
        this.switchMinusElement = switchMinusElement;
    }

    public void setQuadView(Group quadView) {
        this.quadView = quadView;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QuadImpl(int x, int y) {
        background = new Rectangle(90, 80);
        background.setFill(DEFAULT_BACKGROUND_COLOR);
        quadView = new Group(background);
        if (FxController.isConstructorMode())
            quadView.getChildren().add(QuadPainter.getQuadBoarder());
        this.x = x;
        this.y = y;
    }

    @Override
    public void refresh() {
        if (FxController.isConstructorMode()) {
            trackConfigured(associatedTrackOne, trackOneElement);
            trackConfigured(associatedTrackTwo, trackTwoElement);
            //TODO make switch and signal view refresh
        } else {
            trackStateRefresh(associatedTrackOne, trackOneElement);
            trackStateRefresh(associatedTrackTwo, trackTwoElement);
            switchStateRefresh(associatedSwitch, switchPlusElement, switchMinusElement);
            //TODO make signal view refresh
        }

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
    public String getId() {
        return id;
    }

    private void trackStateRefresh(TrackSection trackSection, Shape trackElement) {
        if (trackSection != null) {
            if (trackSection.getVacancyState().equals(TrackSectionState.FREE)) {
                if (trackSection.isInterlocked())
                    trackElement.setFill(INTERLOCKED_ELEMENT_COLOR);
                else
                    trackElement.setFill(FREE_ELEMENT_COLOR);
            } else if (trackSection.getVacancyState().equals(TrackSectionState.OCCUPIED))
                trackElement.setFill(OCCUPIED_ELEMENT_COLOR);
            else if (trackSection.getVacancyState().equals(TrackSectionState.UNDEFINED))
                trackElement.setFill(UNDEFINED_ELEMENT_COLOR);
        }
    }

    @Override
    public Group getView() {
        return quadView;
    }

    private void switchStateRefresh(Switch associatedSwitch, Shape switchPlusElement, Shape switchMinusElement) {
        if (associatedSwitch != null) {
            if (associatedSwitch.getSwitchState().equals(SwitchState.PLUS)) {
                switchPlusElement.setFill(trackOneElement.getFill());
                switchMinusElement.setFill(background.getFill());
            } else if (associatedSwitch.getSwitchState().equals(SwitchState.MINUS)) {
                switchPlusElement.setFill(background.getFill());
                switchMinusElement.setFill(trackOneElement.getFill());
            } else if (associatedSwitch.getSwitchState().equals(SwitchState.UNDEFINED)) {
                switchPlusElement.setFill(background.getFill());
                switchMinusElement.setFill(background.getFill());
            }
        }
    }

    @Override
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("Type", quadType.toString());
        if (associatedTrackOne != null)
            properties.setProperty("Track 1", associatedTrackOne.getId());
        if (associatedTrackTwo != null)
            properties.setProperty("Track 2", associatedTrackTwo.getId());
        if (associatedSwitch != null)
            properties.setProperty("Switch", associatedSwitch.getId());
        if (associatedSignal != null)
            properties.setProperty("Signal", associatedSignal.getId());
        return properties;
    }

    private void signalStateRefresh(Signal signal, Shape signalLampOneElement, Shape signalLampTwoElement) {
        //TODO
        //dont forget to realize blink signal!
    }

    private void trackConfigured(TrackSection trackSection, Shape trackElement) {
        if (trackElement != null) {
            if (trackSection != null)
                trackElement.setFill(CONFIGURED_ELEMENT_COLOR);
            else
                trackElement.setFill(UNDEFINED_ELEMENT_COLOR);
        }
    }

    public void showDescription() {
        description.setVisible(true);
    }

    public void hideDescription() {
        description.setVisible(false);
    }


}

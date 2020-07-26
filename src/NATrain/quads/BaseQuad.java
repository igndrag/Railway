package NATrain.quads;

import NATrain.NavigatorFxController;
import NATrain.TrackRedactorFxController;
import NATrain.controller.SwitchState;
import NATrain.controller.TrackSectionState;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;

import java.util.Properties;


public class BaseQuad extends AbstractQuad{

    protected QuadType quadType;
    protected TrackSection firstAssociatedTrack;
    protected TrackSection secondAssociatedTrack;
    protected Signal associatedSignal;
    protected Switch associatedSwitch;

    protected String id;
    protected int x;
    protected int y;

    protected Text description;
    protected Boolean showDescription = true;
    protected Shape firstTrackElement;
    protected Shape secondTrackElement;
    protected Shape firstLampElement;
    protected Shape secondLampElement;
    protected Shape switchPlusElement;
    protected Shape switchMinusElement;
    protected Shape borderElement;
    private Text trackLabel;
    private Text switchLabel;
    private Text signalLabel;

    public Text getSwitchLabel() {
        return switchLabel;
    }

    public void setSwitchLabel(Text switchLabel) {
        this.switchLabel = switchLabel;
    }

    public Text getSignalLabel() {
        return signalLabel;
    }

    public void setSignalLabel(Text signalLabel) {
        this.signalLabel = signalLabel;
    }

    public Text getTrackLabel() {
        return trackLabel;
    }

    public void setTrackLabel(Text trackLabel) {
        this.trackLabel = trackLabel;
    }

    public BaseQuad(int x, int y) {
        super(x, y);
    }

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

    public void setFirstAssociatedTrack(TrackSection firstAssociatedTrack) {
        this.firstAssociatedTrack = firstAssociatedTrack;
    }

    public void setSecondAssociatedTrack(TrackSection secondAssociatedTrack) {
        this.secondAssociatedTrack = secondAssociatedTrack;
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

    public void setFirstTrackElement(Shape firstTrackElement) {
        this.firstTrackElement = firstTrackElement;
    }

    public void setTrackTwoElement(Shape trackTwoElement) {
        this.secondTrackElement = trackTwoElement;
    }

    public void setFirstLampElement(Shape firstLampElement) {
        this.firstLampElement = firstLampElement;
    }

    public void setSecondLampElement(Shape secondLampElement) {
        this.secondLampElement = secondLampElement;
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

    public TrackSection getFirstAssociatedTrack() {
        return firstAssociatedTrack;
    }

    public TrackSection getSecondAssociatedTrack() {
        return secondAssociatedTrack;
    }

    public Signal getAssociatedSignal() {
        return associatedSignal;
    }

    public Switch getAssociatedSwitch() {
        return associatedSwitch;
    }

    public Boolean getShowDescription() {
        return showDescription;
    }

    public Shape getBorderElement() {
        return borderElement;
    }

    public void setBorderElement(Shape borderElement) {
        this.borderElement = borderElement;
    }

    public Shape getFirstTrackElement() {
        return firstTrackElement;
    }

    public Shape getSecondTrackElement() {
        return secondTrackElement;
    }

    public void setSecondTrackElement(Shape secondTrackElement) {
        this.secondTrackElement = secondTrackElement;
    }

    public Shape getFirstLampElement() {
        return firstLampElement;
    }

    public Shape getSecondLampElement() {
        return secondLampElement;
    }

    public Shape getSwitchPlusElement() {
        return switchPlusElement;
    }

    public Shape getSwitchMinusElement() {
        return switchMinusElement;
    }

    @Override
    public void refresh() {
        if (NavigatorFxController.constructorMode) {
            trackConfigured(firstAssociatedTrack, firstTrackElement);
            trackConfigured(secondAssociatedTrack, secondTrackElement);
            signalConfigured(associatedSignal, firstLampElement, secondLampElement);
            //TODO make switch and signal view refresh
        } else {
            trackStateRefresh(firstAssociatedTrack, firstTrackElement);
            trackStateRefresh(secondAssociatedTrack, secondTrackElement);
            switchStateRefresh(associatedSwitch, switchPlusElement, switchMinusElement);
            //TODO make signal view refresh
        }

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
                switchPlusElement.setFill(firstTrackElement.getFill());
                switchMinusElement.setFill(background.getFill());
            } else if (associatedSwitch.getSwitchState().equals(SwitchState.MINUS)) {
                switchPlusElement.setFill(background.getFill());
                switchMinusElement.setFill(firstTrackElement.getFill());
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
        if (firstAssociatedTrack != null)
            properties.setProperty("Track 1", firstAssociatedTrack.getId());
        if (secondAssociatedTrack != null)
            properties.setProperty("Track 2", secondAssociatedTrack.getId());
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

    private void signalConfigured(Signal associatedSignal, Shape firstLampElement, Shape secondLampElement) {
        if (associatedSignal != null) {
            if (firstLampElement != null)
                firstLampElement.setFill(CONFIGURED_ELEMENT_COLOR);
            if (secondLampElement != null)
                secondLampElement.setFill(CONFIGURED_ELEMENT_COLOR);
        } else {
            if (firstLampElement != null)
                firstLampElement.setFill(UNDEFINED_ELEMENT_COLOR);
            if (secondLampElement != null)
                secondLampElement.setFill(UNDEFINED_ELEMENT_COLOR);
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public void showDescription() {
        description.setVisible(true);
    }

    public void hideDescription() {
        description.setVisible(false);
    }


}

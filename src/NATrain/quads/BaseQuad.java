package NATrain.quads;

import NATrain.quads.configurableInterfaces.Configurable;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import javafx.scene.text.Text;


public abstract class BaseQuad extends AbstractQuad implements Configurable {


    protected TrackSection firstAssociatedTrack = TrackSection.EMPTY_TRACK_SECTION;
    protected TrackSection secondAssociatedTrack = TrackSection.EMPTY_TRACK_SECTION;
    protected Switch associatedSwitch = Switch.EMPTY_SWITCH;
    protected Signal associatedSignal = Signal.EMPTY_SIGNAL;



    protected Text descriptionLabel = new Text("");

    public BaseQuad(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void showDescription(boolean show) {
        descriptionLabel.setVisible(show);
    }

    @Override
    public boolean isDescriptionShown() {
        return descriptionLabel.isVisible();
    }
}

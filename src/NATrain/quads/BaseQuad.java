package NATrain.quads;

import NATrain.NavigatorFxController;
import NATrain.controller.SwitchState;
import NATrain.controller.TrackSectionState;
import NATrain.quads.configurableInterfaces.Configurable;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import javafx.util.Pair;

import java.util.Properties;


public abstract class BaseQuad extends AbstractQuad implements Configurable {

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

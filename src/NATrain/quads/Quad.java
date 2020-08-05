package NATrain.quads;

import NATrain.trackSideObjects.ControlAction;
import javafx.scene.Group;

import java.util.List;
import java.util.Properties;

public interface Quad {
    boolean isEmpty();
    void refresh();
    void select();
    void unselect();
    Group getView();
    List<ControlAction> getAvailableActions();

}

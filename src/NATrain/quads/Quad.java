package NATrain.quads;

import NATrain.trackSideObjects.ControlAction;
import javafx.scene.Group;

import java.util.List;

public interface Quad {
    QuadType getType();
    boolean isEmpty();
    void refresh();
    void select();
    void unselect();
    Group getView();
    void setGridLineVisible(Boolean show);
    void activateListeners(); // that's the price of bad design((
    void updateFirstTrackView();
    void updateSecondTrackView();
    void updateSignalView();
    void updateSwitchView();

    List<ControlAction> getAvailableActions();

}

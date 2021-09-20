package NATrain.quads;

import NATrain.trackSideObjects.ControlAction;
import javafx.scene.Group;

import java.util.List;

public interface Quad { // that's the price of bad design((
    QuadType getType();
    boolean isEmpty();
    void refresh();
    void select();
    void unselect();
    Group getView();
    void setGridLineVisible(Boolean show);
    void activateListeners();
    void deactivateListeners();
    void updateFirstTrackView();
    void updateSecondTrackView();
    void updateSignalView();
    void updateSwitchView();

    List<ControlAction> getAvailableActions();

}

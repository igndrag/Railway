package NATrain.quads;

import NATrain.trackSideObjects.ControlAction;
import javafx.scene.Group;

import java.util.List;

public interface Quad { // that's the price of bad design((
    GlobalQuadType getGlobalQuadType();
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
    Object getCustomObject();
    void setCustomObject(Object object);

    List<ControlAction> getAvailableActions();

}

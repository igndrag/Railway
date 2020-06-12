package NATrain.quads;

import javafx.scene.Group;

public interface Quad {
    void refresh();
    void select();
    void unselect();
    String getId();
    Group getView();
}

package NATrain.quads;

import javafx.scene.Group;

import java.util.Properties;

public interface Quad {
    boolean isEmpty();
    void refresh();
    void select();
    void unselect();
    Group getView();
}

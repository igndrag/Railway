package NATrain.UI.tracksideObjectRedactor;

import NATrain.trackSideObjects.TracksideObject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;

public interface TSORedactor {
    void saveAndClose(ActionEvent actionEvent);
    void init (TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList);
}

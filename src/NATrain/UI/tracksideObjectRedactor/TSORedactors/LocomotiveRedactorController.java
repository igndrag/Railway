package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.UIUtils;
import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.model.Model;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.locomotives.Locomotive;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.utils.UtilFunctions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LocomotiveRedactorController extends TracksideObjectRedactorController {

    private Locomotive locomotive;

    @Override
    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        this.locomotive = (Locomotive) tracksideObject;
        textField.setText(locomotive.getId());
        this.tableView = tableView;
        this.observableList = observableList;
        }

    @FXML
    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        locomotive.setId(textField.getText());
        if (!isNameValid(Model.getLocomotives(), Locomotive.INITIAL_LOCOMOTIVE_NAME)) {
            return;
        }
        updateModelAndClose(Model.getLocomotives(), locomotive);
    }
}

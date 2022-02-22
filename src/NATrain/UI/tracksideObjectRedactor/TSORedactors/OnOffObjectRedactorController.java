package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.model.Model;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.customObjects.Gate;
import NATrain.trackSideObjects.customObjects.OnOffObject;
import NATrain.trackSideObjects.customObjects.Servo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;

public class OnOffObjectRedactorController extends TracksideObjectRedactorController {

    @FXML
    private Button saveButton;

    protected OnOffObject onOffObject;

    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        this.onOffObject = (OnOffObject) tracksideObject;
        textField.setText(onOffObject.getId());
        this.tableView = tableView;
        this.observableList = observableList;
        this.initialName = onOffObject.getId();


        if (!onOffObject.getId().equals(OnOffObject.INITIAL_ON_OFF_OBJECT_NAME)) {
            textField.setDisable(true);
        } else {
            textField.setOnMouseClicked(event -> {
                textField.selectAll();
            });
        }
        saveButton.setOnAction(this::saveAndClose);
    }

    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        if (!isNameValid(Model.getGates(), OnOffObject.INITIAL_ON_OFF_OBJECT_NAME)) {
            return;
        }
        onOffObject.setId(textField.getText());
        updateModelAndClose(Model.getOnOffObjects(), onOffObject);
    }
}


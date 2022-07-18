package NATrain.UI.mosaicRedactor;

import NATrain.connectionService.MQTTConnectionService;
import NATrain.model.Model;
import NATrain.quads.custom.OnOffObjectQuad;
import NATrain.quads.custom.ServoQuad;
import NATrain.trackSideObjects.customObjects.OnOffObject;
import NATrain.trackSideObjects.customObjects.Servo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class OnOffQuadConfiguratorController {

    @FXML
    private ChoiceBox<OnOffObject> onOffChoiceBox;
    @FXML
    private Button saveButton;

    OnOffObjectQuad quad;

    public void init(OnOffObject onOffObject, OnOffObjectQuad quad) {
        this.quad = quad;

        ObservableList<OnOffObject> objects = FXCollections.observableArrayList(Model.getOnOffObjects().values());
        objects.add(OnOffObject.EMPTY_ON_OFF_OBJECT);

        if (onOffObject != null) {
            onOffChoiceBox.setValue(onOffObject);
        } else {
            onOffChoiceBox.setValue(OnOffObject.EMPTY_ON_OFF_OBJECT);
        }

        onOffChoiceBox.setItems(objects);
        saveButton.setOnAction(event -> saveAndClose());
    }

    private void saveAndClose() {
        OnOffObject newObject = onOffChoiceBox.getValue();
        if (onOffChoiceBox.getValue() != OnOffObject.EMPTY_ON_OFF_OBJECT) {
            quad.setOnOffObject(newObject);
            quad.getDescriptionLabel().setText(newObject.getId());
        } else {
            quad.setCustomObject(null);
            quad.getDescriptionLabel().setText("NONE");
        }
        quad.refresh();
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}

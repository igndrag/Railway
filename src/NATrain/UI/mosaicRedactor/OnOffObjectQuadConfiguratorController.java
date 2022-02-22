package NATrain.UI.mosaicRedactor;

import NATrain.model.Model;
import NATrain.quads.custom.GateQuad;
import NATrain.quads.custom.LampQuad;
import NATrain.quads.custom.OnOffObjectQuad;
import NATrain.trackSideObjects.customObjects.Gate;
import NATrain.trackSideObjects.customObjects.OnOffObject;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class OnOffObjectQuadConfiguratorController {

    @FXML
    private ChoiceBox<OnOffObject> onOffObjectChoiceBox;
    @FXML
    private Button saveButton;

    OnOffObjectQuad quad;
        /*

    public void init(OnOffObject gate, OnOffObjectQuad quad) {
        this.quad = quad;

        firstTrackSectionChoiceBox.getItems().addAll(Model.getTrackSections().values());
        firstTrackSectionChoiceBox.getItems().addAll(Model.getStationTracks().values());
        firstTrackSectionChoiceBox.getItems().add(TrackSection.EMPTY_TRACK_SECTION);

        if (quad.getFirstAssociatedTrack() == null || quad.getFirstAssociatedTrack() == TrackSection.EMPTY_TRACK_SECTION) {
           firstTrackSectionChoiceBox.setValue(TrackSection.EMPTY_TRACK_SECTION);
        } else {
            firstTrackSectionChoiceBox.setValue(quad.getFirstAssociatedTrack());
        }

        ObservableList<Gate> gates = FXCollections.observableArrayList(Model.getGates().values());
        gates.add(Gate.EMPTY_GATE);
        onOffObjectChoiceBox.setItems(gates);

        if (quad.getGate() != null) {
            onOffObjectChoiceBox.setValue(quad.getGate());
        } else {
            onOffObjectChoiceBox.setValue(Gate.EMPTY_GATE);
        }
    }

    @FXML
    private void saveAndClose() {
        Gate gate = onOffObjectChoiceBox.getValue();
        if (onOffObjectChoiceBox.getValue() != Gate.EMPTY_GATE) {
            quad.setGate(gate);
        } else {
            quad.setGate(null);
        }

        if (firstTrackSectionChoiceBox.getValue() == TrackSection.EMPTY_TRACK_SECTION) {
            quad.setFirstAssociatedTrack(null);
        } else {
            quad.setFirstAssociatedTrack(firstTrackSectionChoiceBox.getValue());
        }
        quad.refresh();
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

         */
}

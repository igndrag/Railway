package NATrain.UI.mosaicRedactor;

import NATrain.model.Model;
import NATrain.quads.custom.GateQuad;
import NATrain.trackSideObjects.customObjects.Gate;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class GateQuadConfiguratorController {
    @FXML
    public ChoiceBox<TrackSection> firstTrackSectionChoiceBox;
    @FXML
    private ChoiceBox<Gate> gateChoiceBox;
    @FXML
    private Button saveButton;

    GateQuad quad;

    public void init(Gate gate, GateQuad quad) {
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
        gateChoiceBox.setItems(gates);

        if (quad.getGate() != null) {
            gateChoiceBox.setValue(quad.getGate());
        } else {
            gateChoiceBox.setValue(Gate.EMPTY_GATE);
        }
    }

    @FXML
    private void saveAndClose() {
        Gate gate = gateChoiceBox.getValue();
        if (gateChoiceBox.getValue() != Gate.EMPTY_GATE) {
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
}

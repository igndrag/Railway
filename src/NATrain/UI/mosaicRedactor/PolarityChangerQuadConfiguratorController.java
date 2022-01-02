package NATrain.UI.mosaicRedactor;

import NATrain.model.Model;
import NATrain.quads.custom.PolarityChangerQuad;
import NATrain.trackSideObjects.customObjects.PolarityChanger;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class PolarityChangerQuadConfiguratorController {
    @FXML
    public ChoiceBox<TrackSection> firstTrackSectionChoiceBox;
    @FXML
    private ChoiceBox<PolarityChanger> polarityChangerChoiceBox;
    @FXML
    private Button saveButton;

    PolarityChangerQuad quad;

    public void init(PolarityChanger polarityChanger, PolarityChangerQuad quad) {
        this.quad = quad;

        firstTrackSectionChoiceBox.getItems().addAll(Model.getTrackSections().values());
        firstTrackSectionChoiceBox.getItems().addAll(Model.getStationTracks().values());
        firstTrackSectionChoiceBox.getItems().add(TrackSection.EMPTY_TRACK_SECTION);

        if (quad.getFirstAssociatedTrack() == null || quad.getFirstAssociatedTrack() == TrackSection.EMPTY_TRACK_SECTION) {
           firstTrackSectionChoiceBox.setValue(TrackSection.EMPTY_TRACK_SECTION);
        } else {
            firstTrackSectionChoiceBox.setValue(quad.getFirstAssociatedTrack());
        }

        ObservableList<PolarityChanger> polarityChangers = FXCollections.observableArrayList(Model.getPolarityChangers().values());
        polarityChangers.add(PolarityChanger.EMPTY_POLARITY_CHANGER);
        polarityChangerChoiceBox.setItems(polarityChangers);

        if (quad.getPolarityChanger() != null) {
            polarityChangerChoiceBox.setValue(quad.getPolarityChanger());
        } else {
            polarityChangerChoiceBox.setValue(PolarityChanger.EMPTY_POLARITY_CHANGER);
        }
    }

    @FXML
    private void saveAndClose() {
        PolarityChanger newPolarityChanger = polarityChangerChoiceBox.getValue();
        if (polarityChangerChoiceBox.getValue() != PolarityChanger.EMPTY_POLARITY_CHANGER) {
            quad.setPolarityChanger(newPolarityChanger);
        } else {
            quad.setPolarityChanger(null);
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

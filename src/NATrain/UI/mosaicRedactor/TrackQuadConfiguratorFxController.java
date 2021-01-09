package NATrain.UI.mosaicRedactor;

import NATrain.model.Model;
import NATrain.quads.TrackBaseQuad;
import NATrain.quads.configurableInterfaces.*;
import NATrain.routes.Track;
import NATrain.routes.TrackBlockSection;
import NATrain.utils.QuadFactory;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TrackQuadConfiguratorFxController {

    @FXML
    private ChoiceBox<TrackBlockSection> firstBlockSectionChoiceBox;
    @FXML
    private ChoiceBox<TrackBlockSection> secondBlockSectionChoiceBox;
    @FXML
    private CheckBox showBlockSectionNameCheckBox;
    @FXML
    private CheckBox reverseCheckBox;
    @FXML
    private ChoiceBox<Track> trackChoiceBox;

    @FXML
    private Pane quadViewPane;
    private Group quadView;

    private TrackBaseQuad quadForConfig;
    private Stage stage;
    private Pane parent;
    private EventHandler<? super MouseEvent> eventHandler;

    public void initialize(int x, int y) {
        // *** stage init
        quadForConfig = (TrackBaseQuad)Model.getMainGrid()[y][x];
        quadView = quadForConfig.getView();
        parent = (Pane) quadView.getParent();
        quadForConfig.unselect();
        parent.getChildren().clear();
        parent.getChildren().add(QuadFactory.redactorModeQuadView);
        quadViewPane.getChildren().add(quadView);
        stage = (Stage) trackChoiceBox.getScene().getWindow();
        stage.setOnCloseRequest(event -> saveAndClose());
        eventHandler = quadView.getOnMouseClicked();
        quadView.setOnMouseClicked(null);

        trackChoiceBox.setItems(FXCollections.observableArrayList(Model.getTracks()));
        trackChoiceBox.getItems().add(Track.EMPTY_TRACK);
        trackChoiceBox.getSelectionModel().select(quadForConfig.getTrack());
        if (trackChoiceBox.getValue() != Track.EMPTY_TRACK) {
            firstBlockSectionChoiceBox.setItems(FXCollections.observableArrayList(trackChoiceBox.getValue().getBlockSections()));
            secondBlockSectionChoiceBox.setItems(FXCollections.observableArrayList(trackChoiceBox.getValue().getBlockSections()));
        } else {
            firstBlockSectionChoiceBox.setDisable(true);
            secondBlockSectionChoiceBox.setDisable(true);
        }
        trackChoiceBox.setOnAction(event -> {
            quadForConfig.setTrack(trackChoiceBox.getValue());
            if (trackChoiceBox.getValue() != Track.EMPTY_TRACK) {
                firstBlockSectionChoiceBox.setItems(FXCollections.observableArrayList(trackChoiceBox.getValue().getBlockSections()));
                secondBlockSectionChoiceBox.setItems(FXCollections.observableArrayList(trackChoiceBox.getValue().getBlockSections()));
                firstBlockSectionChoiceBox.setDisable(false);
                secondBlockSectionChoiceBox.setDisable(false);
            } else {
                firstBlockSectionChoiceBox.setDisable(true);
                secondBlockSectionChoiceBox.setDisable(true);
            }
            });

        firstBlockSectionChoiceBox.getSelectionModel().select(quadForConfig.getFirstBlockSection());

        firstBlockSectionChoiceBox.setOnMouseClicked(event -> {
            if (firstBlockSectionChoiceBox.getValue() != null) {
                quadForConfig.setFirstBlockSection(firstBlockSectionChoiceBox.getValue());
                if (quadForConfig instanceof BlockSignalConfigurable) {
                    selectFirstSignal();
                }
                quadForConfig.refresh();
            }
        });

        if (quadForConfig instanceof BlockSignalConfigurable) {
            showBlockSectionNameCheckBox.setDisable(true);
            secondBlockSectionChoiceBox.getSelectionModel().select(quadForConfig.getSecondBlockSection());
            secondBlockSectionChoiceBox.setOnMouseClicked((event -> {
                if (secondBlockSectionChoiceBox.getValue() != null) {
                quadForConfig.setSecondBlockSection(secondBlockSectionChoiceBox.getValue());
                selectSecondSignal();
                quadForConfig.refresh();
                }
            }));
            reverseCheckBox.setSelected(quadForConfig.getReversedSignalView());
            reverseCheckBox.setOnAction(event -> {
                if (reverseCheckBox.isSelected()) {
                    quadForConfig.setReversedSignalView(true);
                } else {
                    quadForConfig.setReversedSignalView(false);
                }
                if (!firstBlockSectionChoiceBox.getSelectionModel().isEmpty()) {
                    selectFirstSignal();
                }
                if (!secondBlockSectionChoiceBox.getSelectionModel().isEmpty()) {
                    selectSecondSignal();
                }
                quadForConfig.refresh();
            });

        } else {
            showBlockSectionNameCheckBox.setSelected(quadForConfig.getBlockSectionName().isVisible());
            showBlockSectionNameCheckBox.setOnMouseClicked(event ->
                    quadForConfig.showBlockSectionName(showBlockSectionNameCheckBox.isSelected()));
            secondBlockSectionChoiceBox.setDisable(true);
            reverseCheckBox.setDisable(true);
        }
    }

    private void selectFirstSignal() {
        if (reverseCheckBox.isSelected()) {
            quadForConfig.setFirstSignal((firstBlockSectionChoiceBox.getValue().getNormalDirectionSignal()));
        } else {
            quadForConfig.setFirstSignal(firstBlockSectionChoiceBox.getValue().getReversedDirectionSignal());
        }
    }

    private void selectSecondSignal() {
        if (reverseCheckBox.isSelected()) {
            quadForConfig.setSecondSignal((secondBlockSectionChoiceBox.getValue().getReversedDirectionSignal()));
        } else {
            quadForConfig.setSecondSignal(secondBlockSectionChoiceBox.getValue().getNormalDirectionSignal());
        }
    }

    @FXML
    private void saveAndClose() {
        parent.getChildren().clear();
        parent.getChildren().add(quadForConfig.getView());
        quadForConfig.getView().setOnMouseClicked(eventHandler);
        stage.close();
    }
}


package NATrain.UI.mosaicRedactor;

import NATrain.model.Model;
import NATrain.quads.BlockingBaseQuad;
import NATrain.quads.BlockingControlQuad;
import NATrain.quads.configurableInterfaces.*;
import NATrain.routes.Trackline;
import NATrain.routes.TracklineBlockSection;
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
    private ChoiceBox<TracklineBlockSection> firstBlockSectionChoiceBox;
    @FXML
    private ChoiceBox<TracklineBlockSection> secondBlockSectionChoiceBox;
    @FXML
    private CheckBox showBlockSectionNameCheckBox;
    @FXML
    private CheckBox reverseCheckBox;
    @FXML
    private ChoiceBox<Trackline> trackChoiceBox;

    @FXML
    private Pane quadViewPane;
    private Group quadView;

    private BlockingBaseQuad quadForConfig;
    private Stage stage;
    private Pane parent;
    private EventHandler<? super MouseEvent> eventHandler;

    public void initialize(int x, int y) {
        // *** stage init
        quadForConfig = (BlockingBaseQuad) Model.getMainGrid()[y][x];
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

        trackChoiceBox.setItems(FXCollections.observableArrayList(Model.getTracklines()));
        trackChoiceBox.getSelectionModel().select(quadForConfig.getTrack());

        if (quadForConfig.getTrack() == Trackline.EMPTY_TRACKLINE) {
            firstBlockSectionChoiceBox.setDisable(true);
            secondBlockSectionChoiceBox.setDisable(true);
        }


        if (quadForConfig instanceof BlockingControlQuad) {
            firstBlockSectionChoiceBox.setDisable(true);
            secondBlockSectionChoiceBox.setDisable(true);
            trackChoiceBox.setOnAction(event -> {
                if (trackChoiceBox.getValue() != Trackline.EMPTY_TRACKLINE) {
                    quadForConfig.setTrack(trackChoiceBox.getValue());
                } else {
                    quadForConfig.setTrack(Trackline.EMPTY_TRACKLINE);
                }
                quadForConfig.refresh();
            });
        }

        if (quadForConfig instanceof BlockSectionConfigurable) {
            secondBlockSectionChoiceBox.setDisable(true);
            if (trackChoiceBox.getValue() != Trackline.EMPTY_TRACKLINE) {
                quadForConfig.setTrack(trackChoiceBox.getValue());
                firstBlockSectionChoiceBox.setItems(FXCollections.observableArrayList(trackChoiceBox.getValue().getBlockSections()));
                firstBlockSectionChoiceBox.setDisable(false);
                if (quadForConfig instanceof BlockSignalConfigurable) {
                    secondBlockSectionChoiceBox.setItems(FXCollections.observableArrayList(trackChoiceBox.getValue().getBlockSections()));
                    secondBlockSectionChoiceBox.setDisable(false);
                }
            } else {
                quadForConfig.setTrack(Trackline.EMPTY_TRACKLINE);
                firstBlockSectionChoiceBox.setDisable(true);
                secondBlockSectionChoiceBox.setDisable(true);
            }
            trackChoiceBox.setOnAction(event -> {
                quadForConfig.setTrack(trackChoiceBox.getValue());
                if (trackChoiceBox.getValue() != Trackline.EMPTY_TRACKLINE) {
                    firstBlockSectionChoiceBox.setItems(FXCollections.observableArrayList(trackChoiceBox.getValue().getBlockSections()));
                    firstBlockSectionChoiceBox.setDisable(false);
                    if (quadForConfig instanceof BlockSignalConfigurable) {
                        secondBlockSectionChoiceBox.setItems(FXCollections.observableArrayList(trackChoiceBox.getValue().getBlockSections()));
                        secondBlockSectionChoiceBox.setDisable(false);
                    }
                } else {
                    firstBlockSectionChoiceBox.getSelectionModel().clearSelection();
                    firstBlockSectionChoiceBox.setDisable(true);
                    secondBlockSectionChoiceBox.getSelectionModel().clearSelection();
                    secondBlockSectionChoiceBox.setDisable(true);
                }
            });
        }

        firstBlockSectionChoiceBox.getSelectionModel().select(quadForConfig.getFirstBlockSection());

        firstBlockSectionChoiceBox.setOnAction(event -> {
            if (!firstBlockSectionChoiceBox.getSelectionModel().isEmpty()) {
                quadForConfig.setFirstBlockSection(firstBlockSectionChoiceBox.getValue());
                if (quadForConfig instanceof BlockSignalConfigurable) {
                    selectFirstSignal();
                }
                quadForConfig.refresh();
            } else {
                quadForConfig.setFirstBlockSection(TracklineBlockSection.EMPTY_BLOCK_SECTION);
            }
        });

        if (quadForConfig instanceof BlockSignalConfigurable) {
            showBlockSectionNameCheckBox.setDisable(true);

            secondBlockSectionChoiceBox.setItems(FXCollections.observableArrayList(trackChoiceBox.getValue().getBlockSections()));
            secondBlockSectionChoiceBox.getSelectionModel().select(quadForConfig.getSecondBlockSection());

            secondBlockSectionChoiceBox.setOnAction((event -> {
                if (!secondBlockSectionChoiceBox.getSelectionModel().isEmpty()) {
                    quadForConfig.setSecondBlockSection(secondBlockSectionChoiceBox.getValue());
                    selectSecondSignal();
                    quadForConfig.refresh();
                } else {
                    quadForConfig.setSecondBlockSection(TracklineBlockSection.EMPTY_BLOCK_SECTION);
                }
            }));
            reverseCheckBox.setSelected(quadForConfig.getReversedSignalView());
            reverseCheckBox.setOnAction(event -> {
                quadForConfig.setReversedSignalView(reverseCheckBox.isSelected());
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


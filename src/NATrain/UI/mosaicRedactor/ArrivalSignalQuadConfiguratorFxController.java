package NATrain.UI.mosaicRedactor;

import NATrain.model.Model;
import NATrain.quads.ArrivalSignalQuad;
import NATrain.routes.Trackline;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.utils.QuadFactory;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ArrivalSignalQuadConfiguratorFxController {

    @FXML
    private ChoiceBox<TrackSection> trackSectionChoiceBox;
    @FXML
    private ChoiceBox<Signal> signalChoiceBox;
    @FXML
    private ChoiceBox<TrackSection> blockSectionChoiceBox;
    @FXML
    private ChoiceBox<Trackline> trackChoiceBox;

    @FXML
    private Pane quadViewPane;
    private Group quadView;

    private ArrivalSignalQuad quadForConfig;
    private Stage stage;
    private Pane parent;
    private EventHandler<? super MouseEvent> eventHandler;

    public void initialize(int x, int y) {
        // *** stage init
        quadForConfig = (ArrivalSignalQuad)Model.getMainGrid()[y][x];
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
        trackChoiceBox.getItems().add(Trackline.EMPTY_TRACKLINE);
        trackChoiceBox.getSelectionModel().select(quadForConfig.getTrack());
        blockSectionChoiceBox.setItems(FXCollections.observableArrayList(trackChoiceBox.getValue().getBlockSections()));
        trackChoiceBox.setOnAction(event -> {
            quadForConfig.setTrack(trackChoiceBox.getValue());
            blockSectionChoiceBox.setItems(FXCollections.observableArrayList(trackChoiceBox.getValue().getBlockSections()));
        });

        blockSectionChoiceBox.getSelectionModel().select(quadForConfig.getSecondAssociatedTrack());

        blockSectionChoiceBox.setOnAction(event -> {
            quadForConfig.setSecondAssociatedTrack(blockSectionChoiceBox.getValue());
            quadForConfig.refresh();
        });

        trackSectionChoiceBox.setItems(FXCollections.observableArrayList(Model.getTrackSections().values()));
        trackSectionChoiceBox.getItems().add(TrackSection.EMPTY_TRACK_SECTION);
        trackSectionChoiceBox.getSelectionModel().select(quadForConfig.getFirstAssociatedTrack());

        trackSectionChoiceBox.setOnAction(event -> {
            quadForConfig.setFirstAssociatedTrack(trackSectionChoiceBox.getValue());
            quadForConfig.refresh();
        });

        signalChoiceBox.setItems(FXCollections.observableArrayList(Model.getSignals().values()));
        signalChoiceBox.getItems().add(Signal.EMPTY_SIGNAL);
        signalChoiceBox.getSelectionModel().select(quadForConfig.getAssociatedSignal());
        signalChoiceBox.setOnAction(event -> {
            quadForConfig.setAssociatedSignal(signalChoiceBox.getValue());
            quadForConfig.refresh();
        });
    }

    @FXML
    private void saveAndClose() {
        parent.getChildren().clear();
        parent.getChildren().add(quadForConfig.getView());
        quadForConfig.getView().setOnMouseClicked(eventHandler);
        stage.close();
    }
}


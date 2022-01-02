package NATrain.UI.mosaicRedactor;

import NATrain.model.Model;
import NATrain.quads.Quad;
import NATrain.quads.configurableInterfaces.*;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.utils.QuadFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class QuadConfiguratorFxController {

    @FXML
    private Button saveButton;

    @FXML
    private CheckBox showDescriptionCheckBox;

    @FXML
    private CheckBox trackSectionBorderCheckBox;

    @FXML
    private ChoiceBox<TrackSection> firstTrackSectionChoiceBox;

    @FXML
    private ChoiceBox<TrackSection> secondTrackSectionChoiceBox;

    @FXML
    private ChoiceBox<Signal> signalChoiceBox;

    @FXML
    private ChoiceBox<Switch> switchChoiceBox;

    @FXML
    private Pane quadViewPane;
    private Group quadView;

    private Quad quadForConfig;
    private Stage stage;
    private Pane parent;
    private EventHandler<? super MouseEvent> eventHandler;

    public void initialize(int x, int y) {

        // *** stage init
        quadForConfig = Model.getMainGrid()[y][x];
        quadView = quadForConfig.getView();
        parent = (Pane) quadView.getParent();
        quadForConfig.unselect();
        parent.getChildren().clear();
        parent.getChildren().add(QuadFactory.redactorModeQuadView);
        quadViewPane.getChildren().add(quadView);
        stage = (Stage) saveButton.getScene().getWindow();
        stage.setOnCloseRequest(event -> saveAndClose());
        eventHandler = quadView.getOnMouseClicked();
        quadView.setOnMouseClicked(null);

        // *** first trackline choice box init
        if (quadForConfig instanceof FirstTrackConfigurable) {
            FirstTrackConfigurable firstTrackConfigurable = (FirstTrackConfigurable) quadForConfig;
            firstTrackSectionChoiceBox.setValue(firstTrackConfigurable.getFirstAssociatedTrack());
            firstTrackSectionChoiceBox.getItems().addAll(Model.getTrackSections().values());
            firstTrackSectionChoiceBox.getItems().addAll(Model.getStationTracks().values());
            firstTrackSectionChoiceBox.getItems().add(TrackSection.EMPTY_TRACK_SECTION);

            firstTrackSectionChoiceBox.setOnAction(event -> {
               firstTrackConfigurable.setFirstAssociatedTrack(firstTrackSectionChoiceBox.getValue());
               quadForConfig.refresh();
            });
        } else {
            firstTrackSectionChoiceBox.setDisable(true);
        }

        // *** second trackline choice box init
        if (quadForConfig instanceof SecondTrackConfigurable) {
            SecondTrackConfigurable secondTrackConfigurable = (SecondTrackConfigurable) quadForConfig;
            secondTrackSectionChoiceBox.setValue(secondTrackConfigurable.getSecondAssociatedTrack());
            secondTrackSectionChoiceBox.getItems().addAll(Model.getTrackSections().values());
            secondTrackSectionChoiceBox.getItems().addAll(Model.getStationTracks().values());
            secondTrackSectionChoiceBox.getItems().add(TrackSection.EMPTY_TRACK_SECTION);

            secondTrackSectionChoiceBox.setOnAction(event -> {
                secondTrackConfigurable.setSecondAssociatedTrack(secondTrackSectionChoiceBox.getValue());
                quadForConfig.refresh();
            });
        } else
            secondTrackSectionChoiceBox.setDisable(true);

        // *** signal choice box init
        if (quadForConfig instanceof SignalConfigurable) {
            SignalConfigurable signalConfigurable = (SignalConfigurable) quadForConfig;
            signalChoiceBox.setValue(signalConfigurable.getAssociatedSignal());
            signalChoiceBox.getItems().addAll(Model.getSignals().values());
            signalChoiceBox.getItems().add(Signal.EMPTY_SIGNAL);

            signalChoiceBox.setOnAction(event -> {
                signalConfigurable.setAssociatedSignal(signalChoiceBox.getValue());
                quadForConfig.refresh();
            });
        } else
            signalChoiceBox.setDisable(true);

        // *** switch choice box init
        if (quadForConfig instanceof SwitchConfigurable) {
            SwitchConfigurable switchConfigurable = (SwitchConfigurable) quadForConfig;
            switchChoiceBox.getItems().addAll(Model.getSwitches().values());
            switchChoiceBox.getItems().add(Switch.EMPTY_SWITCH);

            switchChoiceBox.setOnAction(event -> {
                switchConfigurable.setAssociatedSwitch(switchChoiceBox.getValue());
                quadForConfig.refresh();
            });
        } else switchChoiceBox.setDisable(true);

        ///*** show description init
        Configurable configurable = (Configurable) quadForConfig;
        if (configurable.hasDescription()) {
            showDescriptionCheckBox.setSelected(configurable.isDescriptionShown());
            showDescriptionCheckBox.setOnMouseClicked(event ->
                    configurable.showDescription(showDescriptionCheckBox.isSelected()));
        } else {
            showDescriptionCheckBox.setDisable(true);
        }

        ///*** show trackline borders
        if (configurable.hasBorder()) {
            trackSectionBorderCheckBox.setSelected(configurable.isBorderShown());
            trackSectionBorderCheckBox.setOnMouseClicked(event ->
                    configurable.showTrackBorder(trackSectionBorderCheckBox.isSelected()));
        } else {
            trackSectionBorderCheckBox.setDisable(true);
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
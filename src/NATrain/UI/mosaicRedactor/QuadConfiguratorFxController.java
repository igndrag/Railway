package NATrain.UI.mosaicRedactor;

import NATrain.model.Model;
import NATrain.quads.Quad;
import NATrain.quads.configurableInterfaces.*;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
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
    private ChoiceBox<String> firstTrackSectionChoiceBox;

    @FXML
    private ChoiceBox<String> secondTrackSectionChoiceBox;

    @FXML
    private ChoiceBox<String> signalChoiceBox;

    @FXML
    private ChoiceBox<String> switchChoiceBox;

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

        // *** first track choice box init
        if (quadForConfig instanceof FirstTrackConfigurable) {
            FirstTrackConfigurable firstTrackConfigurable = (FirstTrackConfigurable) quadForConfig;
            firstTrackSectionChoiceBox.setValue(firstTrackConfigurable.getFirstAssociatedTrack().getId());
            firstTrackSectionChoiceBox.getItems().addAll(Model.getTrackSections().keySet());
            firstTrackSectionChoiceBox.getItems().add("none");

            firstTrackSectionChoiceBox.setOnAction(event -> {
                String choiceBoxValue = firstTrackSectionChoiceBox.getValue();
                if (choiceBoxValue.equals("none")) {
                    firstTrackConfigurable.setFirstAssociatedTrack(TrackSection.EMPTY_TRACK_SECTION);
                } else {
                    firstTrackConfigurable.setFirstAssociatedTrack(Model.getTrackSections().get(firstTrackSectionChoiceBox.getValue()));
                }
                quadForConfig.refresh();
            });
        } else {
            firstTrackSectionChoiceBox.setDisable(true);
        }

        // *** second track choice box init
        if (quadForConfig instanceof SecondTrackConfigurable) {
            SecondTrackConfigurable secondTrackConfigurable = (SecondTrackConfigurable) quadForConfig;
            secondTrackSectionChoiceBox.setValue(secondTrackConfigurable.getSecondAssociatedTrack().getId());
            secondTrackSectionChoiceBox.getItems().addAll(Model.getTrackSections().keySet());
            secondTrackSectionChoiceBox.getItems().add("none");

            secondTrackSectionChoiceBox.setOnAction(event -> {
                String choiceBoxValue = secondTrackSectionChoiceBox.getValue();
                if (choiceBoxValue.equals("none")) {
                    secondTrackConfigurable.setSecondAssociatedTrack(TrackSection.EMPTY_TRACK_SECTION);
                } else {
                    secondTrackConfigurable.setSecondAssociatedTrack(Model.getTrackSections().get(secondTrackSectionChoiceBox.getValue()));
                }
                quadForConfig.refresh();
            });
        } else
            secondTrackSectionChoiceBox.setDisable(true);

        // *** signal choice box init
        if (quadForConfig instanceof SignalConfigurable) {
            SignalConfigurable signalConfigurable = (SignalConfigurable) quadForConfig;
            signalChoiceBox.setValue(signalConfigurable.getAssociatedSignal().getId());
            signalChoiceBox.getItems().addAll(Model.getSignals().keySet());
            signalChoiceBox.getItems().add("none");

            signalChoiceBox.setOnAction(event -> {
                String choiceBoxValue = signalChoiceBox.getValue();
                if (choiceBoxValue.equals("none")) {
                    signalConfigurable.setAssociatedSignal(Signal.EMPTY_SIGNAL);
                } else {
                    signalConfigurable.setAssociatedSignal(Model.getSignals().get(signalChoiceBox.getValue()));
                }
                quadForConfig.refresh();
            });
        } else
            signalChoiceBox.setDisable(true);

        // *** switch choice box init
        if (quadForConfig instanceof SwitchConfigurable) {
            SwitchConfigurable switchConfigurable = (SwitchConfigurable) quadForConfig;
            switchChoiceBox.setValue(switchConfigurable.getAssociatedSwitch().getId());
            switchChoiceBox.getItems().addAll(Model.getSwitches().keySet());
            switchChoiceBox.getItems().add("none");

            switchChoiceBox.setOnAction(event -> {
                String choiceBoxValue = switchChoiceBox.getValue();
                if (choiceBoxValue.equals("none")) {
                    switchConfigurable.setAssociatedSwitch(Switch.EMPTY_SWITCH);
                } else {
                    switchConfigurable.setAssociatedSwitch(Model.getSwitches().get(switchChoiceBox.getValue()));
                }
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

        ///*** show track borders
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
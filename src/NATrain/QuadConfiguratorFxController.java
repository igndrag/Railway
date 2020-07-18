package NATrain;

import NATrain.model.Model;
import NATrain.quads.AbstractQuad;
import NATrain.quads.BaseQuad;
import NATrain.utils.QuadFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

    private BaseQuad quadForConfig;
    private Stage stage;
    private Pane parent;
    private EventHandler<? super MouseEvent> eventHandler;

    public void initialize(int x, int y) {

        // *** stage init
        quadForConfig = (BaseQuad) Model.getMainGrid()[y][x];
        quadView = quadForConfig.getView();
        parent = (Pane) quadView.getParent();
        quadForConfig.getBackground().setFill(AbstractQuad.DEFAULT_BACKGROUND_COLOR);
        parent.getChildren().clear();
        parent.getChildren().add(QuadFactory.redactorModeQuadView);
        quadViewPane.getChildren().add(quadView);
        stage = (Stage) saveButton.getScene().getWindow();
        stage.setOnCloseRequest(event -> saveAndClose());
        eventHandler = quadView.getOnMouseClicked();
        quadView.setOnMouseClicked(null);

        // *** first track choice box init
        if (quadForConfig.getFirstAssociatedTrack() != null) {
            firstTrackSectionChoiceBox.setValue(quadForConfig.getFirstAssociatedTrack().getId());
        }
        firstTrackSectionChoiceBox.getItems().addAll(Model.getTrackSections().keySet());
        firstTrackSectionChoiceBox.getItems().add("none");
        firstTrackSectionChoiceBox.setOnAction(event -> {
            String choiceBoxValue = firstTrackSectionChoiceBox.getValue();
            if (choiceBoxValue.equals("none")) {
                quadForConfig.setFirstAssociatedTrack(null);
            } else {
                quadForConfig.setFirstAssociatedTrack(Model.getTrackSections().get(firstTrackSectionChoiceBox.getValue()));
                quadForConfig.getTrackLabel().setText(quadForConfig.getFirstAssociatedTrack().getId());
            }
            quadForConfig.refresh();
        });

        // *** second track choice box init
        if (quadForConfig.getSecondAssociatedTrack() != null) {
            secondTrackSectionChoiceBox.setValue(quadForConfig.getSecondAssociatedTrack().getId());
        }
        secondTrackSectionChoiceBox.getItems().addAll(Model.getTrackSections().keySet());
        secondTrackSectionChoiceBox.getItems().add("none");
        secondTrackSectionChoiceBox.setOnAction(event -> {
            String choiceBoxValue = secondTrackSectionChoiceBox.getValue();
            if (choiceBoxValue.equals("none")) {
               quadForConfig.setSecondAssociatedTrack(null);
            } else {
               quadForConfig.setSecondAssociatedTrack(Model.getTrackSections().get(secondTrackSectionChoiceBox.getValue()));
            }
            quadForConfig.refresh();
        });

        // *** signal choice box init
        if (quadForConfig.getAssociatedSignal() != null) {
            signalChoiceBox.setValue(quadForConfig.getAssociatedSignal().getId());
        }
        signalChoiceBox.getItems().addAll(Model.getSignals().keySet());
        signalChoiceBox.getItems().add("none");
        signalChoiceBox.setOnAction(event -> {
            String choiceBoxValue = signalChoiceBox.getValue();
            if (choiceBoxValue.equals("none")) {
                quadForConfig.setAssociatedSignal(null);
            } else {
                quadForConfig.setAssociatedSignal(Model.getSignals().get(signalChoiceBox.getValue()));
            }
            quadForConfig.refresh();
        });


        // *** switch choice box init
        if (quadForConfig.getAssociatedSwitch() != null) {
            switchChoiceBox.setValue(quadForConfig.getAssociatedSwitch().getId());
        }
        switchChoiceBox.getItems().addAll(Model.getSwitches().keySet());
        switchChoiceBox.getItems().add("none");
        switchChoiceBox.setOnAction(event -> {
            String choiceBoxValue = switchChoiceBox.getValue();
            if (choiceBoxValue.equals("none")) {
                quadForConfig.setAssociatedSwitch(null);
            } else {
                quadForConfig.setAssociatedSwitch(Model.getSwitches().get(switchChoiceBox.getValue()));
            }
            quadForConfig.refresh();
        });

        ///*** show description init
        showDescriptionCheckBox.setOnMouseClicked(event -> {

            if (showDescriptionCheckBox.isSelected()) {
                quadForConfig.getTrackLabel().setTextAlignment(TextAlignment.LEFT);
            } else {
                quadForConfig.getTrackLabel().setTextAlignment(TextAlignment.RIGHT);
            }

        });

        ///*** show track borders

        trackSectionBorderCheckBox.setOnMouseClicked(event -> {
            if (trackSectionBorderCheckBox.isSelected()) {

            }
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

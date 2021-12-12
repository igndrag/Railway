package NATrain.UI.mosaicRedactor;

import NATrain.UI.AppConfigController;
import NATrain.UI.NavigatorFxController;
import NATrain.quads.*;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.utils.QuadFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import NATrain.model.Model;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;


public class MosaicRedactorFxController {

    private static Stage primaryStage;
    private static GridPane gridPane;
    private static Quad selectedQuad;
    private static QuadType selectedQuadType;
    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private MenuItem loadMenuItem;

    @FXML
    private ToggleButton eraserToggleButton;

    @FXML
    private TextFlow textFlowPanel;

    @FXML
    private CheckBox gridLinesCheckBox;

    @FXML
    private TitledPane CustomElementIcons;

    @FXML
    private TableColumn<BaseQuad, String> propNameCol;

    @FXML
    private TableColumn<BaseQuad, String> propValueCol;

    @FXML
    private TextField columnsNumber;

    @FXML
    private TextField rawsNumber;

    @FXML
    private TableView<BaseQuad> propertyTable;

    @FXML
    private ScrollPane doubleTrackSectionIcons;

    @FXML
    private ScrollPane switchIcons;

    @FXML
    private ScrollPane signalIcons;

    @FXML
    private ScrollPane simpleTrackSectionIcons;

    @FXML
    private ScrollPane blockSectionIcons;

    @FXML
    private ScrollPane trackSignalIcons;

    @FXML
    private ScrollPane trackControlIcons;

    public static void setPrimaryStage(Stage mainStage) {
        primaryStage = mainStage;
    }

    public static GridPane getGridPane() {
        return gridPane;
    }

    public static void setGridPane(GridPane gridPane) {
        gridPane = gridPane;
    }

    public static void setModel(Model model) {
        model = model;
    }

    public ScrollPane getWorkArea() {
        return workArea;
    }

    public static void changeGridSize(int x, int y) {

    }

    public void initialize() {
        NavigatorFxController.showGridLines = true;

        //** menu initializing **//
        saveMenuItem.setOnAction(event -> {
            Model.saveOnDisk();
        });

        loadMenuItem.setOnAction(event -> {
            Model.loadFromDisk();
            initMainGrid();
        });

        //*** left panel initializing ***//
        VBox STQVBox = new VBox();
        VBox DTQVBox = new VBox();
        VBox SWQVBox = new VBox();
        VBox SIQVBox = new VBox();
        VBox BTQVbox = new VBox();
        VBox BSQVbox = new VBox();
        VBox BCQVbox = new VBox();

        eraserToggleButton.setToggleGroup(toggleGroup);
        eraserToggleButton.setOnAction(event -> {
            if (eraserToggleButton.isSelected()) {
                log("Eraser activated");
                selectedQuadType = QuadType.EMPTY_QUAD;
            } else {
                selectedQuadType = null;
                log("Eraser deactivated");
            }
        });

        Arrays.stream(QuadType.values()).forEach(quadType -> {
                    ToggleButton button = new ToggleButton();
                    button.setToggleGroup(toggleGroup);
                    button.setOnAction(event -> {
                        if (button.isSelected()) {
                            log("Ready for choose quad position. " + quadType + " selected.");
                            selectedQuadType = quadType;
                        } else {
                            selectedQuadType = null;
                            log(quadType + " unselected");
                        }
                    });
                    button.setGraphic(QuadFactory.createQuad(0, 0, quadType).getView());
                    switch (quadType.toString().substring(0, 3)) {
                        case ("STQ"):
                            STQVBox.getChildren().add(button);
                            break;
                        case ("DTQ"):
                            DTQVBox.getChildren().add(button);
                            break;
                        case ("SWQ"):
                            SWQVBox.getChildren().add(button);
                            break;
                        case ("SIQ"):
                            SIQVBox.getChildren().add(button);
                            break;
                        case ("BTQ"):
                            BTQVbox.getChildren().add(button);
                            break;
                        case ("BSQ"):
                            BSQVbox.getChildren().add(button);
                            break;
                        case ("BCQ") :
                            BCQVbox.getChildren().add(button);
                            break;
                    }

                }
        );
        STQVBox.setPadding(new Insets(0, 0, 0, 15));
        DTQVBox.setPadding(new Insets(0, 0, 0, 15));
        SWQVBox.setPadding(new Insets(0, 0, 0, 15));
        SIQVBox.setPadding(new Insets(0, 0, 0, 15));
        BTQVbox.setPadding(new Insets(0, 0, 0, 15));
        BSQVbox.setPadding(new Insets(0, 0, 0, 15));
        BCQVbox.setPadding(new Insets(0, 0, 0, 15));

        simpleTrackSectionIcons.setContent(STQVBox);
        doubleTrackSectionIcons.setContent(DTQVBox);
        switchIcons.setContent(SWQVBox);
        signalIcons.setContent(SIQVBox);
        blockSectionIcons.setContent(BTQVbox);
        trackSignalIcons.setContent(BSQVbox);
        trackControlIcons.setContent(BCQVbox);

        //*** grid pane panel initializing ***//

        initMainGrid();

        gridLinesCheckBox.setSelected(NavigatorFxController.showGridLines);

        log("Track redactor initialized.");
        log("Choice quad from left panel.");

    }

    private void initMainGrid() {
        gridPane = new GridPane();
//        gridPane.setCache(false);
        int rows = Model.getMainGrid().length;
        int columns = Model.getMainGrid()[0].length;

        //gridPane.setPadding(new Insets(5));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Pane quadPane = new Pane();
                //quadPane.setPadding(new Insets(5));
                Quad quad = Model.getMainGrid()[j][i];
                quad.setGridLineVisible(true);
                Group quadView = quad.getView();
                quadPane.getChildren().add(quadView);
                quad.refresh();
                configQuadView(quadView, i, j);
                gridPane.add(quadPane, i, j);
            }
        }
        workArea.setContent(gridPane);
    }

    public void activateKeyListeners() {
        primaryStage.getScene().setOnKeyTyped(event -> {
            if (event.getCharacter().equals("")) {
                toggleGroup.selectToggle(null); //unselect quad or eraser
                if (selectedQuad != null) {
                    selectedQuad.unselect();
                }
                selectedQuadType = null;
            }
        });
    }

    @FXML
    private ScrollPane workArea;

    @FXML
    private Button testButton;


    @FXML
    private void buttonClicked() {

        //workArea.setContent(new EmptyQuad(0, 0).getView());
    }

    @FXML
    public void redButtonClicked(ActionEvent actionEvent) {
        textFlowPanel.getChildren().clear();
        log("Red button clicked");
    }

    @FXML
    public void greenButtonClicked(ActionEvent actionEvent) {
    }

    public Quad getSelectedQuad() {
        return selectedQuad;
    }

    public static void unselectQuadType() {
        selectedQuadType = null;
    }

    private static void selectQuad(int x, int y) {
        Quad quadForSelect = Model.getMainGrid()[y][x];
        if (selectedQuad != null)
            selectedQuad.unselect();
        quadForSelect.select();
        selectedQuad = quadForSelect;
    }

    private static void toQuadConfigurator(int x, int y) throws IOException {
        if (Model.getMainGrid()[y][x].isEmpty())
            return;
        FXMLLoader loader;
        if (Model.getMainGrid()[y][x] instanceof BlockingBaseQuad) {
            switch (AppConfigController.getLanguage()) {
                case RU:
                    loader = new FXMLLoader(TrackQuadConfiguratorFxController.class.getResource("TrackQuadConfigurator_RU.fxml"));
                    break;
                default:
                    loader = new FXMLLoader(TrackQuadConfiguratorFxController.class.getResource("TrackQuadConfigurator.fxml"));
            }
            Stage trackQuadConfigurator = new Stage();
            trackQuadConfigurator.setTitle("Track Quad Configurator");
            trackQuadConfigurator.setScene(new Scene(loader.load(), 400, 240));
            trackQuadConfigurator.setResizable(false);
            TrackQuadConfiguratorFxController controller = loader.getController();
            controller.initialize(x, y);
            trackQuadConfigurator.initModality(Modality.WINDOW_MODAL);
            trackQuadConfigurator.initOwner(primaryStage);
            trackQuadConfigurator.show();
        } else if (Model.getMainGrid()[y][x] instanceof ArrivalSignalQuad) {
            switch (AppConfigController.getLanguage()) {
                case RU:
                    loader = new FXMLLoader(ArrivalSignalQuadConfiguratorFxController.class.getResource("ArrivalSignalQuadConfigurator_RU.fxml"));
                    break;
                default:
                    loader = new FXMLLoader(ArrivalSignalQuadConfiguratorFxController.class.getResource("ArrivalSignalQuadConfigurator.fxml"));
            }
            Stage quadConfigurator = new Stage();
            quadConfigurator.setTitle("Quad Configurator");
            quadConfigurator.setScene(new Scene(loader.load(), 400, 240));
            quadConfigurator.setResizable(false);
            ArrivalSignalQuadConfiguratorFxController controller = loader.getController();
            controller.initialize(x, y);
            quadConfigurator.initModality(Modality.WINDOW_MODAL);
            quadConfigurator.initOwner(primaryStage);
            quadConfigurator.show();
        } else {
            switch (AppConfigController.getLanguage()) {
                case RU:
                    loader = new FXMLLoader(QuadConfiguratorFxController.class.getResource("QuadConfigurator_RU.fxml"));
                    break;
                default:
                    loader = new FXMLLoader(QuadConfiguratorFxController.class.getResource("QuadConfigurator.fxml"));
            }
            Stage quadConfigurator = new Stage();
            quadConfigurator.setTitle("Quad Configurator");
            quadConfigurator.setScene(new Scene(loader.load(), 400, 300));
            quadConfigurator.setResizable(false);
            QuadConfiguratorFxController controller = loader.getController();
            controller.initialize(x, y);
            quadConfigurator.initModality(Modality.WINDOW_MODAL);
            quadConfigurator.initOwner(primaryStage);
            quadConfigurator.show();
        }
    }

    protected void configQuadView(Node quadView, int x, int y) {
        quadView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                selectQuad(x, y);
                if (selectedQuadType != null) {
                    putQuadOnGrid(x, y, selectedQuadType);
                    Pane parent = (Pane) quadView.getParent();
                    parent.getChildren().clear();
                }
            }

            if (event.getClickCount() == 2) {
                try {
                    toQuadConfigurator(x, y);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void putQuadOnGrid(int x, int y, QuadType quadType) {
        Quad newQuad = QuadFactory.createQuad(x, y, selectedQuadType);
        Model.getMainGrid()[y][x] = newQuad;
        Pane quadPane = new Pane();
        //  quadPane.setPadding(new Insets(5));
        quadPane.getChildren().add(newQuad.getView());
        configQuadView(newQuad.getView(), x, y);
        gridPane.add(quadPane, x, y);
        log(String.format("%s quad type located to x = %d, y = %d position", selectedQuadType.toString(), x, y));
    }

    private void log(String message) {
        //https://stackoverflow.com/questions/40822806/add-elements-on-textflow-using-external-thread-in-javafx
        Platform.runLater(() -> {
            if (textFlowPanel.getChildren().size() > 4) {
                textFlowPanel.getChildren().remove(0);
            }
            textFlowPanel.getChildren().add(new Text(message + System.lineSeparator()));
        });
    }

    @FXML
    private void showGridLines() {
        Model.setGridLinesVisible(gridLinesCheckBox.isSelected());
        NavigatorFxController.showGridLines = gridLinesCheckBox.isSelected();
    }
}


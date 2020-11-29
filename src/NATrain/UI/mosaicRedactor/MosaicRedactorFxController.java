package NATrain.UI.mosaicRedactor;

import NATrain.UI.NavigatorFxController;
import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
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
import NATrain.quads.Quad;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;


public class MosaicRedactorFxController {

    private static Stage primaryStage;
    private static GridPane gridPane;
    private static Model model;
    private static Quad selectedQuad;
    private static QuadType selectedQuadType;

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

    public static void setPrimaryStage(Stage mainStage) {
        primaryStage = mainStage;
    }

    public static GridPane getGridPane() {
        return gridPane;
    }

    public static void setGridPane(GridPane gridPane) {
        gridPane = gridPane;
    }

    public static Model getModel() {
        return model;
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

        ToggleGroup toggleGroup = new ToggleGroup();
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
                    button.setGraphic(QuadFactory.createQuad(0,0, quadType).getView());
                    switch (quadType.toString().substring(0, 3)) {
                        case ("STQ") :
                            STQVBox.getChildren().add(button);
                            break;
                        case ("DTQ") :
                            DTQVBox.getChildren().add(button);
                            break;
                        case ("SWQ") :
                            SWQVBox.getChildren().add(button);
                            break;
                        case ("SIQ") :
                            SIQVBox.getChildren().add(button);
                            break;
                    }

                }
        );
        STQVBox.setPadding(new Insets(0,0,0,15));
        DTQVBox.setPadding(new Insets(0,0,0,15));
        SWQVBox.setPadding(new Insets(0,0,0,15));
        SIQVBox.setPadding(new Insets(0,0,0,15));
        simpleTrackSectionIcons.setContent(STQVBox);
        doubleTrackSectionIcons.setContent(DTQVBox);
        switchIcons.setContent(SWQVBox);
        signalIcons.setContent(SIQVBox);

        //*** grid pane panel initializing ***//

        initMainGrid();

        gridLinesCheckBox.setSelected(NavigatorFxController.showGridLines);

        log("Track redactor initialized.");
        log("Choice quad from left panel.");

    }

    private void initMainGrid() {
        gridPane = new GridPane();
//        gridPane.setCache(false);
        int raws = Model.getMainGrid().length;
        int columns = Model.getMainGrid()[0].length;

        //gridPane.setPadding(new Insets(5));
        for (int i = 0; i < raws; i++) {
            for (int j = 0; j < columns; j++) {
                Pane quadPane = new Pane();
                //quadPane.setPadding(new Insets(5));
                Quad quad = Model.getMainGrid()[j][i];
                quad.setGridLineVisible(true);
                Group quadView = quad.getView();
                quadPane.getChildren().add(quadView);
                configQuadView(quadView, i, j);
                gridPane.add(quadPane, i, j);
            }
        }
        workArea.setContent(gridPane);
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
        FXMLLoader loader = new FXMLLoader(MosaicRedactorFxController.class.getResource("QuadConfigurator.fxml"));
        Stage quadConfigurator = new Stage();
        quadConfigurator.setTitle("Quad configurator");
        quadConfigurator.setScene(new Scene(loader.load(), 400, 300));
        quadConfigurator.setResizable(false);
        QuadConfiguratorFxController controller = loader.getController();
        controller.initialize(x, y);
        quadConfigurator.initModality(Modality.WINDOW_MODAL);
        quadConfigurator.initOwner(primaryStage);
        quadConfigurator.show();
    }

    protected void configQuadView(Node quadView, int x, int y) {
        quadView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                selectQuad(x, y);
                if (selectedQuadType != null) {
                    putQuadOnGrid(x, y, selectedQuadType);
                    Pane parent = (Pane)quadView.getParent();
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


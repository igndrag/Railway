package NATrain;

import NATrain.quads.QuadType;
import NATrain.utils.ModelMock;
import NATrain.utils.QuadFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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


public class TrackRedactorFxController {

    private static Stage primaryStage;
    private static GridPane gridPane;
    private static Model model;
    private static Quad selectedQuad;
    private static QuadType selectedQuadType;

    @FXML
    public TextFlow textFlowPanel;

    @FXML
    private TitledPane CustomElementIcons;

    @FXML
    private TableColumn propNameCol;

    @FXML
    private TableColumn propValueCol;

    @FXML
    private TextField columnsNumber;

    @FXML
    private TextField rawsNumber;

    @FXML
    private TableView propertyTable;

    @FXML
    private TitledPane doubleTrackSectionIcons;

    @FXML
    private TitledPane switchIcons;

    @FXML
    private TitledPane signalIcons;

    @FXML
    private TitledPane doubleSignalIcons;

    @FXML
    private ScrollPane simpleTrackSectionIcons;

    @FXML
    private Button redButton;

    @FXML
    private Button greenButton;

    public static void setPrimaryStage(Stage mainStage) {
        TrackRedactorFxController.primaryStage = mainStage;
    }

    public static void setConstructorMode(boolean mode) {
        NavigatorFxController.constructorMode = mode;
    }

    public static GridPane getGridPane() {
        return gridPane;
    }

    public static void setGridPane(GridPane gridPane) {
        TrackRedactorFxController.gridPane = gridPane;
    }

    public static Model getModel() {
        return model;
    }

    public static void setModel(Model model) {
        TrackRedactorFxController.model = model;
    }

    public ScrollPane getWorkArea() {
        return workArea;
    }


    public static void changeGridSize(int x, int y) {

    }

    public void initialize() {

        ModelMock.MockModel();

        //*** left panel initializing ***//
        VBox STQVBox = new VBox();
        VBox DTQVBox = new VBox();
        VBox SWQVBox = new VBox();
        VBox SIQVBox = new VBox();
        VBox DSIQVBox = new VBox();

        ToggleGroup toggleGroup = new ToggleGroup();
        Arrays.stream(QuadType.values()).forEach(quadType -> {
                    ToggleButton button = new ToggleButton();
                    button.setToggleGroup(toggleGroup);
                    button.setOnAction(event -> {
                        if (button.isSelected()) {
                            log("Ready for choose quad position. " + quadType + " selected.");
                            System.out.println(quadType + " selected.");
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
                        case ("DSI") :
                            DSIQVBox.getChildren().add(button);
                            break;
                    }

                }
        );
        STQVBox.setPadding(new Insets(0,0,0,15));
        simpleTrackSectionIcons.setContent(STQVBox);
        doubleTrackSectionIcons.setContent(DTQVBox);
        switchIcons.setContent(SWQVBox);
        signalIcons.setContent(SIQVBox);
        doubleSignalIcons.setContent(DSIQVBox);


        //*** grid pane panel initializing ***//

        gridPane = new GridPane();
        gridPane.setCache(false);
        int raws = Model.getMainGrid().length;
        int columns = Model.getMainGrid()[0].length;

        //gridPane.setPadding(new Insets(5));
        for (int i = 0; i < raws; i++) {
            for (int j = 0; j < columns; j++) {
                Pane quadPane = new Pane();
                //quadPane.setPadding(new Insets(5));
                Group quadView = Model.getMainGrid()[j][i].getView();
                quadPane.getChildren().add(quadView);
                configQuadView(quadView, i, j);
                gridPane.add(quadPane, i, j);
            }
        }
        workArea.setContent(gridPane);


        log("Track redactor initialized.");
        log("Choice quad from left panel.");
    }

    @FXML
    private ScrollPane workArea;

    @FXML
    private Button testButton;


    @FXML
    private void buttonClicked() {
        System.out.println("Clicked");
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
        FXMLLoader loader = new FXMLLoader(TrackRedactorFxController.class.getResource("quadConfigurator.fxml"));
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
            System.out.println(event.getClickCount());

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
}


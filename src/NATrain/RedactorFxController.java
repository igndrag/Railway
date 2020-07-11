package NATrain;

import NATrain.library.QuadType;
import NATrain.utils.QuadFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import NATrain.library.QuadLibrary;
import NATrain.model.Model;
import NATrain.quads.EmptyQuad;
import NATrain.quads.Quad;
import NATrain.view.View;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class RedactorFxController {

    private static boolean constructorMode = true;
    private static GridPane gridPane;
    private static Model model;
    private static Quad selectedQuad;
    private static Stage primaryStage;
    private static QuadType selectedQuadType;

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
    private TitledPane simpleTrackSectionIcons;

    @FXML
    private Button redButton;

    @FXML
    private Button greenButton;

    public static void setPrimaryStage(Stage mainStage) {
        RedactorFxController.primaryStage = mainStage;
    }

    public static boolean isConstructorMode() {
        return constructorMode;
    }

    public static void setConstructorMode(boolean mode) {
        constructorMode = mode;
    }

    public static GridPane getGridPane() {
        return gridPane;
    }

    public static void setGridPane(GridPane gridPane) {
        RedactorFxController.gridPane = gridPane;
    }

    public static Model getModel() {
        return model;
    }

    public static void setModel(Model model) {
        RedactorFxController.model = model;
    }

    public ScrollPane getWorkArea() {
        return workArea;
    }


    public static void changeGridSize(int x, int y) {

    }

    public void initialize() {
        int raws = 20;
        int columns = 20;
        View.setSize(raws, columns);

        //*** left panel initializing ***//
        VBox leftPanelVBox = new VBox();
        ToggleGroup toggleGroup = new ToggleGroup();
        QuadLibrary.getSimpleTrackQuadImgLib().forEach((quadType, image) -> {
                    ToggleButton button = new ToggleButton();
                    button.setToggleGroup(toggleGroup);
                    button.setOnAction(event -> {
                        if (button.isSelected()) {
                            System.out.println("Ready for choose quad position. " + quadType + " selected.");
                            selectedQuadType = quadType;
                        } else {
                            selectedQuadType = null;
                            System.out.println(quadType + " unselected ");
                        }
                    });
                    button.setGraphic(new ImageView(image));
                    leftPanelVBox.getChildren().add(button);
                }
        );
        simpleTrackSectionIcons.setContent(leftPanelVBox);

        //*** grid pane panel initializing ***//

        gridPane = new GridPane();
        //gridPane.setPadding(new Insets(5));
        for (int i = 0; i < raws; i++) {
            for (int j = 0; j < columns; j++) {
                EmptyQuad emptyQuad = new EmptyQuad(i, j);
                View.getMainGrid()[j][i] = emptyQuad;
                Pane quadPane = new Pane();
                quadPane.setPadding(new Insets(5));
                quadPane.getChildren().add(emptyQuad.getView());
                configQuadView(emptyQuad.getView(), i, j);
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
        System.out.println("Clicked");
        //workArea.setContent(new EmptyQuad(0, 0).getView());
    }

    @FXML
    public void redButtonClicked(ActionEvent actionEvent) {

    }

    @FXML
    public void greenButtonClicked(ActionEvent actionEvent) {
    }

    public void onView(int x, int y) {
        View.getMainGrid()[y][x].refresh();
    }

    private static void selectQuad(int x, int y) {
        Quad quadForSelect = View.getMainGrid()[y][x];
        if (selectedQuad != null)
            selectedQuad.unselect();
        quadForSelect.select();
        selectedQuad = quadForSelect;
    }

    private static void toQuadConfigurator(int x, int y) throws IOException {
        if (View.getMainGrid()[y][x] instanceof EmptyQuad)
            return;
        FXMLLoader loader = new FXMLLoader(RedactorFxController.class.getResource("quadConfigurator.fxml"));
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

    private static void configQuadView(Node quadView, int x, int y) {
        quadView.setOnMouseClicked(event -> {
            System.out.println(event.getClickCount());

            if (event.getClickCount() == 1) {
                selectQuad(x, y);
                if (selectedQuadType != null)
                    putQuadOnGrid(x, y, selectedQuadType);
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

    public static void putQuadOnGrid(int x, int y, QuadType quadType) {
        Quad newQuad = QuadFactory.createQuad(x, y, selectedQuadType);
        View.getMainGrid()[y][x] = newQuad;
        Pane quadPane = new Pane();
        quadPane.setPadding(new Insets(5));
        quadPane.getChildren().add(newQuad.getView());
        configQuadView(newQuad.getView(), x, y);
        gridPane.add(quadPane, x, y);
    }
}


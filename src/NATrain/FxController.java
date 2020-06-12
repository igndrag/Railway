package NATrain;

import NATrain.library.QuadType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import NATrain.library.QuadLibrary;
import NATrain.model.MainModel;
import NATrain.quads.EmptyQuad;
import NATrain.quads.Quad;
import NATrain.view.View;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class FxController {

    private static boolean constructorMode = true;
    private static GridPane gridPane;
    private static MainModel mainModel;
    private static Quad selectedQuad;
    private static Stage primaryStage;
    private static QuadType selectedQuadType = QuadType.STQ1_1;

    @FXML
    public TextField columnsNumber;

    @FXML
    public TextField rawsNumber;

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
        FxController.primaryStage = mainStage;
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
        FxController.gridPane = gridPane;
    }

    public static MainModel getMainModel() {
        return mainModel;
    }

    public static void setMainModel(MainModel mainModel) {
        FxController.mainModel = mainModel;
    }

    public ScrollPane getWorkArea() {
        return workArea;
    }


    public static void changeGridSize (int x, int y) {

    }

    public void initialize() {
        int raws = 20;
        int columns = 20;
        View.setSize(raws, columns);

        //*** left panel initializing ***//
            VBox leftPanelVBox = new VBox();
            QuadLibrary.getSimpleTrackQuadImgLib().forEach((quadType, image) -> {
            Button button = new Button();
            button.setOnAction(event -> {
                    System.out.println("Ready for choose quad position. " + quadType + " selected.");
                    selectedQuadType = quadType;}
                    );
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
                EmptyQuad emptyQuad = new EmptyQuad(i, j, true);
                View.getMainGrid()[j][i] = emptyQuad;
                StackPane quadPane = new StackPane();
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

    private static void selectQuad (int x, int y) {
        Quad quadForSelect = View.getMainGrid()[y][x];
        if (selectedQuad != null)
        selectedQuad.unselect();
        quadForSelect.select();
        selectedQuad = quadForSelect;
    }

    private void toQuadConfigurator(int x, int y) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("quadConfigurator.fxml"));
        Stage quadConfigurator = new Stage();
        quadConfigurator.setTitle("Quad configurator");
        quadConfigurator.setScene(new Scene(loader.load(), 400, 300));
        quadConfigurator.setResizable(false);
        QuadConfiguratorController controller = loader.getController();
        controller.initialize(x, y, selectedQuadType);
        quadConfigurator.initModality(Modality.WINDOW_MODAL);
        quadConfigurator.initOwner(primaryStage);
        quadConfigurator.show();
    }

    private void configQuadView(Node quadView, int x, int y) {
        quadView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1)
                selectQuad(x, y);
            if (event.getClickCount() == 2) {
                try {
                    toQuadConfigurator(x, y);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

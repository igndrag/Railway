package NATrain.UI.workPlace;

import NATrain.model.Model;
import NATrain.trackSideObjects.movableObjects.Movable;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LocatorController {

    public static void refreshTable(){
        if (movableObjectsTableStaticReference != null) {
                movableObjectsTableStaticReference.refresh();
        }
    }

    static TableView <Movable> movableObjectsTableStaticReference;

    @FXML
    private TableView <Movable> locatorTable;
    @FXML
    private TableColumn <Movable, String> movableObjectColumn;
    @FXML
    private TableColumn <Movable, TrackSection> frontTagColumn;
    @FXML
    private TableColumn <Movable, TrackSection> rearTagColumn ;

    public void init() {
        locatorTable.setItems(FXCollections.observableArrayList(Model.getLocomotives().values()));
        movableObjectColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        frontTagColumn.setCellValueFactory(new PropertyValueFactory<>("frontTagLocation"));
        rearTagColumn.setCellValueFactory(new PropertyValueFactory<>("rearTagLocation"));
        movableObjectsTableStaticReference = locatorTable;
    }

}

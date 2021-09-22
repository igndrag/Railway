package NATrain.UI.workPlace;

import NATrain.UI.UIUtils;
import NATrain.model.Model;
import NATrain.routes.RouteDirection;
import NATrain.trackSideObjects.RFIDTag;
import NATrain.trackSideObjects.movableObjects.Locomotive;
import NATrain.trackSideObjects.movableObjects.Movable;
import NATrain.trackSideObjects.movableObjects.MovableObjectType;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class LocomotiveSelectorController {

    @FXML
    private ListView<Movable> movableObjectsListView;
    @FXML
    private ToggleButton evenToggleButton;
    @FXML
    private ToggleButton oddToggleButton;
    @FXML
    private Button okButton;

    public void initialize(TrackSection trackSection) {
        ObservableList<Movable> movableObjects = FXCollections.observableArrayList(Model.getLocomotives().values());
        movableObjects.addAll(Model.getWagons().values());
        movableObjectsListView.setItems(movableObjects);


        ToggleGroup toggleGroup = new ToggleGroup();

        evenToggleButton.setToggleGroup(toggleGroup);
        oddToggleButton.setToggleGroup(toggleGroup);

        okButton.setOnAction(event -> {
            if (movableObjectsListView.getSelectionModel().isEmpty()) {
                UIUtils.showAlert("Select movable object, please.");
                return;
            } else
            if (movableObjectsListView.getSelectionModel().getSelectedItem().getMovableObjectType() == MovableObjectType.LOCOMOTIVE &&
                    !evenToggleButton.isSelected() &&
                    !oddToggleButton.isSelected()) {
                UIUtils.showAlert("Select forward direction, please.");
                return;
            }
            Movable movableObject = movableObjectsListView.getSelectionModel().getSelectedItem();
            RFIDTag frontTag = movableObject.getFrontTag();
            RFIDTag rearTag = movableObject.getRearTag();
            if (frontTag != null && frontTag.getTagLocation() != null) {
                frontTag.getTagLocation().getTags().remove(frontTag);
                frontTag.getTagLocation().updateVacancyState();
            }
            if (rearTag != null && rearTag.getTagLocation() != null) {
                rearTag.getTagLocation().getTags().remove(rearTag);
                rearTag.getTagLocation().updateVacancyState();
            }
            if (frontTag != null && frontTag != RFIDTag.EMPTY_TAG) {
                frontTag.setTagLocation(trackSection);
                trackSection.getTags().add(frontTag);
            }

            if (rearTag != null && rearTag != RFIDTag.EMPTY_TAG) {
                rearTag.setTagLocation(trackSection);
                trackSection.getTags().add(rearTag);
            }

            trackSection.updateVacancyState();

            LocatorController.refreshTable();

            if (movableObject.getMovableObjectType() == MovableObjectType.LOCOMOTIVE) {
                Locomotive locomotive = (Locomotive) movableObject;
                if (evenToggleButton.isSelected()) {
                    locomotive.setForwardDirection(RouteDirection.EVEN);
                } else {
                    locomotive.setForwardDirection(RouteDirection.ODD);
                }
            }

            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();

        });

    }


}

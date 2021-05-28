package NATrain.UI.workPlace;

import NATrain.UI.UIUtils;
import NATrain.model.Model;
import NATrain.routes.RouteDirection;
import NATrain.trackSideObjects.locomotives.Locomotive;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class LocomotiveSelectorController {

    @FXML
    private ListView<Locomotive> locomotiveListView;
    @FXML
    private ToggleButton evenToggleButton;
    @FXML
    private ToggleButton oddToggleButton;
    @FXML
    private Button okButton;

    public void initialize(TrackSection trackSection) {
        locomotiveListView.setItems(FXCollections.observableArrayList(Model.getLocomotives().values()));

        ToggleGroup toggleGroup = new ToggleGroup();

        evenToggleButton.setToggleGroup(toggleGroup);
        oddToggleButton.setToggleGroup(toggleGroup);

        okButton.setOnAction(event -> {
            if (locomotiveListView.getSelectionModel().isEmpty()) {
                UIUtils.showAlert("Select Locomotive, please.");
                return;
            }
            if (!evenToggleButton.isSelected() && !oddToggleButton.isSelected()) {
                UIUtils.showAlert("Select forward direction, please.");
                return;
            }
            Locomotive locomotive = locomotiveListView.getSelectionModel().getSelectedItem();
            locomotive.getFrontTag().setTagLocation(trackSection);
            locomotive.getRearTag().setTagLocation(trackSection);
            trackSection.getTags().add(locomotive.getFrontTag());
            trackSection.getTags().add(locomotive.getRearTag());

            if (evenToggleButton.isSelected()) {
                locomotive.setForwardDirection(RouteDirection.EVEN);
            } else {
                locomotive.setForwardDirection(RouteDirection.ODD);
            }

            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();

        });

    }


}

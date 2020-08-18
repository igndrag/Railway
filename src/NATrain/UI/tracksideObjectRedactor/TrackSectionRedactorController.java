package NATrain.UI.tracksideObjectRedactor;

import NATrain.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TrackSectionRedactorController extends TracksideObjectRedactorController{ //make generic class

    @FXML
    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        trackSideObject.setId(textField.getText());
        if (!isNameValid())
            return;
        // put logic here for another TSO types
        updateModelAndClose(Model.getTrackSections());
    }
}

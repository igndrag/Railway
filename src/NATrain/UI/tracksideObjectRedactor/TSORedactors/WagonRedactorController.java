package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.UIUtils;
import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.model.Model;
import NATrain.trackSideObjects.RFIDTag;
import NATrain.trackSideObjects.TagType;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.movableObjects.MovableObjectType;
import NATrain.trackSideObjects.movableObjects.Wagon;
import NATrain.utils.UtilFunctions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WagonRedactorController extends TracksideObjectRedactorController implements TagConfigurable {

    @FXML
    public Button frontReadButton;
    @FXML
    public Button rearReadButton;
    @FXML
    public CheckBox shortBaseCheckBox;
    @FXML
    private TextField frontTagTextField1;
    @FXML
    private TextField frontTagTextField2;
    @FXML
    private TextField frontTagTextField3;
    @FXML
    private TextField frontTagTextField4;
    @FXML
    private TextField rearTagTextField1;
    @FXML
    private TextField rearTagTextField2;
    @FXML
    private TextField rearTagTextField3;
    @FXML
    private TextField rearTagTextField4;

    private Wagon wagon;

    @Override
    public void setFrontUid(String uid) {
        String[] uidParts = uid.split(" ");
        if (uidParts.length < 4)
            return;
        if (isUidValid(uidParts[0], uidParts[1], uidParts[2], uidParts[3])) {
            frontTagTextField1.setText(uidParts[0]);
            frontTagTextField2.setText(uidParts[1]);
            frontTagTextField3.setText(uidParts[2]);
            frontTagTextField4.setText(uidParts[3]);
        }
    }

    @Override
    public void setRearUid(String uid) {
        String[] uidParts = uid.split(" ");
        if (isUidValid(uidParts[0], uidParts[1], uidParts[2], uidParts[3])) {
            rearTagTextField1.setText(uidParts[0]);
            rearTagTextField2.setText(uidParts[1]);
            rearTagTextField3.setText(uidParts[2]);
            rearTagTextField4.setText(uidParts[3]);
        }
    }

    @Override
    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        this.wagon = (Wagon) tracksideObject;
        textField.setText(wagon.getId());
        this.tableView = tableView;
        this.observableList = observableList;
        this.initialName = wagon.getId();

        if (!wagon.getId().equals(Wagon.INITIAL_WAGON_NAME)) {
            textField.setDisable(true);
        } else {
            textField.setOnMouseClicked(event -> {
                textField.selectAll();
            });
        }

        shortBaseCheckBox.setOnAction(event -> {
            if (shortBaseCheckBox.isSelected()) {
                rearTagTextField1.clear();
                rearTagTextField1.setDisable(true);
                rearTagTextField2.clear();
                rearTagTextField2.setDisable(true);
                rearTagTextField3.clear();
                rearTagTextField3.setDisable(true);
                rearTagTextField4.clear();
                rearTagTextField4.setDisable(true);
                rearReadButton.setDisable(true);
            } else {
                rearTagTextField1.setDisable(false);
                rearTagTextField2.setDisable(false);
                rearTagTextField3.setDisable(false);
                rearTagTextField4.setDisable(false);
                rearReadButton.setDisable(false);
            }
        });

        if (wagon.getFrontTag() != null) {
            RFIDTag frontTag = wagon.getFrontTag();
            frontTagTextField1.setText(frontTag.getUid()[0]);
            frontTagTextField1.setDisable(true);
            frontTagTextField2.setText(frontTag.getUid()[1]);
            frontTagTextField2.setDisable(true);
            frontTagTextField3.setText(frontTag.getUid()[2]);
            frontTagTextField3.setDisable(true);
            frontTagTextField4.setText(frontTag.getUid()[3]);
            frontTagTextField4.setDisable(true);
            frontReadButton.setText("Clear");
            frontReadButton.setOnAction(event -> {
                frontTagTextField1.setDisable(false);
                frontTagTextField1.clear();
                frontTagTextField2.setDisable(false);
                frontTagTextField2.clear();
                frontTagTextField3.setDisable(false);
                frontTagTextField3.clear();
                frontTagTextField4.setDisable(false);
                frontTagTextField4.clear();
                Model.getTags().remove(wagon.getFrontTag().getDecUid());
                wagon.setFrontTag(null);
                frontReadButton.setOnAction(ev -> {
                    try {
                        toTagReader(this, TagType.FRONT_TAG);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                frontReadButton.setText("Read");
            });
        } else {
            frontReadButton.setOnAction(event -> {
                try {
                    toTagReader(this, TagType.FRONT_TAG);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (wagon.getRearTag() != null) {
            RFIDTag rearTag = wagon.getRearTag();
            rearTagTextField1.setText(rearTag.getUid()[0]);
            rearTagTextField1.setDisable(true);
            rearTagTextField2.setText(rearTag.getUid()[1]);
            rearTagTextField2.setDisable(true);
            rearTagTextField3.setText(rearTag.getUid()[2]);
            rearTagTextField3.setDisable(true);
            rearTagTextField4.setText(rearTag.getUid()[3]);
            rearTagTextField4.setDisable(true);
            rearReadButton.setText("Clear");
            rearReadButton.setOnAction(event -> {
                rearTagTextField1.setDisable(false);
                rearTagTextField1.clear();
                rearTagTextField2.setDisable(false);
                rearTagTextField2.clear();
                rearTagTextField3.setDisable(false);
                rearTagTextField3.clear();
                rearTagTextField4.setDisable(false);
                rearTagTextField4.clear();
                Model.getTags().remove(wagon.getRearTag().getDecUid());
                wagon.setRearTag(null);
                rearReadButton.setOnAction(ev -> {
                    try {
                        toTagReader(this, TagType.REAR_TAG);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                rearReadButton.setText("Read");
            });
        } else {
            rearReadButton.setOnAction(event -> {
                try {
                    toTagReader(this, TagType.REAR_TAG);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @FXML
    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        wagon.setId(textField.getText());
        if (!isNameValid(Model.getWagons(), Wagon.INITIAL_WAGON_NAME)) {
            return;
        }

        if (!frontTagTextField1.isDisabled()) {
            if (!isUidValid(frontTagTextField1.getText(),
                    frontTagTextField2.getText(),
                    frontTagTextField3.getText(),
                    frontTagTextField4.getText())) {
                return;
            }

            RFIDTag frontTag = new RFIDTag(
                    frontTagTextField1.getText(),
                    frontTagTextField2.getText(),
                    frontTagTextField3.getText(),
                    frontTagTextField4.getText(),
                    TagType.FRONT_TAG);

            frontTag.setId(String.format(("%s_front_tag"), wagon.getId()));
            wagon.setFrontTag(frontTag);
            Model.getTags().put(frontTag.getDecUid(), frontTag);
        }

        if (!shortBaseCheckBox.isSelected()) {
            if (!rearTagTextField1.isDisabled()) {
                if (!isUidValid(rearTagTextField1.getText(),
                        rearTagTextField2.getText(),
                        rearTagTextField3.getText(),
                        rearTagTextField4.getText())) {
                    return;
                }

                RFIDTag rearTag = new RFIDTag(
                        rearTagTextField1.getText(),
                        rearTagTextField2.getText(),
                        rearTagTextField3.getText(),
                        rearTagTextField4.getText(),
                        TagType.REAR_TAG);
                rearTag.setId(String.format(("%s_rear_tag"), wagon.getId()));
                wagon.setRearTag(rearTag);
                Model.getTags().put(rearTag.getDecUid(), rearTag);
            }
            wagon.setMovableObjectType(MovableObjectType.LONG_BASE_WAGON);
        } else {
            wagon.setMovableObjectType(MovableObjectType.SHORT_BASE_WAGON);
        }

        updateModelAndClose(Model.getWagons(), wagon);
    }

    private boolean checkByte(String expectedByte) {
        try {
            int expectedDec = Integer.parseInt(expectedByte, 16);
            return 0 <= expectedDec && expectedDec <= 256;
        } catch (NumberFormatException e) {
            UIUtils.showAlert(String.format("%s is incorrect byte.", expectedByte));
            return false;
        }
    }

    protected boolean isUidValid(String b1, String b2, String b3, String b4) {
        boolean bytesChecked = checkByte(b1) && checkByte(b2) && checkByte(b3) && checkByte(b4);
        if (bytesChecked) {
            long decUid = UtilFunctions.convertUidToLong(b1, b2, b3, b4);
            if (Model.getTags().containsKey(decUid)) {
                UIUtils.showAlert(String.format("Tag %s %s %s %s is already configured. Id: %s", b1, b2, b3, b4, Model.getTags().get(decUid).getId()));
                return false;
            }
        }
        return bytesChecked;
    }

    protected void toTagReader(WagonRedactorController wagonRedactorController, TagType tagType) throws IOException {
        FXMLLoader loader = new FXMLLoader(TrackSectionRedactorController.class.getResource("TagReader.fxml"));
        Stage tagReader = new Stage();
        tagReader.setTitle("TagReader");
        tagReader.setScene(new Scene(loader.load(), 250, 170));
        tagReader.setResizable(false);
        TagReaderController controller = loader.getController();
        controller.init(wagonRedactorController, tagType);
        tagReader.initModality(Modality.WINDOW_MODAL);
        tagReader.initOwner(frontReadButton.getScene().getWindow());
        tagReader.show();
        tagReader.setOnCloseRequest(event -> {
            controller.closeReader();
        });
    }
}
package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.UIUtils;
import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.connectionService.MQTTConnectionService;
import NATrain.model.Model;
import NATrain.trackSideObjects.RFIDTag;
import NATrain.trackSideObjects.TagType;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.movableObjects.railCircuitAutopilot;
import NATrain.trackSideObjects.movableObjects.Locomotive;
import NATrain.utils.UtilFunctions;
import NATrain.сontrolModules.MQTTLocomotiveModule;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LocomotiveRedactorController extends TracksideObjectRedactorController implements TagConfigurable {

    @FXML
    public Button frontReadButton;
    @FXML
    public Button rearReadButton;
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
    @FXML
    private TextField fullSpeedTextField;
    @FXML
    private Button fullSpeedTestButton;
    @FXML
    private TextField halfSpeedTextField;
    @FXML
    private Button halfSpeedTestButton;
    @FXML
    private Locomotive locomotive;

    @Override
    public void setFrontUid (String uid) {
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
    public void setRearUid (String uid) {
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
        this.locomotive = (Locomotive) tracksideObject;
        textField.setText(locomotive.getId());
        this.tableView = tableView;
        this.observableList = observableList;
        this.initialName = locomotive.getId();

        if (locomotive.getControlModule() != null) {
            textField.setDisable(true);
        } else {
            textField.setOnMouseClicked(event -> {
                textField.selectAll();
            });
        }

        fullSpeedTextField.setText(locomotive.getFullSpeed() + "");
        halfSpeedTextField.setText(locomotive.getRestrictedSpeed() + "");

        fullSpeedTestButton.setOnAction(event -> {
            speedTest(railCircuitAutopilot.FULL_SPEED);
        });

        halfSpeedTestButton.setOnAction(event -> {
            speedTest(railCircuitAutopilot.RESTRICTED_SPEED);
        });

        if (locomotive.getFrontTag() != null) {
            RFIDTag frontTag = locomotive.getFrontTag();
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
                Model.getTags().remove(locomotive.getFrontTag().getDecUid());
                locomotive.setFrontTag(null);
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

        if (locomotive.getRearTag() != null) {
            RFIDTag rearTag = locomotive.getRearTag();
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
                Model.getTags().remove(locomotive.getRearTag().getDecUid());
                locomotive.setRearTag(null);
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
        locomotive.setId(textField.getText());
        if (!isNameValid(Model.getLocomotives(), Locomotive.INITIAL_LOCOMOTIVE_NAME)) {
            return;
        }

        if (!frontTagTextField1.isDisabled()) {
            if (!isUidValid(frontTagTextField1.getText(),
                    frontTagTextField2.getText(),
                    frontTagTextField3.getText(),
                    frontTagTextField4.getText())) {
                return;
            }

            RFIDTag frontTag = new RFIDTag (
                    frontTagTextField1.getText(),
                    frontTagTextField2.getText(),
                    frontTagTextField3.getText(),
                    frontTagTextField4.getText(),
                    TagType.FRONT_TAG);

            frontTag.setId(String.format(("%s_front_tag"), locomotive.getId()));
            locomotive.setFrontTag(frontTag);
        }

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
            rearTag.setId(String.format(("%s_rear_tag"), locomotive.getId()));
            locomotive.setRearTag(rearTag);
            Model.getTags().put(rearTag.getDecUid(), rearTag);
        }

        int expectedFullSpeed = UtilFunctions.parseIfPositiveNumeric(fullSpeedTextField.getText());
        if (expectedFullSpeed > 0) {
            locomotive.setFullSpeed(expectedFullSpeed);
        }

        int expectedHalfSpeed = UtilFunctions.parseIfPositiveNumeric(halfSpeedTextField.getText());
        if (expectedHalfSpeed > 0) {
            locomotive.setRestrictedSpeed(expectedHalfSpeed);
        }

        locomotive.setModule(new MQTTLocomotiveModule(locomotive.getId() + "_MQTTLocomotiveModule", locomotive));
        if (locomotive.getFrontTag() != null) {
            Model.getTags().put(locomotive.getFrontTag().getDecUid(), locomotive.getFrontTag());
        }
        if (locomotive.getRearTag() != null) {
            Model.getTags().put(locomotive.getRearTag().getDecUid(), locomotive.getRearTag());
        }
        updateModelAndClose(Model.getLocomotives(), locomotive);
    }

    private void speedTest(int speed) {
        Timeline speedTester = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            MQTTConnectionService.publish(MQTTLocomotiveModule.getCommandTopicRoot() + "/" + textField.getText(), String.format("%s:%s", MQTTLocomotiveModule.STOP_CHANNEL, "0"));
        }));
        speedTester.setCycleCount(1);
        MQTTConnectionService.publish(MQTTLocomotiveModule.getCommandTopicRoot() + "/" + textField.getText(), String.format("%s:%s", MQTTLocomotiveModule.SET_SPEED_CHANNEL, String.format("%04d", speed)));
        speedTester.play();
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

    protected void toTagReader (LocomotiveRedactorController locomotiveRedactorController, TagType tagType) throws IOException {
        FXMLLoader loader = new FXMLLoader(TrackSectionRedactorController.class.getResource("TagReader.fxml"));
        Stage tagReader = new Stage();
        tagReader.setTitle("TagReader");
        tagReader.setScene(new Scene(loader.load(), 250, 170));
        tagReader.setResizable(false);
        TagReaderController controller = loader.getController();
        controller.init(locomotiveRedactorController, tagType);
        tagReader.initModality(Modality.WINDOW_MODAL);
        tagReader.initOwner(frontReadButton.getScene().getWindow());
        tagReader.show();
        tagReader.setOnCloseRequest(event -> {
            controller.closeReader();
        });
    }

}

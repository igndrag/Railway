package NATrain.quads.custom;

import NATrain.quads.AbstractQuad;
import NATrain.quads.QuadType;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.servos.Servo;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.Collections;
import java.util.List;

public class ServoQuad extends AbstractQuad {
    private Servo associatedServo;
    private Text positionOneText;
    private Text positionTwoText;
    private Button positionOneButton;
    private Button positionTwoButton;

    public ServoQuad (int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.SERVO_QUAD;
    }


    public Servo getAssociatedServo() {
        return associatedServo;
    }

    public void setAssociatedServo(Servo associatedServo) {
        this.associatedServo = associatedServo;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void refresh() {
        //do nothing
    }

    @Override
    public void paintView() {
        quadType = QuadType.SERVO_QUAD;
        positionOneButton = new Button();
        positionOneButton.setLayoutX(20);
        positionOneButton.setLayoutY(20);
        positionOneButton.setText("POS1");
       // positionOneText = new Text("pos1");

        quadView.getChildren().add(positionOneButton);

    }

    @Override
    public void activateListeners() {
        //do nothing
    }

    @Override
    public void deactivateListeners() {
        //do nothing
    }

    @Override
    public List<ControlAction> getAvailableActions() {
        return Collections.emptyList();
    }

    @Override
    public void select() {
        //do nothing
    }

    @Override
    public void unselect() {
        //do nothing
    }

    @Override
    public void updateFirstTrackView() {
        //do nothing
    }

    @Override
    public void updateSecondTrackView() {
        //do nothing
    }

    @Override
    public void updateSignalView() {
        //do nothing
    }

    @Override
    public void updateSwitchView() {
        //do nothing
    }
}

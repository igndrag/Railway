package NATrain.quads;

import NATrain.trackSideObjects.ControlAction;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Collections;
import java.util.List;

public class EmptyQuad extends AbstractQuad {

    public EmptyQuad(int x, int y) {
        super(x, y);
        paintView();
    }

    @Override
    public Group getView() {
        return quadView;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void refresh() { }

    @Override
    public void paintView() {
        quadType = QuadType.EMPTY_QUAD;
        Text text1 = new Text();
        text1.setX(20);
        text1.setY(50);
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setText("x = " + x + " y = " + y);
        quadView.getChildren().add(text1);
    }

    @Override
    public void activateListeners() {
        //do nothing
    }

    @Override
    public List<ControlAction> getAvailableActions() {
        return Collections.emptyList();
    }
}

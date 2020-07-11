package NATrain.quads;

import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Properties;

public class EmptyQuad extends AbstractQuad {


    public EmptyQuad(int x, int y) {
        super(x, y);
        Text text1 = new Text();
        text1.setX(20);
        text1.setY(50);
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setText("x = " + x + " y = " + y);
        quadView.getChildren().add(text1);
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
    public void refresh() {

    }


    @Override
    public String getId() {
        return null;
    }

    @Override
    public Properties getProperties() {
        return null;
    }
}

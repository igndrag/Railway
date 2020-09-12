package NATrain.quads;

import javafx.scene.Group;
import javafx.scene.text.Text;

public abstract class DemoQuad extends SignalQuad {

    public static Group train;
    private Text TVDS1name;
    private Text TVDS2name;
    private Text fromSignalName;
    private Text toSignalName;


    static {
        train = new Group();
        //paint train logo here
    }

    public DemoQuad(int x, int y) {
        super(x, y);
    }

}

package NATrain.trackSideObjects;
import java.io.Serializable;

public class Wagon extends AbstractMovableObject implements Serializable {
    static final long serialVersionUID = 1L;

    public Wagon(String id) {
        super(id);
    }

    @Override
    public String getModules() {
        return "";
    }
}

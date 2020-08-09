package NATrain.quads;

import NATrain.quads.configurableInterfaces.Configurable;
import javafx.scene.text.Text;


public abstract class BaseQuad extends AbstractQuad implements Configurable {

    protected Text descriptionLabel = new Text("");

    public BaseQuad(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void showDescription(boolean show) {
        descriptionLabel.setVisible(show);
    }

    @Override
    public boolean isDescriptionShown() {
        return descriptionLabel.isVisible();
    }
}

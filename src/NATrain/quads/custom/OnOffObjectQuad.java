package NATrain.quads.custom;

import NATrain.quads.BaseQuad;
import NATrain.quads.GlobalQuadType;
import NATrain.quads.QuadType;
import NATrain.quads.SimpleTrackQuad;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.customObjects.OnOffObject;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public abstract class OnOffObjectQuad extends SimpleTrackQuad {

    protected OnOffObject onOffObject;

    private static final List<ControlAction> availableActions = new ArrayList<>();

    static {
        availableActions.add(ControlAction.ON);
        availableActions.add(ControlAction.OFF);
    }

    public OnOffObjectQuad(int x, int y) {
        super(x, y);
        globalQuadType = GlobalQuadType.ON_OFF_QUAD;
    }

    public OnOffObject getOnOffObject() {
        return onOffObject;
    }


    public void setOnOffObject() {
        if (onOffObject == null || onOffObject == OnOffObject.EMPTY_ON_OFF_OBJECT) {
            this.onOffObject = null;
            descriptionLabel.setText("");
        } else {
            descriptionLabel.setText(onOffObject.getId());
        }
    }

    @Override
    public Object getCustomObject() {
        return onOffObject;
    }

    @Override
    public void setCustomObject(Object object) {
        this.onOffObject = (OnOffObject) object;
    }

    protected class OnOffObjectListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            refresh();
        }
    }
}
     

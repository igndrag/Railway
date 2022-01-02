package NATrain.quads.custom;

import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.quads.GlobalQuadType;
import NATrain.quads.SimpleTrackQuad;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.customObjects.Gate;
import NATrain.trackSideObjects.customObjects.GatePosition;
import javafx.scene.shape.Rectangle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public abstract class GateQuad extends SimpleTrackQuad {

    protected Rectangle leftDoorOpenedElement;
    protected Rectangle leftDoorClosedElement;
    protected Rectangle rightDoorOpenedElement;
    protected Rectangle rightDoorClosedElement;

    protected Gate gate = Gate.TEST_GATE;

    private static final List<ControlAction> availableActions = new ArrayList<>();

    static {
        availableActions.add(ControlAction.OPEN_GATE);
        availableActions.add(ControlAction.CLOSE_GATE);
    }

    public GateQuad(int x, int y) {
        super(x, y);
        globalQuadType = GlobalQuadType.GATE_QUAD;
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        if (gate == null || gate == Gate.EMPTY_GATE) {
            this.gate = null;
            leftDoorClosedElement.setFill(UNDEFINED_ELEMENT_COLOR);
            rightDoorClosedElement.setFill(UNDEFINED_ELEMENT_COLOR);
            rightDoorOpenedElement.setFill(UNDEFINED_ELEMENT_COLOR);
            leftDoorOpenedElement.setFill(UNDEFINED_ELEMENT_COLOR);
        } else {
            this.gate = gate;
            leftDoorClosedElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
            rightDoorClosedElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
            rightDoorOpenedElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
            leftDoorOpenedElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
        }
    }

    @Override
    public void activateListeners() {
        super.activateListeners();
        if (gate != null) {
            PropertyChangeListener gateListener = new GateListener();
            quadListeners.put(gate, gateListener);
            gate.addPropertyChangeListener(gateListener);
        }
    }

    @Override
    public void deactivateListeners() {
        super.deactivateListeners();
    }

    private void updateGateView() {
        if (gate != null) {
            if (gate.getPosition() == GatePosition.OPENED) {
                leftDoorOpenedElement.setVisible(true);
                rightDoorOpenedElement.setVisible(true);
                leftDoorClosedElement.setVisible(false);
                rightDoorClosedElement.setVisible(false);
            } else {
                leftDoorOpenedElement.setVisible(false);
                rightDoorOpenedElement.setVisible(false);
                leftDoorClosedElement.setVisible(true);
                rightDoorClosedElement.setVisible(true);
            }
        }
    }

    @Override
    public List<ControlAction> getAvailableActions() {
        return availableActions;
    }

    @Override
    public void refresh() {
        updateFirstTrackView();
        updateGateView();
        if (gate == null) {
            leftDoorClosedElement.setFill(UNDEFINED_ELEMENT_COLOR);
            rightDoorClosedElement.setFill(UNDEFINED_ELEMENT_COLOR);
            rightDoorOpenedElement.setFill(UNDEFINED_ELEMENT_COLOR);
            leftDoorOpenedElement.setFill(UNDEFINED_ELEMENT_COLOR); }
        else {
            if (WorkPlaceController.isActiveMode()) {
                leftDoorClosedElement.setFill(DEFAULT_CUSTOM_OBJECT_COLOR);
                rightDoorClosedElement.setFill(DEFAULT_CUSTOM_OBJECT_COLOR);
                rightDoorOpenedElement.setFill(DEFAULT_CUSTOM_OBJECT_COLOR);
                leftDoorOpenedElement.setFill(DEFAULT_CUSTOM_OBJECT_COLOR);
            } else {
                leftDoorClosedElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
                rightDoorClosedElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
                rightDoorOpenedElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
                leftDoorOpenedElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
            }
        }
    }

    @Override
    public Object getCustomObject() {
        return gate;
    }

    @Override
    public void setCustomObject(Object object) {
        this.gate = (Gate) object;
    }

    private class GateListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            refresh();
        }
    }
}

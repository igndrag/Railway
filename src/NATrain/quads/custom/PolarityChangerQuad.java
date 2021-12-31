package NATrain.quads.custom;

import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.quads.SimpleTrackQuad;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.customObjects.PolarityChanger;
import NATrain.trackSideObjects.switches.SwitchState;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.trackSideObjects.trackSections.TrackSectionState;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

public abstract class PolarityChangerQuad extends SimpleTrackQuad {

    protected PolarityChanger polarityChanger = PolarityChanger.TEST_POLARITY_CHANGER;

    protected Rectangle leftRailMinusElement;
    protected Polygon leftRailPlusElement;
    protected Rectangle rightRailMinusElement;
    protected Polygon rightRailPlusElement;

    public PolarityChangerQuad(int x, int y) {
        super(x, y);
    }

    public PolarityChanger getPolarityChanger() {
        return polarityChanger;
    }

    public void setPolarityChanger(PolarityChanger polarityChanger) {
        this.polarityChanger = polarityChanger;
        if (polarityChanger != null && polarityChanger != PolarityChanger.EMPTY_POLARITY_CHANGER) {
            leftRailMinusElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
            leftRailPlusElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
            rightRailMinusElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
            rightRailPlusElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
        } else {
            leftRailMinusElement.setFill(UNDEFINED_ELEMENT_COLOR);
            leftRailPlusElement.setFill(UNDEFINED_ELEMENT_COLOR);
            rightRailMinusElement.setFill(UNDEFINED_ELEMENT_COLOR);
            rightRailPlusElement.setFill(UNDEFINED_ELEMENT_COLOR);
        }
    }

    private void checkPolarity() {
        if (polarityChanger != null && associatedSwitch != null) {
            if (associatedSwitch.getSwitchState() == SwitchState.PLUS) {
                polarityChanger.normalPolarityOn();
            } else if (associatedSwitch.getSwitchState() == SwitchState.MINUS) {
                polarityChanger.reversedPolarityOn();
            }
            updatePolarityChangerView();
        }
    }

    @Override
    public void activateListeners() {
        checkPolarity();
        super.activateListeners();
        if (polarityChanger != null && polarityChanger.getAssociatedSwitch() != null) {
            PropertyChangeListener switchListener = new SwitchListener();
            quadListeners.put(polarityChanger.getAssociatedSwitch(), switchListener);
            polarityChanger.getAssociatedSwitch().addPropertyChangeListener(switchListener);

            TrackSection changerSwitchSection = polarityChanger.getAssociatedSwitch().getTrackSection();
            if (changerSwitchSection != null) {
                PropertyChangeListener switchStateChanger = new SwitchStateChanger();
                quadListeners.put(changerSwitchSection, switchStateChanger);
                changerSwitchSection.addPropertyChangeListener(switchStateChanger);
            }
        }
    }

    @Override
    public void deactivateListeners() {
        super.deactivateListeners();
    }

    private void updatePolarityChangerView() {
        if (polarityChanger != null) {
            switch (polarityChanger.getPolarity()) {
                case NORMAL:
                    rightRailPlusElement.setVisible(true);
                    rightRailPlusElement.setFill(Color.RED);
                    leftRailMinusElement.setVisible(true);
                    leftRailMinusElement.setFill(Color.BLUE);
                    leftRailPlusElement.setVisible(false);
                    rightRailMinusElement.setVisible(false);
                    break;
                case REVERSED:
                    rightRailPlusElement.setVisible(false);
                    leftRailMinusElement.setVisible(false);
                    leftRailPlusElement.setVisible(true);
                    leftRailPlusElement.setFill(Color.RED);
                    rightRailMinusElement.setVisible(true);
                    rightRailMinusElement.setFill(Color.BLUE);
                    break;
                case OFF:
                    rightRailPlusElement.setFill(Color.GRAY);
                    leftRailMinusElement.setFill(Color.GRAY);
                    leftRailPlusElement.setFill(Color.GRAY);
                    rightRailMinusElement.setFill(Color.GRAY);
                    break;
            }
        }
    }

    @Override
    public List<ControlAction> getAvailableActions() {
        return Collections.emptyList();
    }

    @Override
    public void refresh() {
        updateFirstTrackView();
        updatePolarityChangerView();
        if (polarityChanger == null) {
            leftRailPlusElement.setFill(UNDEFINED_ELEMENT_COLOR);
            leftRailMinusElement.setFill(UNDEFINED_ELEMENT_COLOR);
            rightRailPlusElement.setFill(UNDEFINED_ELEMENT_COLOR);
            rightRailMinusElement.setFill(UNDEFINED_ELEMENT_COLOR); }
        else {
            if (WorkPlaceController.isActiveMode()) {
                leftRailPlusElement.setFill(DEFAULT_CUSTOM_OBJECT_COLOR);
                leftRailMinusElement.setFill(DEFAULT_CUSTOM_OBJECT_COLOR);
                rightRailPlusElement.setFill(DEFAULT_CUSTOM_OBJECT_COLOR);
                rightRailMinusElement.setFill(DEFAULT_CUSTOM_OBJECT_COLOR);
            } else {
                leftRailPlusElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
                leftRailMinusElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
                rightRailPlusElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
                rightRailMinusElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
            }
        }
    }

    private class SwitchListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            checkPolarity();
        }
    }

    private class SwitchStateChanger implements PropertyChangeListener { //add to associated switch track section
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (firstAssociatedTrack.getVacancyState() == TrackSectionState.OCCUPIED
                && polarityChanger.getAssociatedSwitch().getTrackSection().getVacancyState() == TrackSectionState.FREE) {
                associatedSwitch.changePosition();
            }
        }
    }
}

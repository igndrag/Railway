package NATrain.quads.custom;

import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.model.Model;
import NATrain.quads.GlobalQuadType;
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

    private boolean leftSectionOccupationFixed = false;
    private boolean rightSectionOccupationFixed = false;
    private boolean locoOnChangerSection = false;

    public PolarityChangerQuad(int x, int y) {
        super(x, y);
        globalQuadType = GlobalQuadType.POLARITY_CHANGER_QUAD;
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
        if (polarityChanger != null) {
            switch (polarityChanger.getType()) {
                case SWITCH_POLARITY_CHANGER:
                    if (polarityChanger.getAssociatedSwitch() != null) {
                        if (polarityChanger.getAssociatedSwitch().getSwitchState() == SwitchState.PLUS) {
                            polarityChanger.normalPolarityOn();
                        } else if (polarityChanger.getAssociatedSwitch().getSwitchState() == SwitchState.MINUS) {
                            polarityChanger.reversedPolarityOn();
                        }
                    }
                    break;
                case SECTION_POLARITY_CHANGER:
                    if (firstAssociatedTrack.getVacancyState() == TrackSectionState.FREE) {
                        if ((polarityChanger.getLeftTrackSection().getVacancyState() == TrackSectionState.FREE
                                && polarityChanger.getRightTrackSection().getVacancyState() == TrackSectionState.FREE)
                                || (polarityChanger.getLeftTrackSection().getVacancyState() == TrackSectionState.OCCUPIED
                                && polarityChanger.getRightTrackSection().getVacancyState() == TrackSectionState.OCCUPIED)) {
                                polarityChanger.off();
                        } else if (polarityChanger.getLeftTrackSection().getVacancyState() == TrackSectionState.OCCUPIED) {
                            polarityChanger.normalPolarityOn();
                        } else if (polarityChanger.getRightTrackSection().getVacancyState() == TrackSectionState.OCCUPIED) {
                            polarityChanger.reversedPolarityOn();
                        }
                    } else {
                        if (leftSectionOccupationFixed
                            && polarityChanger.getRightTrackSection().getVacancyState() == TrackSectionState.FREE) {
                            polarityChanger.reversedPolarityOn();
                            leftSectionOccupationFixed = false;
                        }
                        if (rightSectionOccupationFixed
                            && polarityChanger.getRightTrackSection().getVacancyState() == TrackSectionState.FREE) {
                            polarityChanger.normalPolarityOn();
                            rightSectionOccupationFixed = false;
                        }
                    }
                    break;
                case LOCO_POLARITY_CHANGER:
                    if (!locoOnChangerSection) {
                        if (leftSectionOccupationFixed && !rightSectionOccupationFixed) {
                            polarityChanger.normalPolarityOn();
                        } else if (rightSectionOccupationFixed && !leftSectionOccupationFixed) {
                            polarityChanger.reversedPolarityOn();
                        } else {
                            polarityChanger.off();
                        }
                    } else {
                        polarityChanger.changePolarity();
                    }
            }
            updatePolarityChangerView();
        }
    }

    @Override
    public void activateListeners() {
        super.activateListeners();
        if (polarityChanger != null) {
            switch (polarityChanger.getType()) {
                case SWITCH_POLARITY_CHANGER:
                    if (polarityChanger.getAssociatedSwitch() != null) {
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
                    break;
                case SECTION_POLARITY_CHANGER:
                    if (firstAssociatedTrack != null
                            && firstAssociatedTrack != TrackSection.EMPTY_TRACK_SECTION
                            && polarityChanger.getLeftTrackSection() != null
                            && polarityChanger.getRightTrackSection() != null) {
                        if (polarityChanger.getLeftTrackSection().getVacancyState() == TrackSectionState.OCCUPIED) {
                            leftSectionOccupationFixed = true;
                        }
                        if (polarityChanger.getRightTrackSection().getVacancyState() == TrackSectionState.OCCUPIED) {
                            rightSectionOccupationFixed = true;
                        }
                        PropertyChangeListener leftBorderSectionListener = new LeftBorderSectionsListener();
                        polarityChanger.getLeftTrackSection().addPropertyChangeListener(leftBorderSectionListener);
                        quadListeners.put(polarityChanger.getLeftTrackSection(), leftBorderSectionListener);
                        PropertyChangeListener rightBorderSectionListener = new RightBorderSectionsListener();
                        polarityChanger.getRightTrackSection().addPropertyChangeListener(rightBorderSectionListener);
                        quadListeners.put(polarityChanger.getRightTrackSection(), rightBorderSectionListener);
                    }
                    break;
                case LOCO_POLARITY_CHANGER:
                    Model.getLocomotives().values().forEach(locomotive -> {
                        if (locomotive.isRFID()) {
                            PropertyChangeListener listener = new LocoPositionListener();
                            locomotive.addPropertyChangeListener(listener);
                            quadListeners.put(locomotive, listener);
                        }
                    });
                    break;
            }
        }
        checkPolarity();
    }

    @Override
    public void deactivateListeners() {
        super.deactivateListeners();
        leftSectionOccupationFixed = false;
        rightSectionOccupationFixed = false;
        locoOnChangerSection = false;
    }

    private void updatePolarityChangerView() {
        if (polarityChanger != null) {
            if (!WorkPlaceController.isActiveMode()) {
                leftRailPlusElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
                leftRailMinusElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
                rightRailPlusElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
                rightRailMinusElement.setFill(CONFIGURED_CUSTOM_OBJECT_COLOR);
            } else {
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
        } else {
            leftRailPlusElement.setFill(UNDEFINED_ELEMENT_COLOR);
            leftRailMinusElement.setFill(UNDEFINED_ELEMENT_COLOR);
            rightRailPlusElement.setFill(UNDEFINED_ELEMENT_COLOR);
            rightRailMinusElement.setFill(UNDEFINED_ELEMENT_COLOR);
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
    }

    private class SwitchListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            checkPolarity();
        }
    }

    private class SwitchStateChanger implements PropertyChangeListener { //add to associated switch trackline section
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (firstAssociatedTrack.getVacancyState() == TrackSectionState.OCCUPIED
                    && polarityChanger.getAssociatedSwitch().getTrackSection().getVacancyState() == TrackSectionState.FREE) {
                associatedSwitch.changePosition();
            }
        }
    }

    private class LeftBorderSectionsListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getNewValue() == TrackSectionState.OCCUPIED) {
                leftSectionOccupationFixed = true;
            } else if (evt.getNewValue() == TrackSectionState.FREE && firstAssociatedTrack.getVacancyState() == TrackSectionState.FREE) {
                leftSectionOccupationFixed = false;
            }
            checkPolarity();
        }
    }

    private class RightBorderSectionsListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getNewValue() == TrackSectionState.OCCUPIED) {
                rightSectionOccupationFixed = true;
            } else if (evt.getNewValue() == TrackSectionState.FREE && firstAssociatedTrack.getVacancyState() == TrackSectionState.FREE) {
                rightSectionOccupationFixed = false;
            }
            checkPolarity();
        }
    }

    private class LocoPositionListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getNewValue() == polarityChanger.getLeftTrackSection()) {
                leftSectionOccupationFixed = true;
            }
            if (evt.getNewValue() == polarityChanger.getRightTrackSection()) {
                rightSectionOccupationFixed = true;
            }
            if (evt.getNewValue() == firstAssociatedTrack) {
                if (evt.getOldValue() == polarityChanger.getLeftTrackSection()) {
                    leftSectionOccupationFixed = false;
                    locoOnChangerSection = true;
                }
                if (evt.getOldValue() == polarityChanger.getRightTrackSection()) {
                    rightSectionOccupationFixed = false;
                    locoOnChangerSection = true;
                }
            } else {
                locoOnChangerSection = false;
            }
            checkPolarity();
        }
    }

    @Override
    public Object getCustomObject() {
        return polarityChanger;
    }

    @Override
    public void setCustomObject(Object object) {
        this.polarityChanger = (PolarityChanger) object;
    }
}

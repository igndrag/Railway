package NATrain.quads;

import NATrain.UI.workPlace.Blinker;
import NATrain.routes.Track;
import NATrain.routes.TrackBlockSection;
import NATrain.routes.TrackDirection;
import NATrain.trackSideObjects.*;
import NATrain.trackSideObjects.signals.GlobalSignalState;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalState;
import NATrain.trackSideObjects.trackSections.TrackSectionState;
import javafx.scene.text.Text;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

public abstract class BlockingBaseQuad extends AbstractQuad {
    protected Track track = Track.EMPTY_TRACK;
    protected TrackBlockSection firstBlockSection = TrackBlockSection.EMPTY_BLOCK_SECTION;
    protected TrackBlockSection secondBlockSection = TrackBlockSection.EMPTY_BLOCK_SECTION;
    protected Signal firstSignal = Signal.EMPTY_SIGNAL;
    protected Signal secondSignal = Signal.EMPTY_SIGNAL;
    protected Text blockSectionName; //creating in factory
    protected Boolean reversedSignalView = false;
    protected Text firstSignalLabel;
    protected Text secondSignalLabel;

    public BlockingBaseQuad(int x, int y) {
        super(x, y);
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public TrackBlockSection getFirstBlockSection() {
        return firstBlockSection;
    }

    public void setFirstBlockSection(TrackBlockSection firstBlockSection) {
        this.firstBlockSection = firstBlockSection;
    }

    public TrackBlockSection getSecondBlockSection() {
        return secondBlockSection;
    }

    public void setSecondBlockSection(TrackBlockSection secondBlockSection) {
        this.secondBlockSection = secondBlockSection;
    }

    public Signal getFirstSignal() {
        return firstSignal;
    }

    public void setFirstSignal(Signal firstSignal) {
        this.firstSignal = firstSignal;
        if (firstSignalLabel != null) {
            this.firstSignalLabel.setText(firstSignal.getId());
        }
    }

    public Signal getSecondSignal() {
        return secondSignal;
    }

    public void setSecondSignal(Signal secondSignal) {
        this.secondSignal = secondSignal;
        if (secondSignalLabel != null) {
            this.secondSignalLabel.setText(secondSignal.getId());
        }
    }

    @Override
    public void updateSwitchView() {
        //do nothing
    }

    @Override
    public void activateListeners() {
        if (this instanceof BlockingSignalQuad) {
            track.getSignalQuads().add(this);
        }
        activateBlockSectionsListeners();
        activateSignalStateAutoselectors();
    }

    public void activateSignalStateAutoselectors() {
        if (firstSignal != Signal.EMPTY_SIGNAL) {
            int index = track.getBlockSections().indexOf(firstBlockSection); //find index TVDS1
            TrackBlockSection TVDS1 = firstBlockSection;
            TrackBlockSection TVDS2;
            HashSet<TracksideObject> objectsForListening = new HashSet<>();
            PropertyChangeListener listener;
            if (reversedSignalView) {// FS - ND
                if (!firstBlockSection.isLastInNormalDirection()) { //not last in normal direction
                    TVDS2 = track.getBlockSections().get(index + 1);
                    listener = new TrackSignalStateAutoselector(TVDS1, TVDS2, firstSignal, TrackDirection.NORMAL);
                    TVDS1.addPropertyChangeListener(listener);
                    TVDS2.addPropertyChangeListener(listener);
                    objectsForListening.add(TVDS1);
                    objectsForListening.add(TVDS2);
                } else {
                    listener = new LastTrackSignalStateAutoselector(TVDS1, firstSignal, this, TrackDirection.NORMAL);
                    TVDS1.addPropertyChangeListener(listener);
                    track.getNormalDirectionArrivalSignal().addPropertyChangeListener(listener);
                    objectsForListening.add(TVDS1);
                    objectsForListening.add(track.getNormalDirectionArrivalSignal());
                }
            } else { // FS - RD
                if (!firstBlockSection.isLastInReverseDirection()) { //not last in reversed direction
                    TVDS2 = track.getBlockSections().get(index - 1);
                    listener = new TrackSignalStateAutoselector(TVDS1, TVDS2, firstSignal, TrackDirection.REVERSED);
                    TVDS1.addPropertyChangeListener(listener);
                    TVDS2.addPropertyChangeListener(listener);
                    objectsForListening.add(TVDS1);
                    objectsForListening.add(TVDS2);
                } else {
                    listener = new LastTrackSignalStateAutoselector(TVDS1, firstSignal, this, TrackDirection.REVERSED);
                    TVDS1.addPropertyChangeListener(listener);
                    track.getReversedDirectionArrivalSignal().addPropertyChangeListener(listener);
                    objectsForListening.add(TVDS1);
                    objectsForListening.add(track.getReversedDirectionArrivalSignal());
                }
            }
            track.getActiveSignalListeners().put(listener, objectsForListening);
            ((SignalStateAutoselector) listener).autoselectSignalState();
        }

        if (secondSignal != Signal.EMPTY_SIGNAL) {
            int index = track.getBlockSections().indexOf(secondBlockSection); //find index TVDS1
            TrackBlockSection TVDS1 = secondBlockSection;
            TrackBlockSection TVDS2;
            HashSet<TracksideObject> objectsForListening = new HashSet<>();
            PropertyChangeListener listener;
            if (reversedSignalView) {// SS - RD
                if (!secondBlockSection.isLastInReverseDirection()) { //not last in reversed direction
                    TVDS2 = track.getBlockSections().get(index - 1);
                    listener = new TrackSignalStateAutoselector(TVDS1, TVDS2, secondSignal, TrackDirection.REVERSED);
                    TVDS1.addPropertyChangeListener(listener);
                    TVDS2.addPropertyChangeListener(listener);
                    objectsForListening.add(TVDS1);
                    objectsForListening.add(TVDS2);
                } else {
                    listener = new LastTrackSignalStateAutoselector(TVDS1, secondSignal, this, TrackDirection.REVERSED);
                    TVDS1.addPropertyChangeListener(listener);
                    track.getReversedDirectionArrivalSignal().addPropertyChangeListener(listener);
                    objectsForListening.add(TVDS1);
                    objectsForListening.add(track.getReversedDirectionArrivalSignal());
                }
            } else { //SS - ND
                if (!secondBlockSection.isLastInNormalDirection()) { //not last in reversed direction
                    TVDS2 = track.getBlockSections().get(index + 1);
                    listener = new TrackSignalStateAutoselector(TVDS1, TVDS2, secondSignal, TrackDirection.NORMAL);
                    TVDS1.addPropertyChangeListener(listener);
                    TVDS2.addPropertyChangeListener(listener);
                    objectsForListening.add(TVDS1);
                    objectsForListening.add(TVDS2);
                } else {
                    listener = new LastTrackSignalStateAutoselector(TVDS1, secondSignal, this, TrackDirection.NORMAL);
                    TVDS1.addPropertyChangeListener(listener);
                    track.getNormalDirectionArrivalSignal().addPropertyChangeListener(listener);
                    objectsForListening.add(TVDS1);
                    objectsForListening.add(track.getNormalDirectionArrivalSignal());
                }
            }
            track.getActiveSignalListeners().put(listener, objectsForListening);
            ((SignalStateAutoselector) listener).autoselectSignalState();
        }
    }

    private void activateBlockSectionsListeners() {
        if (firstBlockSection != TrackBlockSection.EMPTY_BLOCK_SECTION) {
            firstBlockSection.addPropertyChangeListener(new FirstTrackViewUpdater());
        }
        if (secondBlockSection != TrackBlockSection.EMPTY_BLOCK_SECTION) {
            secondBlockSection.addPropertyChangeListener(new SecondTrackViewUpdater());
        }
    }

    private class TrackSignalStateAutoselector implements PropertyChangeListener, SignalStateAutoselector {
        private TrackBlockSection TVDS1;
        private TrackBlockSection TVDS2;
        private Signal trackSignal;
        private TrackDirection trackDirection;

        public TrackSignalStateAutoselector(TrackBlockSection TVDS1, TrackBlockSection TVDS2, Signal trackSignal, TrackDirection trackDirection) {
            this.TVDS1 = TVDS1;
            this.TVDS2 = TVDS2;
            this.trackSignal = trackSignal;
            this.trackDirection = trackDirection;
        }

        @Override
        public void autoselectSignalState() {
            if (trackDirection == track.getTrackDirection()) {
                if (TVDS1.getVacancyState() == TrackSectionState.OCCUPIED) {
                    trackSignal.setSignalState(SignalState.RED);
                } else if (TVDS2.getVacancyState() == TrackSectionState.OCCUPIED) {
                    trackSignal.setSignalState(SignalState.YELLOW);
                } else if (TVDS1.getVacancyState() == TrackSectionState.FREE &&
                        TVDS2.getVacancyState() == TrackSectionState.FREE) {
                    trackSignal.setSignalState(SignalState.GREEN);
                }
            } else {
                trackSignal.setSignalState(SignalState.NOT_LIGHT);
            }
            updateSignalView();
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            autoselectSignalState();
        }
    }

    private class LastTrackSignalStateAutoselector implements PropertyChangeListener, SignalStateAutoselector {
        private TrackBlockSection TVDS1;
        private Signal arrivalSignal;
        private Signal trackSignal;
        private TrackDirection trackDirection;
        Quad quad;

        public LastTrackSignalStateAutoselector(TrackBlockSection TVDS1, Signal trackSignal, Quad quad, TrackDirection trackDirection) {
            this.quad = quad;
            this.TVDS1 = TVDS1;
            this.trackDirection = trackDirection;
            if (trackDirection == TrackDirection.NORMAL) {
                this.arrivalSignal = track.getNormalDirectionArrivalSignal();
            } else {
                this.arrivalSignal = track.getReversedDirectionArrivalSignal();
            }
            this.trackSignal = trackSignal;
        }

        @Override
        public void autoselectSignalState() {
            if (trackDirection == track.getTrackDirection()) {
                if (TVDS1.getVacancyState() == TrackSectionState.OCCUPIED) {
                    trackSignal.setSignalState(SignalState.RED);
                } else if (arrivalSignal.getGlobalStatus() == GlobalSignalState.CLOSED) {
                    trackSignal.setSignalState(SignalState.YELLOW);
                } else if (arrivalSignal.getGlobalStatus() == GlobalSignalState.OPENED_ON_RESTRICTED_SPEED) {
                    trackSignal.setSignalState(SignalState.BLINKED_YELLOW);
                } else if (TVDS1.getVacancyState() == TrackSectionState.FREE
                        && arrivalSignal.getGlobalStatus() == GlobalSignalState.OPENED) {
                    trackSignal.setSignalState(SignalState.GREEN);
                }
                if (trackSignal.getSignalState().isBlinking()) {
                    Blinker.registerQuad(quad);
                } else {
                    Blinker.unregisterQuad(quad);
                }
            } else {
                trackSignal.setSignalState(SignalState.NOT_LIGHT);
            }
            updateSignalView();
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            autoselectSignalState();
        }
    }

    public void showBlockSectionName(boolean show) {
        if (blockSectionName != null) {
            blockSectionName.setVisible(show);
        }
    }

    public Text getBlockSectionName() {
        return blockSectionName;
    }

    public Boolean getReversedSignalView() {
        return reversedSignalView;
    }

    public void setReversedSignalView(Boolean reversedSignalView) {
        this.reversedSignalView = reversedSignalView;
    }
}

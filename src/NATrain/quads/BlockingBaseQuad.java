package NATrain.quads;

import NATrain.UI.workPlace.Blinker;
import NATrain.routes.Track;
import NATrain.routes.TrackBlockSection;
import NATrain.routes.TrackDirection;
import NATrain.trackSideObjects.GlobalSignalState;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.SignalState;
import NATrain.trackSideObjects.TrackSectionState;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
        if (firstBlockSection != TrackBlockSection.EMPTY_BLOCK_SECTION) {
            firstBlockSection.getSection().addPropertyChangeListener(new FirstTrackViewUpdater());
        }
        if (secondBlockSection != TrackBlockSection.EMPTY_BLOCK_SECTION) {
            secondBlockSection.getSection().addPropertyChangeListener(new SecondTrackViewUpdater());
        }
        if (firstSignal != Signal.EMPTY_SIGNAL) {
            int index = track.getBlockSections().indexOf(firstBlockSection); //find index TVDS1
            TrackBlockSection TVDS1 = firstBlockSection;
            TrackBlockSection TVDS2;
            PropertyChangeListener listener;
            if (reversedSignalView) {// FS - ND
                if (!firstBlockSection.isLastInNormalDirection()) { //not last in normal direction
                    TVDS2 = track.getBlockSections().get(index + 1);
                    listener = new TrackSignalStateAutoselector(TVDS1, TVDS2, firstSignal);
                    TVDS1.getSection().addPropertyChangeListener(listener);
                    TVDS2.getSection().addPropertyChangeListener(listener);
                } else {
                    listener = new LastTrackSignalStateAutoselector(TVDS1, firstSignal, this);
                    TVDS1.getSection().addPropertyChangeListener(listener);
                    track.getNormalDirectionArrivalSignal().addPropertyChangeListener(listener);
                }

            } else { // FS - RD

            }
        }

        if (secondSignal != Signal.EMPTY_SIGNAL) {

        }


    }

    private class TrackSignalStateAutoselector implements PropertyChangeListener {
        private TrackBlockSection TVDS1;
        private TrackBlockSection TVDS2;
        private Signal trackSignal;

        public TrackSignalStateAutoselector(TrackBlockSection TVDS1, TrackBlockSection TVDS2, Signal trackSignal) {
            this.TVDS1 = TVDS1;
            this.TVDS2 = TVDS2;
            this.trackSignal = trackSignal;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (TVDS1.getSection().getVacancyState() == TrackSectionState.OCCUPIED) {
                trackSignal.setSignalState(SignalState.RED);
            } else if (TVDS2.getSection().getVacancyState() == TrackSectionState.OCCUPIED) {
                trackSignal.setSignalState(SignalState.YELLOW);
            } else if (TVDS1.getSection().getVacancyState() == TrackSectionState.FREE &&
                    TVDS2.getSection().getVacancyState() == TrackSectionState.FREE) {
                trackSignal.setSignalState(SignalState.GREEN);
            }
            updateSignalView();
        }
    }

    private class LastTrackSignalStateAutoselector implements PropertyChangeListener {
        private TrackBlockSection TVDS1;
        private Signal arrivalSignal;
        private Signal trackSignal;
        Quad quad;

        public LastTrackSignalStateAutoselector(TrackBlockSection TVDS1, Signal trackSignal, Quad quad) {
            this.quad = quad;
            this.TVDS1 = TVDS1;
            if (track.getTrackDirection() == TrackDirection.NORMAL) {
                this.arrivalSignal = track.getNormalDirectionArrivalSignal();
            } else {
                this.arrivalSignal = track.getReversedDirectionArrivalSignal();
            }
            this.trackSignal = trackSignal;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (TVDS1.getSection().getVacancyState() == TrackSectionState.OCCUPIED) {
                trackSignal.setSignalState(SignalState.RED);
            } else if (arrivalSignal.getGlobalStatus() == GlobalSignalState.CLOSED) {
                trackSignal.setSignalState(SignalState.YELLOW);
            } else if (arrivalSignal.getGlobalStatus() == GlobalSignalState.OPENED_ON_RESTRICTED_SPEED) {
                trackSignal.setSignalState(SignalState.BLINKED_YELLOW);
            } else if (TVDS1.getSection().getVacancyState() == TrackSectionState.FREE
                    && arrivalSignal.getGlobalStatus() == GlobalSignalState.OPENED) {
                trackSignal.setSignalState(SignalState.GREEN);
            }
            if (trackSignal.getSignalState().isBlinking()) {
                Blinker.registerQuad(quad);
            } else {
                Blinker.unregisterQuad(quad);
            }
            updateSignalView();
        }
    }

    public void showBlockSectionName(boolean show) {
        blockSectionName.setVisible(show);
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

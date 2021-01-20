package NATrain.routes;

import NATrain.UI.NavigatorFxController;
import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.quads.Quad;
import NATrain.trackSideObjects.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Track implements Serializable {

    static final long serialVersionUID = 1L;
    public static final Track EMPTY_TRACK = new Track("none");
    private transient Map<PropertyChangeListener, Set<TracksideObject>> activeListeners = new HashMap<>();

    private String id;
    private TrackDirection trackDirection = TrackDirection.NORMAL;
    private boolean bidirectional = false;
    private final List<TrackBlockSection> blockSections = new CopyOnWriteArrayList<>();
    private TrackBlockingType trackBlockingType = TrackBlockingType.AUTOMATIC_THREE_SIGNAL_BLOCKING;
    private Signal normalDirectionArrivalSignal = Signal.EMPTY_SIGNAL;
    private Signal reversedDirectionArrivalSignal = Signal.EMPTY_SIGNAL;

    public Track(String id) {
        this.id = id;
    }

    public TrackDirection getTrackDirection() {
        return trackDirection;
    }

    public List<TrackBlockSection> getBlockSections() {
        return blockSections;
    }

    public Map<PropertyChangeListener, Set<TracksideObject>> getActiveListeners() {
        return activeListeners;
    }

    public void setTrackDirection(TrackDirection trackDirection) {
        //TODO make property change support for recreate listeners for BSQs
        if (bidirectional) {
            if (isAllBlockSectionsFree()) {
                this.trackDirection = trackDirection;
            } else {
                //WorkPlaceController.getActiveController().log(String.format("Track direction change of %s is impossible. Track isn't free.", this.id));
            }
        } else {
            System.out.println("Try to set direction for specialized track");
        }
    }

    public void setBidirectional(boolean bidirectional) {
        this.bidirectional = bidirectional;
    }

    public boolean isBidirectional() {
        return bidirectional;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBlockSectionCount() {
        return blockSections.size();
    }

    public TrackBlockingType getTrackBlockingType() {
        return trackBlockingType;
    }

    public void setTrackBlockingType(TrackBlockingType trackBlockingType) {
        this.trackBlockingType = trackBlockingType;
    }

    public Signal getNormalDirectionArrivalSignal() {
        return normalDirectionArrivalSignal;
    }

    public void setNormalDirectionArrivalSignal(Signal normalDirectionArrivalSignal) {
        this.normalDirectionArrivalSignal = normalDirectionArrivalSignal;
    }

    public Signal getReversedDirectionArrivalSignal() {
        return reversedDirectionArrivalSignal;
    }

    public void setReversedDirectionArrivalSignal(Signal reversedDirectionArrivalSignal) {
        this.reversedDirectionArrivalSignal = reversedDirectionArrivalSignal;
    }

    public boolean isAllBlockSectionsFree() {
        for (TrackBlockSection blockSection : blockSections) {
            if (blockSection.getSection().getVacancyState() == TrackSectionState.OCCUPIED)
                return false;
        }
        return true;
    }

    public void deactivateSignalListeners() {
        activeListeners.forEach((listener, objects) -> {
            objects.forEach(object -> {
                object.removePropertyChangeListener(listener);
            });
        });
        getBlockSections().stream().map(TrackBlockSection::getNormalDirectionSignal).forEach(signal -> {signal.setSignalState(SignalState.NOT_LIGHT);});
        getBlockSections().stream().map(TrackBlockSection::getReversedDirectionSignal).forEach(signal -> {signal.setSignalState(SignalState.NOT_LIGHT);});
        activeListeners.clear();
    }

    @Override
    public String toString() {
        return id;
    }
}

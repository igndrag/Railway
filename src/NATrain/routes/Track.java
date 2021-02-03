package NATrain.routes;

import NATrain.quads.BlockingBaseQuad;
import NATrain.trackSideObjects.*;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Track implements Serializable {

    static final long serialVersionUID = 1L;
    public static final Track EMPTY_TRACK = new Track("none");
    private transient Map<PropertyChangeListener, Set<TracksideObject>> activeSignalListeners = new HashMap<>();
    private transient List<BlockingBaseQuad> signalQuads = new ArrayList<>();

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

    public Map<PropertyChangeListener, Set<TracksideObject>> getActiveSignalListeners() {
        return activeSignalListeners;
    }

    public void setActiveSignalListeners(Map<PropertyChangeListener, Set<TracksideObject>> activeSignalListeners) {
        this.activeSignalListeners = activeSignalListeners;
    }

    public List<BlockingBaseQuad> getSignalQuads() {
        return signalQuads;
    }

    public void setSignalQuads(List<BlockingBaseQuad> signalQuads) {
        this.signalQuads = signalQuads;
    }

    public void setTrackDirection(TrackDirection trackDirection) {
        //TODO make property change support for recreate listeners for BSQs
        if (bidirectional) {
            if (isAllBlockSectionsFree()) {
                deactivateSignalListeners();
                this.trackDirection = trackDirection;
                signalQuads.forEach(quad -> {
                    quad.activateSignalStateAutoselectors();
                    quad.refresh();
                });

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
        activeSignalListeners.forEach((listener, objects) -> {
            objects.forEach(object -> {
                object.removePropertyChangeListener(listener);
            });
        });
        getBlockSections().stream().map(TrackBlockSection::getNormalDirectionSignal).forEach(signal -> {signal.setSignalState(SignalState.NOT_LIGHT);});
        getBlockSections().stream().map(TrackBlockSection::getReversedDirectionSignal).forEach(signal -> {signal.setSignalState(SignalState.NOT_LIGHT);});
        activeSignalListeners.clear();
    }

    public List<TrackSection> getTrackSections() {
        return blockSections.stream().map(TrackBlockSection::getSection).collect(Collectors.toList());
    }

    public List<Signal> getSignals() {
        List<Signal> result = new ArrayList<>();
        for (TrackBlockSection blockSection : blockSections) {
            Signal normalDirectionSignal = blockSection.getNormalDirectionSignal();
            if (normalDirectionSignal != null && normalDirectionSignal != Signal.EMPTY_SIGNAL) {
                result.add(normalDirectionSignal);
            }
            Signal reversedDirectionSignal = blockSection.getReversedDirectionSignal();
            if (reversedDirectionSignal != null && reversedDirectionSignal != Signal.EMPTY_SIGNAL) {
                result.add(reversedDirectionSignal);
            }
        }
        result.sort(Comparator.comparing(TracksideObject::getId));
        return result;
    }

    @Override
    public String toString() {
        return id;
    }

}

package NATrain.routes;

import NATrain.quads.BlockingBaseQuad;
import NATrain.trackSideObjects.*;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalState;
import NATrain.trackSideObjects.trackSections.TrackSectionState;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static NATrain.routes.RouteDirection.*;

public class Trackline implements Serializable {

    static final long serialVersionUID = 1L;
    public static final Trackline EMPTY_TRACKLINE = new Trackline("none");
    private transient Map<PropertyChangeListener, Set<TracksideObject>> activeSignalListeners;
    private transient Set<BlockingBaseQuad> signalQuads;

    private RouteDirection normalDirection = EVEN;
    private String id;
    private TrackDirection trackDirection = TrackDirection.NORMAL;
    private boolean bidirectional = false;
    private final List<TracklineBlockSection> blockSections = new CopyOnWriteArrayList<>();
    private TrackBlockingType trackBlockingType = TrackBlockingType.AUTOMATIC_THREE_SIGNAL_BLOCKING;
    private Signal normalDirectionArrivalSignal = Signal.EMPTY_SIGNAL;
    private Signal reversedDirectionArrivalSignal = Signal.EMPTY_SIGNAL;

    public RouteDirection getNormalDirection() {
        return normalDirection;
    }

    public void setNormalDirection(RouteDirection normalDirection) {
        this.normalDirection = normalDirection;
    }

    public Trackline(String id) {
        this.id = id;
    }

    public TrackDirection getTrackDirection() {
        return trackDirection;
    }

    public RouteDirection getRouteDirection() {
        switch (normalDirection) {
            case EVEN:
                if (trackDirection == TrackDirection.NORMAL) {
                    return EVEN;
                } else {
                    return ODD;
                }
            case ODD:
                if (trackDirection == TrackDirection.NORMAL) {
                    return ODD;
                } else {
                    return EVEN;
                }
        }
        return UNDEFINED;
    }

    public List<TracklineBlockSection> getBlockSections() {
        return blockSections;
    }

    public Map<PropertyChangeListener, Set<TracksideObject>> getActiveSignalListeners() {
        return activeSignalListeners;
    }

    public Signal getFirstSignalInEvenDirection() {
        if (normalDirection == EVEN) {
          return blockSections.get(1).getNormalDirectionSignal();
        } else {
            return blockSections.get(blockSections.size() - 2).getReversedDirectionSignal();
        }
    }

    public Signal getFirstSignalInOddDirection() {
        if (normalDirection == ODD) {
            return blockSections.get(1).getNormalDirectionSignal();
        } else {
            return blockSections.get(blockSections.size() - 2).getReversedDirectionSignal();
        }
    }

    public Set<BlockingBaseQuad> getSignalQuads() {
        return signalQuads;
    }

    public void setTrackDirection(TrackDirection trackDirection) {
        //TODO make property change support for recreate listeners for BSQs
        if (bidirectional) {
            if (isAllBlockSectionsFree()) {
                deactivateSignalListeners();
                this.trackDirection = trackDirection;
                activateSignalListeners();
            } else {
                //WorkPlaceController.getActiveController().log(String.format("Trackline direction change of %s is impossible. Trackline isn't free.", this.id));
            }
        } else {
            System.out.println("Try to set direction for specialized trackline");
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
        for (TracklineBlockSection blockSection : blockSections) {
            if (blockSection.getVacancyState() == TrackSectionState.OCCUPIED)
                return false;
        }
        return true;
    }

    public void activateSignalListeners() {
        signalQuads.forEach(quad -> {
            quad.activateSignalStateAutoselectors();
            quad.refresh();
        });
    }

    public void deactivateSignalListeners() {
        activeSignalListeners.forEach((listener, objects) -> {
            objects.forEach(object -> {
                object.removePropertyChangeListener(listener);
            });
        });
        getBlockSections().stream().map(TracklineBlockSection::getNormalDirectionSignal).forEach(signal -> {signal.setSignalState(SignalState.NOT_LIGHT);});
        getBlockSections().stream().map(TracklineBlockSection::getReversedDirectionSignal).forEach(signal -> {signal.setSignalState(SignalState.NOT_LIGHT);});
        activeSignalListeners.clear();
    }

    public List<Signal> getSignals() {
        List<Signal> result = new ArrayList<>();
        for (TracklineBlockSection blockSection : blockSections) {
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

    public TracklineBlockSection getFirstSectionInActualDirection() {
        if (trackDirection == TrackDirection.NORMAL) {
            return blockSections.get(0);
        } else {
            return blockSections.get(blockSections.size() - 1);
        }
    }
    public TracklineBlockSection getLastSectionInActualDirection() {
        if (trackDirection == TrackDirection.NORMAL) {
            return blockSections.get(blockSections.size() - 1);
        } else {
            return blockSections.get(0);
        }
    }

    @Override
    public String toString() {
        return id;
    }

    public void init() {
        signalQuads = new HashSet<>();
        activeSignalListeners = new HashMap<>();
    }
}

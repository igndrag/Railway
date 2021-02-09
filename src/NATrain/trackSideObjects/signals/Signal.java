package NATrain.trackSideObjects.signals;

import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.сontrolModules.OutputChannel;
import com.sun.istack.internal.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static NATrain.trackSideObjects.signals.SignalState.*;

public class Signal extends TracksideObject {
    static final long serialVersionUID = 1L;

    public static final Signal EMPTY_SIGNAL = new Signal("None", Collections.emptySet(), SignalType.EMPTY_SIGNAL);

    static {
        EMPTY_SIGNAL.setSignalState(UNDEFINED);
    }

    public static final String INITIAL_SIGNAL_NAME = "New Signal";

    public Signal(String id, SignalType signalType) {
        super(id);
        setSignalType(signalType);
    }

    Set<SignalState> approvedSignals;
    transient SignalState signalState = SignalState.UNDEFINED;
    private SignalType signalType;
    private SignalState closedSignalState;
    private final Map<SignalLampType, OutputChannel> lamps = new HashMap<>();
    @Nullable
    private TrackSection borderedSection;
    @Nullable
    private TrackSection previousSection;


    public void setSignalType(SignalType signalType) {
        this.signalType = signalType;
        switch (signalType) {
            case STATION:
                approvedSignals = STANDARD_STATION_SIGNAL_STATES;
                closedSignalState = RED;
                break;
            case TRIMMER:
                approvedSignals = STANDARD_TRIMMER_SIGNAL_STATES;
                closedSignalState = BLUE;
                break;
            case TRACK:
                approvedSignals = STANDARD_TRACK_SIGNAL_STATES;
                closedSignalState = RED;
                break;
        }
    }

    public SignalType getSignalType() {
        return signalType;
    }

    public Set<SignalState> getApprovedSignals() {
        return approvedSignals;
    }

    public SignalState getSignalState() {
        return signalState;
    }

    public Map<SignalLampType, OutputChannel> getLamps() {
        return lamps;
    }

    public void setSignalState(SignalState signalState) {
        if (approvedSignals.contains(signalState)) {
            SignalState oldState = this.signalState;
            this.signalState = signalState;
            if (WorkPlaceController.isActiveMode()) {
                sendOutputCommands(signalState);
            }
            propertyChangeSupport.firePropertyChange("signalStateProperty", oldState, signalState);
        }
    }

    public void setClosedSignalState(SignalState closedSignalState) {
        if (closedSignalState == RED || closedSignalState == BLUE) {
            this.closedSignalState = closedSignalState;
        };
    }

    public SignalState getClosedSignalState() {
        return closedSignalState;
    }

    public Signal(String id, Set<SignalState> approvedSignals, SignalType signalType) {
        super(id);
        this.approvedSignals = approvedSignals;
        this.signalType = signalType;
    }

    public void close() {
        setSignalState(closedSignalState);
    }

    private void sendOutputCommands(SignalState signalState) {
        lamps.forEach((signalLampType, outputChannel) -> {
            outputChannel.sendCommand(signalState.getLampStates().getOrDefault(signalLampType, SignalLampState.NOT_LIGHT).getCode());
        });
    }

    public GlobalSignalState getGlobalStatus() {
        switch (signalState) {
            case RED:
            case BLUE:
                return GlobalSignalState.CLOSED;
            case GREEN:
            case WHITE:
                return GlobalSignalState.OPENED;
            case YELLOW:
            case BLINKED_WHITE:
            case BLINKED_YELLOW:
            case YELLOW_AND_YELLOW:
            case YELLOW_AND_BLINKED_YELLOW:
                return GlobalSignalState.OPENED_ON_RESTRICTED_SPEED;
            default:
                return GlobalSignalState.NOT_ACTIVE; //if not configured or not light
        }
    }

}

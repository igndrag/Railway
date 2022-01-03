package NATrain.trackSideObjects.signals;

import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.routes.RouteDirection;
import NATrain.trackSideObjects.Controllable;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.сontrolModules.ControlModule;
import NATrain.сontrolModules.OutputChannel;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static NATrain.trackSideObjects.signals.SignalState.*;

public class Signal extends TracksideObject implements Controllable {
    static final long serialVersionUID = 1L;

    private transient Set<ControlModule> assignedModules;
    public static final Signal EMPTY_SIGNAL = new Signal("None", Collections.emptySet(), SignalType.EMPTY_SIGNAL);

    static {
        EMPTY_SIGNAL.setSignalState(UNDEFINED);
    }

    public static final String INITIAL_SIGNAL_NAME = "New Signal";
    private static final Pattern pattern = Pattern.compile("\\d+$");

    public Signal(String id, SignalType signalType) {
        super(id);
        setSignalType(signalType);
    }

    Set<SignalState> approvedSignals;
    transient SignalState signalState = SignalState.UNDEFINED;
    private SignalType signalType;
    private SignalState closedSignalState;
    private final Map<SignalLampType, OutputChannel> lamps = new HashMap<>();
    private TrackSection borderedSection;
    private TrackSection previousSection;
    private RouteDirection direction = RouteDirection.UNDEFINED;
    private Integer signalNumber = 0;

    @Override
    public void setId(String id) {
        super.setId(id);
        Matcher matcher = pattern.matcher(id);
        if (matcher.find()) {
            try {
                signalNumber = Integer.parseInt(id.substring(matcher.start(), matcher.end()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

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
            case ROAD_CROSSING_SIGNAL:
                approvedSignals = STANDARD_ROAD_CROSSING_SIGNAL_STATES;
                closedSignalState = CROSSING_SIGNAL_CLOSED;
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
        }
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

    public void init() {
        assignedModules = new HashSet<>();
        assignedModules = lamps.values().stream().map(OutputChannel::getModule).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    private void sendOutputCommands(SignalState signalState) {
        if (!assignedModules.isEmpty()) {
            assignedModules.forEach(controlModule -> {
                StringBuilder sb = new StringBuilder();
                lamps.forEach((signalLampType, outputChannel) -> {
                    //outputChannel.sendCommandCode(signalState.getLampStates().getOrDefault(signalLampType, SignalLampState.NOT_LIGHT).getCode());
                    if (outputChannel.getModule() == controlModule) {
                        int commandCode = signalState.getLampStates().getOrDefault(signalLampType, SignalLampState.NOT_LIGHT).getCode();
                        outputChannel.setLastCommandCode(commandCode);
                        sb.append(String.format("%02d:%02d_", outputChannel.getChNumber(), commandCode));
                    }
                });
                controlModule.sendMultipleCommand(sb.toString());
            });
        }
    }

    public static GlobalSignalState getGlobalStatusForState(SignalState signalState) {
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

    public GlobalSignalState getGlobalStatus() {
        return getGlobalStatusForState(signalState);
    }

    public RouteDirection getDirection() {
        return direction;
    }

    public void setDirection(RouteDirection direction) {
        this.direction = direction;
    }

    @Override
    public String getModules() {
        StringBuilder stringBuilder = new StringBuilder();
        lamps.values().stream().map(OutputChannel::getModule)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()).forEach(controlModule -> {
            stringBuilder.append(controlModule.getId());
            stringBuilder.append(controlModule.getConfiguredChannels(this));
        });
        return stringBuilder.toString();
    }

    public boolean isEven() {
        return signalNumber > 0 && signalNumber % 2 == 0;
    }

    public boolean isOdd() {
        return signalNumber > 0 && signalNumber % 2 > 0;
    }

}

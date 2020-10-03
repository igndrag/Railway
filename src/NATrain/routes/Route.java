package NATrain.routes;

import NATrain.trackSideObjects.*;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public abstract class Route implements Serializable {

    protected RouteType routeType;
    private String description;
    private Signal signal;
    private ConcurrentMap <Switch, SwitchState> switchStatePositions = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue <TrackSection> occupationalOrder = new ConcurrentLinkedQueue<>();
    private TrackSection destination;

    public Route (String description) {
        this.description = description;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public String getDescription() {
        return description;
    }

    public String getRouteTypeName() {
        return routeType.getName();
    }

    public String getSignalName() {
        return signal.getId();
    }

    public TrackSection getDestination() {
        return destination;
    }

    public void setDestination(TrackSection destination) {
        this.destination = destination;
    }

    public String getSwitchPositions() {
        if (switchStatePositions.size() == 0)
            return "none";
        else {
            StringBuilder stringBuilder = new StringBuilder();
            switchStatePositions.forEach( (sw, position) -> {
                if (position == SwitchState.PLUS) {
                    stringBuilder.append("+");
                } else {
                    stringBuilder.append("-");
                }
                stringBuilder.append(sw.getId()).append(", ");
            });
            return stringBuilder.toString();
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public ConcurrentMap<Switch, SwitchState> getSwitchStatePositions() {
        return switchStatePositions;
    }

    public void setSwitchStatePositions(ConcurrentMap<Switch, SwitchState> switchStatePositions) {
        this.switchStatePositions = switchStatePositions;
    }

    public ConcurrentLinkedQueue<TrackSection> getOccupationalOrder() {
        return occupationalOrder;
    }

    public void setOccupationalOrder(ConcurrentLinkedQueue<TrackSection> occupationalOrder) {
        this.occupationalOrder = occupationalOrder;
    }
}

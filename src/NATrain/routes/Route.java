package NATrain.routes;

import NATrain.trackSideObjects.*;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;

public class Route implements Serializable {
    static final long serialVersionUID = 1L;

    protected RouteType routeType;
    private String description;
    private Signal signal;
    private ConcurrentMap <Switch, SwitchState> switchStatePositions = new ConcurrentHashMap<>();
    private ConcurrentLinkedDeque <TrackSection> occupationalOrder = new ConcurrentLinkedDeque<>();
    private TrackSection destinationTrackSection;
    private Signal destinationSignal;
    private TrackAutomationType trackAutomationType;
    private Boolean withManeuver;
    private TrackSection TVDS1;
    private TrackSection TVDS2;


    public Route (String description, RouteType routeType) {
        this.description = description;
        this.routeType = routeType;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
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

    public TrackSection getDestinationTrackSection() {
        return destinationTrackSection;
    }

    public Signal getDestinationSignal() {
        return destinationSignal;
    }

    public void setDestinationSignal(Signal destinationSignal) {
        this.destinationSignal = destinationSignal;
    }

    public void setDestinationTrackSection(TrackSection destinationTrackSection) {
        this.destinationTrackSection = destinationTrackSection;
    }

    public TrackAutomationType getTrackAutomationType() {
        return trackAutomationType;
    }

    public void setTrackAutomationType(TrackAutomationType trackAutomationType) {
        this.trackAutomationType = trackAutomationType;
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

    public ConcurrentLinkedDeque<TrackSection> getOccupationalOrder() {
        return occupationalOrder;
    }

    public void setOccupationalOrder(ConcurrentLinkedDeque<TrackSection> occupationalOrder) {
        this.occupationalOrder = occupationalOrder;
    }

    public Boolean getWithManeuver() {
        return withManeuver;
    }

    public void setWithManeuver(Boolean withManeuver) {
        this.withManeuver = withManeuver;
    }

    public TrackSection getTVDS1() {
        return TVDS1;
    }

    public void setTVDS1(TrackSection TVDS1) {
        this.TVDS1 = TVDS1;
    }

    public TrackSection getTVDS2() {
        return TVDS2;
    }

    public void setTVDS2(TrackSection TVDS2) {
        this.TVDS2 = TVDS2;
    }

}

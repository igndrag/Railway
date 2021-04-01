package NATrain.routes;

import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.switches.SwitchState;
import NATrain.trackSideObjects.trackSections.TrackSection;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;

public class Route implements Serializable {
    static final long serialVersionUID = 1L;

    protected RouteDirection routeDirection;
    protected RouteType routeType;
    private String description;
    private Signal signal;
    private ConcurrentMap<Switch, SwitchState> switchStatePositions = new ConcurrentHashMap<>();
    private ConcurrentLinkedDeque<TrackSection> occupationalOrder = new ConcurrentLinkedDeque<>();
    private TrackSection departureTrackSection;
    private TrackSection destinationTrackSection;
    private TrackBlockingType trackAutomationType;
    private Boolean withManeuver = false;
    private TrackBlockSection TVDS1;
    private TrackBlockSection TVDS2;
    private Track destinationTrackLine;

    public Route(String description, RouteType routeType) {
        this.description = description;
        this.routeType = routeType;
    }

    public Track getDestinationTrackLine() {
        return destinationTrackLine;
    }

    public void setDestinationTrackLine(Track destinationTrackLine) {
        this.destinationTrackLine = destinationTrackLine;
    }

    public RouteDirection getRouteDirection() {
        return routeDirection;
    }

    public void setRouteDirection(RouteDirection routeDirection) {
        this.routeDirection = routeDirection;
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

    public TrackSection getDepartureTrackSection() {
        return departureTrackSection;
    }

    public void setDepartureTrackSection(TrackSection departureTrackSection) {
        this.departureTrackSection = departureTrackSection;
    }

    public void setDestinationTrackSection(TrackSection destinationTrackSection) {
        this.destinationTrackSection = destinationTrackSection;
    }

    public TrackBlockingType getTrackAutomationType() {
        return trackAutomationType;
    }

    public void setTrackAutomationType(TrackBlockingType trackAutomationType) {
        this.trackAutomationType = trackAutomationType;
    }

    public String getSwitchPositions() {
        if (switchStatePositions.size() == 0)
            return "none";
        else {
            StringBuilder stringBuilder = new StringBuilder();
            switchStatePositions.forEach((sw, position) -> {
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

    public TrackBlockSection getTVDS1() {
        return TVDS1;
    }

    public void setTVDS1(TrackBlockSection TVDS1) {
        this.TVDS1 = TVDS1;
    }

    public TrackBlockSection getTVDS2() {
        return TVDS2;
    }

    public void setTVDS2(TrackBlockSection TVDS2) {
        this.TVDS2 = TVDS2;
    }
}

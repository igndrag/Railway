package NATrain.UI.workPlace.executors;

import NATrain.routes.Route;
import NATrain.routes.RouteDirection;
import NATrain.trackSideObjects.signals.GlobalSignalState;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalState;
import NATrain.trackSideObjects.trackSections.TrackSectionState;

public class ArrivalRouteExecutor extends AbstractRouteExecutor {
    public ArrivalRouteExecutor(Route route) {
        super(route);
    }

    @Override
    public void autoselectSignalState() {
        Signal signal = route.getSignal();
        GlobalSignalState nextSignalGlobalState;
        if (route.getRouteDirection() == RouteDirection.EVEN) {
            nextSignalGlobalState = route.getStationTrack().getEvenSignal().getGlobalStatus();
        } else {
            nextSignalGlobalState = route.getStationTrack().getOddSignal().getGlobalStatus();
        }

        if (routeStatus == RouteStatus.READY && isAllSectionsFree()
                && route.getDestinationTrackSection().getVacancyState() == TrackSectionState.FREE) {
            if (route.getWithManeuver()) {
                if (nextSignalGlobalState == GlobalSignalState.OPENED || nextSignalGlobalState == GlobalSignalState.OPENED_ON_RESTRICTED_SPEED) {
                    signal.setSignalState(SignalState.YELLOW_AND_BLINKED_YELLOW);
                } else if (nextSignalGlobalState == GlobalSignalState.CLOSED) {
                    signal.setSignalState(SignalState.YELLOW_AND_YELLOW);
                }
            } else {
                if (nextSignalGlobalState == GlobalSignalState.OPENED || nextSignalGlobalState == GlobalSignalState.OPENED_ON_RESTRICTED_SPEED) {
                    signal.setSignalState(SignalState.GREEN);
                } else if (nextSignalGlobalState == GlobalSignalState.CLOSED) {
                    signal.setSignalState(SignalState.YELLOW);
                }
            }
        } else {
            route.getSignal().close();
        }
    }
}

package NATrain.UI.workPlace.executors;

import NATrain.routes.Route;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalState;
import NATrain.trackSideObjects.trackSections.TrackSectionState;

public class DepartureRouteExecutor extends AbstractRouteExecutor {
    public DepartureRouteExecutor(Route route) {
        super(route);
    }

    @Override
    public void autoselectSignalState() {
        Signal signal = route.getSignal();
        if (routeStatus == RouteStatus.READY && isAllSectionsFree()
                && route.getTVDS1().getVacancyState() == TrackSectionState.FREE) {
            if (route.getWithManeuver()) {
                if (route.getTVDS2().getVacancyState() == TrackSectionState.FREE) {
                 //   signal.sendCommand(Command.OPEN_ON_YELLOW_AND_BLINKED_YELLOW);
                    signal.setSignalState(SignalState.YELLOW_AND_BLINKED_YELLOW);
                } else {
                 //   signal.sendCommand(Command.OPEN_ON_YELLOW_AND_YELLOW);
                    route.getSignal().setSignalState(SignalState.YELLOW_AND_YELLOW);
                }
            } else {
                if (route.getTVDS2().getVacancyState() == TrackSectionState.FREE) {
                   // signal.sendCommand(Command.OPEN_ON_GREEN);
                    route.getSignal().setSignalState(SignalState.GREEN);
                } else {
                   // signal.sendCommand(Command.OPEN_ON_YELLOW);
                    route.getSignal().setSignalState(SignalState.YELLOW);
                }
            }
        } else {
            route.getSignal().close();
        }
    }
}

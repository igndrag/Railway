package NATrain.UI.workPlace.executors;

import NATrain.remoteControlModules.Command;
import NATrain.routes.Route;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.SignalState;
import NATrain.trackSideObjects.TrackSectionState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DepartureRouteExecutor extends AbstractRouteExecutor {
    public DepartureRouteExecutor(Route route) {
        super(route);
    }


    private class TVDSStateListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            autoselectSignalState();
        }
    }

    @Override
    public void autoselectSignalState() {
        Signal signal = route.getSignal();
        if (routeStatus == RouteStatus.READY && isAllSectionsFree()) {
            if (route.getWithManeuver()) {
                if (route.getTVDS2().getVacancyState() == TrackSectionState.FREE) {
                  //  signal.getControlModule().sendCommand(signal.getChannel(), Command.OPEN_ON_YELLOW_AND_BLINKED_YELLOW);
                    signal.setSignalState(SignalState.YELLOW_AND_BLINKED_YELLOW);
                } else {
                    route.getSignal().setSignalState(SignalState.YELLOW_AND_YELLOW);
                }
            } else {
                if (route.getTVDS2().getVacancyState() == TrackSectionState.FREE) {
                    route.getSignal().setSignalState(SignalState.GREEN);
                } else {
                    route.getSignal().setSignalState(SignalState.YELLOW);
                }
            }
        } else {
            route.getSignal().close();
        }
    }
}

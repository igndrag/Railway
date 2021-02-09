package NATrain.UI.workPlace.executors;

import NATrain.routes.Route;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalState;

public class ShuntingRouteExecutor extends AbstractRouteExecutor {
    public ShuntingRouteExecutor(Route route) {
        super(route);
    }

    @Override
    public void autoselectSignalState() {
        Signal signal = route.getSignal();
        if (routeStatus == RouteStatus.READY && isAllSectionsFree()) {
            signal.setSignalState(SignalState.WHITE);
        } else {
            signal.close();
        }
    }
}

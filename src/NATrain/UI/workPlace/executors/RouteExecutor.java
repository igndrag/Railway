package NATrain.UI.workPlace.executors;

import NATrain.routes.Route;

public interface RouteExecutor {
    Route getRoute();
    void executeRoute();
    String getRouteDescription();
    RouteStatus getRouteStatus();
    void autoselectSignalState();
    void cancelRoute();
}

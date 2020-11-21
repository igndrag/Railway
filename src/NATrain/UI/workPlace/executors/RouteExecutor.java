package NATrain.UI.workPlace.executors;

public interface RouteExecutor {
    void executeRoute();
    String getRouteDescription();
    RouteStatus getRouteStatus();
    void autoselectSignalState();
    void cancelRoute();
}

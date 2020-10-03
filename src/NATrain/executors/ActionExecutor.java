package NATrain.executors;

import NATrain.quads.Quad;
import NATrain.routes.Route;
import NATrain.trackSideObjects.ControlAction;

public enum ActionExecutor {
    INSTANCE;

    public static void executeControlAction(ControlAction controlAction, Quad quad) {
        System.out.println("Request on " + controlAction.getDescription() + " received from " + quad.getType());
    }

    public void setRoute (Route route) {
        RouteExecutor routeExecutor = new RouteExecutor(route);
                                                //TODO
        routeExecutor.start();
    }

    public void cancelRoute (Route route) {

    }





}

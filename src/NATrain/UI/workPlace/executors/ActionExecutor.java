package NATrain.UI.workPlace.executors;

import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.model.Model;
import NATrain.quads.EmptyQuad;
import NATrain.quads.Quad;
import NATrain.quads.SignalQuad;
import NATrain.quads.SimpleTrackQuad;
import NATrain.routes.Route;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.TracksideObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class ActionExecutor {

    public static final Quad EMPTY_QUAD = new EmptyQuad(-1, -1) ;
    public static final ObservableList<Route> EMPTY_ROUTE_LIST = FXCollections.observableArrayList();

    private Quad firstSelectedQuad = EMPTY_QUAD;
    private ControlAction firstControlAction;
    private Quad secondSelectedQuad = EMPTY_QUAD;
    private ControlAction secondControlAction;
    private WorkPlaceController workPlaceController;

    public ActionExecutor(WorkPlaceController workPlaceController) {
        this.workPlaceController = workPlaceController;
    }

    private ObservableList<RouteExecutor> activeRoutes = FXCollections.observableArrayList();

    public ObservableList<RouteExecutor> getActiveRoutes() {
        return activeRoutes;
    }

    public void executeControlAction(ControlAction controlAction, Quad quad) {
        System.out.println("Request on " + controlAction.getDescription() + " received from " + quad.getType());
        if (firstSelectedQuad == EMPTY_QUAD) {
            if (controlAction == ControlAction.SET_ROUT_FROM) {
                firstSelectedQuad = quad;
                firstControlAction = controlAction;
                quad.select();
            } else {
                execute();
            }
        } else if (secondSelectedQuad == EMPTY_QUAD) {
            secondSelectedQuad = quad;
            secondControlAction = controlAction;
            quad.select();
            execute();
        }

    }

    private void execute() {
        switch (firstControlAction) {
            case SET_ROUT_FROM:
                ObservableList<Route> foundedRoutes = EMPTY_ROUTE_LIST;
                switch (secondControlAction) {
                    case SET_ROUT_TO:
                        foundedRoutes = findRoutes(((SignalQuad) firstSelectedQuad).getAssociatedSignal(),
                                ((SignalQuad) secondSelectedQuad).getAssociatedSignal());
                        break;
                    case SET_ROUTE_TO_TRACK:
                        foundedRoutes = findRoutes(((SignalQuad) firstSelectedQuad).getAssociatedSignal(),
                                ((SimpleTrackQuad) secondSelectedQuad).getFirstAssociatedTrack());
                        break;
                    default:
                        workPlaceController.log("Wrong command.");
                }
                if (foundedRoutes.isEmpty()) {
                    workPlaceController.log("Route isn't founded in route table.");
                } else {
                    if (foundedRoutes.size() == 1) {
                        prepareRoute(foundedRoutes.get(0));
                    } else {
                        //TODO create pane for choosing alternative routes
                    }
                }
            case CHANGE_SWITCH_POSITION:
        }
        clearSelection();
    }

    private void prepareRoute(Route route) {
        RouteExecutor routeExecutor = null;
        switch (route.getRouteType()) {
            case ARRIVAL:
                routeExecutor = new ArrivalRouteExecutor(route);
                break;
            case DEPARTURE:
                routeExecutor = new DepartureRouteExecutor(route);
                break;
            case SHUNTING:
                //TODO
        }
        if (routeExecutor != null) {
            routeExecutor.executeRoute();
            activeRoutes.add(routeExecutor);
        }
    }

    private ObservableList<Route> findRoutes(Signal signal, TracksideObject tracksideObject) {
        List<Route> result = Model.getRouteTable().stream()
                .filter(route -> route.getSignal() == signal)
                .filter(route -> route.getNextSignal() == tracksideObject || route.getDestinationTrackSection() == tracksideObject)
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(result);
    }


    public void cancelRoute (RouteExecutor routeExecutor) {

    }

    public void clearSelection() {
        firstSelectedQuad.unselect();
        firstSelectedQuad = EMPTY_QUAD;
        firstControlAction = null;
        secondSelectedQuad.unselect();
        secondSelectedQuad = EMPTY_QUAD;
        secondControlAction = null;
    }





}

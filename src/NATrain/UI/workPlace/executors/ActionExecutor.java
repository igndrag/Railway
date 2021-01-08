package NATrain.UI.workPlace.executors;

import NATrain.UI.mosaicRedactor.MosaicRedactorFxController;
import NATrain.UI.mosaicRedactor.QuadConfiguratorFxController;
import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.model.Model;
import NATrain.quads.*;
import NATrain.routes.Route;
import NATrain.routes.Track;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TracksideObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ActionExecutor {

    public static final Quad EMPTY_QUAD = new EmptyQuad(-1, -1);
    public static final ObservableList<Route> EMPTY_ROUTE_LIST = FXCollections.observableArrayList();

    private Quad firstSelectedQuad = EMPTY_QUAD;
    private ControlAction firstControlAction;
    private Quad secondSelectedQuad = EMPTY_QUAD;
    private ControlAction secondControlAction;
    private WorkPlaceController workPlaceController;

    public ActionExecutor(WorkPlaceController workPlaceController) {
        this.workPlaceController = workPlaceController;
    }

    private static ObservableList<RouteExecutor> activeRoutes = FXCollections.observableArrayList();

    public ObservableList<RouteExecutor> getActiveRoutes() {
        return activeRoutes;
    }

    public void executeControlAction(ControlAction controlAction, Quad quad) {
        System.out.println("Request on " + controlAction.getDescription() + " received from " + quad.getType());
        if (firstSelectedQuad == EMPTY_QUAD) {
            firstControlAction = controlAction;
            firstSelectedQuad = quad;
            if (controlAction == ControlAction.SET_ROUT_FROM) {
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
                    case SET_ROUTE_TO_TRACK_LINE:
                        foundedRoutes = findRoutsToTrackLine(((SignalQuad) firstSelectedQuad).getAssociatedSignal(),
                                ((BlockingTrackQuad) secondSelectedQuad).getFirstBlockSection().getTrack());
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
                        try {
                            toAlternativeRouteSelector(FXCollections.observableArrayList(foundedRoutes));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case CHANGE_SWITCH_POSITION:
                Switch aSwitch = ((SwitchQuad) firstSelectedQuad).getAssociatedSwitch();
                //aSwitch.sendCommandToChangePosition();
                break;
        }
        clearSelection();
    }

    protected static void prepareRoute(Route route) {
        RouteExecutor routeExecutor = null;
        switch (route.getRouteType()) {
            case ARRIVAL:
                routeExecutor = new ArrivalRouteExecutor(route);
                break;
            case DEPARTURE:
                routeExecutor = new DepartureRouteExecutor(route);
                break;
            case SHUNTING:
                routeExecutor = new ShuntingRouteExecutor(route);
        }
        routeExecutor.executeRoute();
        activeRoutes.add(routeExecutor);
    }

    private ObservableList<Route> findRoutes(Signal signal, TracksideObject tracksideObject) {
        List<Route> result = Model.getRouteTable().stream()
                .filter(route -> route.getSignal() == signal)
                .filter(route -> route.getDestinationTrackSection() == tracksideObject)
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(result);
    }

    private ObservableList<Route> findRoutsToTrackLine(Signal signal, Track track) {
        List<Route> result = Model.getRouteTable().stream()
                .filter(route -> route.getSignal() == signal)
                .filter(route -> route.getDestinationTrack() == track)
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(result);
    }

    public void clearSelection() {
        firstSelectedQuad.unselect();
        firstSelectedQuad = EMPTY_QUAD;
        firstControlAction = null;
        secondSelectedQuad.unselect();
        secondSelectedQuad = EMPTY_QUAD;
        secondControlAction = null;
    }

    private void toAlternativeRouteSelector(ObservableList<Route> routes) throws IOException {
        FXMLLoader loader = new FXMLLoader(AlternativeRouteSelectorController.class.getResource("AlternativeRouteSelector.fxml"));
        Stage alternativeRouteSelector = new Stage();
        alternativeRouteSelector.setTitle("Alternative Route Selector");
        alternativeRouteSelector.setScene(new Scene(loader.load(), 500, 200));
        alternativeRouteSelector.setResizable(false);
        AlternativeRouteSelectorController controller = loader.getController();
        controller.initialize(routes);
        alternativeRouteSelector.initModality(Modality.WINDOW_MODAL);
        alternativeRouteSelector.initOwner(workPlaceController.getPrimaryStage());
        alternativeRouteSelector.show();
    }
}

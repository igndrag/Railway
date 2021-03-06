package NATrain.UI.workPlace.executors;

import NATrain.UI.workPlace.LocomotiveSelectorController;
import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.model.Model;
import NATrain.quads.*;
import NATrain.routes.Route;
import NATrain.routes.StationTrack;
import NATrain.routes.Track;
import NATrain.routes.TrackDirection;
import NATrain.trackSideObjects.ControlAction;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.trackSections.TrackSection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public enum ActionExecutor {

    INSTANCE;

    public static final Quad EMPTY_QUAD = new EmptyQuad(-1, -1);
    public static final ObservableList<Route> EMPTY_ROUTE_LIST = FXCollections.observableArrayList();

    private static Quad firstSelectedQuad = EMPTY_QUAD;
    private static ControlAction firstControlAction;
    private static Quad secondSelectedQuad = EMPTY_QUAD;
    private static ControlAction secondControlAction;

    private static ObservableList<RouteExecutor> activeRoutes = FXCollections.observableArrayList();

    public static ObservableList<RouteExecutor> getActiveRoutes() {
        return activeRoutes;
    }

    public static void executeControlAction(ControlAction controlAction, Quad quad) {
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

    private static void execute() {
        switch (firstControlAction) {
            case SET_ROUT_FROM:
                ObservableList<Route> foundedRoutes = EMPTY_ROUTE_LIST;
                Signal firstSignal = Signal.EMPTY_SIGNAL;
                if (firstSelectedQuad instanceof SignalQuad) {
                    firstSignal = ((SignalQuad) firstSelectedQuad).getAssociatedSignal();
                }
                if (firstSelectedQuad instanceof ArrivalSignalQuad) {
                    firstSignal = ((ArrivalSignalQuad) firstSelectedQuad).getAssociatedSignal();
                }
                switch (secondControlAction) {
                    case SET_ROUT_TO:
                        Signal secondSignal = ((SignalQuad) secondSelectedQuad).getAssociatedSignal();
                        foundedRoutes = findRoutes(firstSignal, secondSignal);
                        break;
                    case SET_ROUTE_TO_TRACK:
                        foundedRoutes = findRoutes(firstSignal,
                                ((SimpleTrackQuad) secondSelectedQuad).getFirstAssociatedTrack());
                        break;
                    case SET_ROUTE_TO_TRACK_LINE:
                        foundedRoutes = findRoutsToTrackLine(firstSignal,
                                ((BlockingTrackQuad) secondSelectedQuad).getFirstBlockSection().getTrack());
                        break;
                    default:
                        WorkPlaceController.getActiveController().log("Wrong command.");
                }
                if (foundedRoutes.isEmpty()) {
                    WorkPlaceController.getActiveController().log("Route isn't founded in route table.");
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
                aSwitch.changePosition();
                break;
            case CHANGE_TRACK_LINE_DIRECTION:
                Track track = ((BlockingControlQuad) firstSelectedQuad).getTrack();
                TrackDirection newDirection = track.getTrackDirection() == TrackDirection.NORMAL ? TrackDirection.REVERSED : TrackDirection.NORMAL;
                track.setTrackDirection(newDirection);
                firstSelectedQuad.refresh();
                break;
            case ALLOCATE_LOCOMOTIVE:
                try {
                    if (firstSelectedQuad instanceof SimpleTrackQuad) {
                        toLocomotiveSelector(((SimpleTrackQuad) firstSelectedQuad).getFirstAssociatedTrack());
                    } else if (firstSelectedQuad instanceof BlockingTrackQuad) {
                        toLocomotiveSelector(((BlockingTrackQuad)firstSelectedQuad).getFirstBlockSection());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    private static ObservableList<Route> findRoutes(Signal signal, TracksideObject tracksideObject) {
        List<Route> result = Model.getRouteTable().stream()
                .filter(route -> route.getSignal() == signal)
                .filter(route -> route.getDestinationTrackSection() == tracksideObject)
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(result);
    }

    private static ObservableList<Route> findRoutsToTrackLine(Signal signal, Track track) {
        List<Route> result = Model.getRouteTable().stream()
                .filter(route -> route.getSignal() == signal)
                .filter(route -> route.getDestinationTrackLine() == track)
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(result);
    }

    public static void clearSelection() {
        firstSelectedQuad.unselect();
        firstSelectedQuad = EMPTY_QUAD;
        firstControlAction = null;
        secondSelectedQuad.unselect();
        secondSelectedQuad = EMPTY_QUAD;
        secondControlAction = null;
    }

    private static void toAlternativeRouteSelector(ObservableList<Route> routes) throws IOException {
        FXMLLoader loader = new FXMLLoader(AlternativeRouteSelectorController.class.getResource("AlternativeRouteSelector.fxml"));
        Stage alternativeRouteSelector = new Stage();
        alternativeRouteSelector.setTitle("Alternative Route Selector");
        alternativeRouteSelector.setScene(new Scene(loader.load(), 500, 200));
        alternativeRouteSelector.setResizable(false);
        AlternativeRouteSelectorController controller = loader.getController();
        controller.initialize(routes);
        alternativeRouteSelector.initModality(Modality.WINDOW_MODAL);
        alternativeRouteSelector.initOwner(WorkPlaceController.getActiveController().getPrimaryStage());
        alternativeRouteSelector.show();
    }

    private static void toLocomotiveSelector(TrackSection track) throws IOException {
        FXMLLoader loader = new FXMLLoader(LocomotiveSelectorController.class.getResource("LocomotiveSelector.fxml"));
        Stage locomotiveSelector = new Stage();
        locomotiveSelector.setTitle("Locomotive Selector");
        locomotiveSelector.setScene(new Scene(loader.load(), 250, 350));
        locomotiveSelector.setResizable(false);
        LocomotiveSelectorController controller = loader.getController();
        controller.initialize(track);
        locomotiveSelector.initModality(Modality.WINDOW_MODAL);
        locomotiveSelector.initOwner(WorkPlaceController.getActiveController().getPrimaryStage());
        locomotiveSelector.show();
    }

}

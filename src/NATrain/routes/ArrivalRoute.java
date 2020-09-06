package NATrain.routes;

public class ArrivalRoute extends TrackRoute {

    public ArrivalRoute(String description) {
        super(description);
        this.routeType = RouteType.ARRIVAL;
    }
}

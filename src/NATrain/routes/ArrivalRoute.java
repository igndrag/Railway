package NATrain.routes;

public class ArrivalRoute extends TrackRoute {
    static final long serialVersionUID = 1L;

    public ArrivalRoute(String description) {
        super(description);
        this.routeType = RouteType.ARRIVAL;
    }
}

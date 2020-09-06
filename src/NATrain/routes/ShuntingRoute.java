package NATrain.routes;

public class ShuntingRoute extends Route {
    public ShuntingRoute(String description) {
        super(description);
        this.routeType = RouteType.SHUNTING;
    }
}

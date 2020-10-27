package NATrain.routes;

public class ShuntingRoute extends Route {
    static final long serialVersionUID = 1L;

    public ShuntingRoute(String description) {
        super(description);
        this.routeType = RouteType.SHUNTING;
    }
}

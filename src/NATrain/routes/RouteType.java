package NATrain.routes;

public enum RouteType {

    ARRIVAL ("Arrival"),
    DEPARTURE ("Departure"),
    SHUNTING ("Shunting");

    private final String name;

    private RouteType(String description) {
        this.name = description;
    }

    public String getName() {
        return name;
    }


}

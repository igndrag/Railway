package NATrain.routes;

public abstract class TrackRoute extends Route {

    public TrackRoute(String description) {
        super(description);
    }

    Boolean withManeuver;

    public Boolean getWithManeuver() {
        return withManeuver;
    }

    public void setWithManeuver(Boolean withManeuver) {
        this.withManeuver = withManeuver;
    }
}

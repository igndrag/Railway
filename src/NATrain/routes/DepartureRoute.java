package NATrain.routes;

import NATrain.trackSideObjects.TrackSection;

public class DepartureRoute extends TrackRoute {
    static final long serialVersionUID = 1L;

    private TrackSection TVDS1; //track vacancy detection section
    private TrackSection TVDS2;

    public DepartureRoute(String description) {
        super(description);
        this.routeType = RouteType.DEPARTURE;
    }

    public TrackSection getTVDS1() {
        return TVDS1;
    }

    public void setTVDS1(TrackSection TVDS1) {
        this.TVDS1 = TVDS1;
    }

    public TrackSection getTVDS2() {
        return TVDS2;
    }

    public void setTVDS2(TrackSection TVDS2) {
        this.TVDS2 = TVDS2;
    }

    public void initialize() {

    }
}

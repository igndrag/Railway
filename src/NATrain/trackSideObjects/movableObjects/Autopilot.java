package NATrain.trackSideObjects.movableObjects;

import NATrain.routes.AutopilotMode;
import NATrain.routes.Route;



public interface Autopilot {
    public static final int FULL_SPEED = 850; //max value is 1024
    public static final int RESTRICTED_SPEED = 650;
    public static final int SUPER_RESTRICTED_SPEED = 500; // impossible to start))

    void executeRoute(Route route);
    void enable();
    void disable();
    AutopilotMode getMode();
    void checkPreparedRoute();
    //void executeTrackLineMovement(Track track);
}

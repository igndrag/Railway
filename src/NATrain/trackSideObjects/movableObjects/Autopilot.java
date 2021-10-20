package NATrain.trackSideObjects.movableObjects;

import NATrain.routes.AutopilotMode;
import NATrain.routes.Route;
import NATrain.routes.Track;

public interface Autopilot {
    void executeRoute(Route route);
    //void enable();
    void disable();
    AutopilotMode getMode();
    //void executeTrackLineMovement(Track track);
}

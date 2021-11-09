package NATrain.trackSideObjects.movableObjects;

import NATrain.UI.workPlace.executors.ActionExecutor;
import NATrain.UI.workPlace.executors.RouteExecutor;
import NATrain.UI.workPlace.executors.RouteStatus;
import NATrain.routes.AutopilotMode;
import NATrain.routes.Route;
import NATrain.routes.Track;
import NATrain.trackSideObjects.trackSections.TrackSection;

import java.util.Optional;

public interface Autopilot {
    void executeRoute(Route route);
    void enable();
    void disable();
    AutopilotMode getMode();
    void checkPreparedRoute();
    //void executeTrackLineMovement(Track track);
}

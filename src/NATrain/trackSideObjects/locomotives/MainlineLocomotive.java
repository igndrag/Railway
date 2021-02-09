package NATrain.trackSideObjects.locomotives;

import NATrain.routes.RouteDirection;
import NATrain.trackSideObjects.signals.Signal;

public class MainlineLocomotive extends Locomotive{
    public MainlineLocomotive(String id) {
        super(id);
    }

    private RouteDirection forwardDirection = RouteDirection.EVEN;
    private transient Signal nextSignal;
    private boolean autoPilotOn = false;

}

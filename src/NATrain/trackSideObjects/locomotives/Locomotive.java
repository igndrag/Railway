package NATrain.trackSideObjects.locomotives;

import NATrain.routes.RouteDirection;
import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.сontrolModules.OutputChannel;
import NATrain.сontrolModules.OutputChannelType;

public class Locomotive extends TracksideObject {
    OutputChannel outputChannel = new OutputChannel(OutputChannelType.LOCOMOTIVE_OUTPUT, this, null);

    public LocomotiveState actualState = LocomotiveState.NOT_MOVING;
    int speed = 0;
    public boolean mainLight = false;
    public boolean rearLight = false;

    private RouteDirection forwardDirection = RouteDirection.EVEN;  //for autopilot usage
    private transient Signal nextSignal;                            //for autopilot usage
    private boolean autoPilotOn = false;                            //for autopilot usage

    public static final String INITIAL_LOCOMOTIVE_NAME = "New Locomotive";

    private TrackSection location = TrackSection.EMPTY_TRACK_SECTION;

    public Locomotive(String id) {
        super(id);
    }

    public OutputChannel getOutputChannel() {
        return outputChannel;
    }

    public TrackSection getLocation() {
        return location;
    }

    public void setLocation(TrackSection location) {
        this.location = location;
    }

    @Override
    public String getModules() {
        return null;
    }
}

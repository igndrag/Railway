package NATrain.trackSideObjects.locomotives;

import NATrain.routes.AutopilotMode;
import NATrain.routes.Route;
import NATrain.routes.StationTrack;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalState;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.сontrolModules.MQTTLocomotiveModule;
import com.sun.org.apache.regexp.internal.RE;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Autopilot {

    private AutopilotMode mode;
    private Route route;
    private Locomotive locomotive;

    private TrackSection nextLocation;
    private PropertyChangeListener nextLocationListener;
    private TrackSection destination;
    private Signal nextSignal;
    private NextLocationListener listener;
    private Queue<TrackSection> movementPlan;

    public static final int FULL_SPEED = 1024;
    public static final int RESTRICTED_SPEED = 700;

    public Autopilot(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    public Signal getNextSignal() {
        return nextSignal;
    }

    protected class NextLocationListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            nextLocation.removePropertyChangeListener(this);//delete listener from previous section
            locomotive.setLocation(nextLocation);//TODO
            if (nextLocation != destination) {
                nextLocation.addPropertyChangeListener(this);
            }
        }
    }

    protected class NextSignalListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            SignalState signalState = (SignalState) evt.getNewValue();
            switch (Signal.getGlobalStatus(signalState)) {
                case CLOSED:
                    locomotive.stop();
                    break;
                case OPENED_ON_RESTRICTED_SPEED:
                    locomotive.setSpeed(RESTRICTED_SPEED);
                    break;
                case OPENED:
                    locomotive.setSpeed(FULL_SPEED);
                    break;
            }
        }
    }

    public void setRoute(Route route) {
        this.route = route;
        this.mode = AutopilotMode.STATION;
        switch (route.getRouteType()) {
            case ARRIVAL:
               // StationTrack arrivalTrack = route. //TODO
        }
        this.destination = route.getDestinationTrackSection();
        this.movementPlan = new LinkedBlockingQueue<>(route.getOccupationalOrder()); //create local copy of occupational order
        this.nextLocation = movementPlan.peek();
    }


    private void stopTimerStart() {
    //todo
    }





}

package NATrain.routes;

import NATrain.trackSideObjects.locomotives.Locomotive;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.trackSections.TrackSection;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Autopilot {

    private AutopilotMode mode;
    private Route route;
    private Locomotive locomotive;

    private TrackSection nextLocation;
    private PropertyChangeListener nextLocationListener;
    private TrackSection destination;
    private Signal nextSignal;
    private NextLocationListener listener;

    public Autopilot(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    public Signal getNextSignal() {
        return nextSignal;
    }

    protected class NextLocationListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            locomotive.setLocation(nextLocation);//TODO
        }
    }

    protected class NextSignalListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            locomotive.setLocation(nextLocation);//TODO
        }
    }


    private void stopTimerStart() {
    //todo
    }





}

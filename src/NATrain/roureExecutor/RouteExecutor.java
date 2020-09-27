package NATrain.roureExecutor;

import NATrain.routes.Route;
import NATrain.trackSideObjects.TrackSection;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RouteExecutor extends Thread {

    Lock lock = new ReentrantLock();

    public void execute(Route route) {

    }

    private class StateListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            lock.unlock();
        }

    }
}







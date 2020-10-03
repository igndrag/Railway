package NATrain.executors;

import NATrain.routes.Route;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RouteExecutor extends Thread {

    Lock lock = new ReentrantLock();

    public RouteExecutor (Route route) {
        
    }

    @Override
    public void run() {
        super.run();

    }

    private class StateListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            lock.unlock();
        }

    }
}

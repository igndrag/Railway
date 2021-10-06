package NATrain.trackSideObjects;

import java.beans.PropertyChangeListener;

public interface Listenable {
    public void addPropertyChangeListener(PropertyChangeListener listener);
    public void removePropertyChangeListener(PropertyChangeListener listener);
}

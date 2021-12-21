package NATrain.trackSideObjects;

import java.beans.PropertyChangeListener;

public interface Listenable {
    void deactivateListeners();
    public void addPropertyChangeListener(PropertyChangeListener listener);
    public void removePropertyChangeListener(PropertyChangeListener listener);
}

package NATrain.routes;

import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.trackSections.TrackSection;

public class ArrivalDepartureTrack extends TrackSection {

    private Signal evenDirectionSignal;
    private Signal oddDirectionSignal;
    public ArrivalDepartureTrack(String id) {
        super(id);
    }

    public Signal getEvenDirectionSignal() {
        return evenDirectionSignal;
    }

    public void setEvenDirectionSignal(Signal evenDirectionSignal) {
        this.evenDirectionSignal = evenDirectionSignal;
    }

    public Signal getOddDirectionSignal() {
        return oddDirectionSignal;
    }

    public void setOddDirectionSignal(Signal oddDirectionSignal) {
        this.oddDirectionSignal = oddDirectionSignal;
    }
}

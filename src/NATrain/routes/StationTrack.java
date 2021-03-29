package NATrain.routes;

import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.trackSections.TrackSection;

public class StationTrack extends TrackSection {

    public static final String INITIAL_STATION_TRACK_NAME = "New Station Track";



    private Signal evenSignal;
    private Signal oddSignal;
    public StationTrack(String id) {
        super(id);
    }

    public Signal getEvenSignal() {
        return evenSignal;
    }

    public void setEvenSignal(Signal evenSignal) {
        this.evenSignal = evenSignal;
    }

    public Signal getOddSignal() {
        return oddSignal;
    }

    public void setOddSignal(Signal oddSignal) {
        this.oddSignal = oddSignal;
    }
}

package NATrain.routes;

import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.TrackSection;

import java.io.Serializable;

public class TrackBlockSection implements Serializable {
    static final long serialVersionUID = 1L;

    private final TrackSection section;
    private Signal normalDirectionSignal;
    private Signal reversedDirectionSignal;
    private Boolean bidirectional = false;

    public TrackBlockSection(TrackSection section) {
        this.section = section;
    }

    public TrackSection getSection() {
        return section;
    }

    public Signal getNormalDirectionSignal() {
        return normalDirectionSignal;
    }

    public Signal getReversedDirectionSignal() {
        return reversedDirectionSignal;
    }

    public Boolean getBidirectional() {
        return bidirectional;
    }

    public String getId() {
        return section.getId();
    }

    public void setNormalDirectionSignal(Signal normalDirectionSignal) {
        this.normalDirectionSignal = normalDirectionSignal;
    }

    public void setReversedDirectionSignal(Signal reversedDirectionSignal) {
        this.reversedDirectionSignal = reversedDirectionSignal;
    }

    public void setBidirectional(Boolean bidirectional) {
        this.bidirectional = bidirectional;
    }
}

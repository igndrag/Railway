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

    public TrackBlockSection(TrackSection section, Signal normalDirectionSignal, Signal reversedDirectionSignal, Boolean bidirectional) {
        this.section = section;
        this.normalDirectionSignal = normalDirectionSignal;
        if (bidirectional)
        this.reversedDirectionSignal = reversedDirectionSignal;
        this.bidirectional = bidirectional;
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
}

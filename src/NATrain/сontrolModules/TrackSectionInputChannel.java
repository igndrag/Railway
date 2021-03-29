package NATrain.сontrolModules;

import NATrain.trackSideObjects.TracksideObject;

public class TrackSectionInputChannel extends InputChannel {
    private TrackSectionInputType inputType;
    private int positionInSection;

    public TrackSectionInputChannel(int positionInSection, TrackSectionInputType trackSectionInputType, TracksideObject tracksideObject) {
        super(InputChannelType.TRACK_SECTION, tracksideObject);
        this.positionInSection = positionInSection;
        this.inputType = trackSectionInputType;
    }

    public TrackSectionInputType getType() {
        return inputType;
    }

    @Override
    public String toString() {
        return inputType + "_" + positionInSection;
    }
}

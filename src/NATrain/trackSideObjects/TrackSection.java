package NATrain.trackSideObjects;

import NATrain.controller.TrackSectionState;


public class TrackSection extends TracksideObject {

    public static final TrackSection EMPTY_TRACK_SECTION = new TrackSection("");

    private TrackSectionState vacancyState = TrackSectionState.UNDEFINED;

    private boolean interlocked = false;

    public TrackSection(String id) {
        super(id);
    }

    public boolean isInterlocked() {
        return interlocked;
    }

    public void setInterlocked(boolean interlocked) {
        this.interlocked = interlocked;
    }

    public TrackSectionState getVacancyState() {
        return vacancyState;
    }

    public void setVacancyState(TrackSectionState vacancyState) {
        this. vacancyState = vacancyState;
    }
 }

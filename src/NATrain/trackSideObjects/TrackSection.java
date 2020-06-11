package NATrain.trackSideObjects;

import NATrain.controller.TrackSectionState;


public class TrackSection extends TrackSideObject {

    private TrackSectionState vacancyState = TrackSectionState.UNDEFINED;
    private boolean interlocked;


    public TrackSectionState getVacancyState() {
        return vacancyState;
    }

    public void setVacancyState(TrackSectionState vacancyState) {
        this. vacancyState = vacancyState;
    }
}

package NATrain.quads;

import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import NATrain.utils.QuadFactory;
import java.io.Serializable;

public class QuadDTO implements Serializable {
    static final long serialVersionUID = 1L;
    int x;
    int y;
    private QuadType quadType;
    private final TrackSection firstAssociatedTrack;
    private final TrackSection secondAssociatedTrack;
    private final Switch associatedSwitch;
    private final Signal associatedSignal;
    private boolean descriptionShown = false;



    private QuadDTO (BaseQuad originalQuad) {
        this.x = originalQuad.x;
        this.y = originalQuad.y;
        this.quadType = originalQuad.quadType;

        if (originalQuad.descriptionLabel != null && originalQuad.descriptionLabel.isVisible()) {
            descriptionShown = true;
        }

        if (originalQuad.firstAssociatedTrack == TrackSection.EMPTY_TRACK_SECTION)
            this.firstAssociatedTrack = null;
        else
            this.firstAssociatedTrack = originalQuad.firstAssociatedTrack;

        if (originalQuad.secondAssociatedTrack == TrackSection.EMPTY_TRACK_SECTION)
            this.secondAssociatedTrack = null;
        else
            this.secondAssociatedTrack = originalQuad.secondAssociatedTrack;

        if (originalQuad.associatedSwitch == Switch.EMPTY_SWITCH)
            this.associatedSwitch = null;
        else
            this.associatedSwitch = originalQuad.associatedSwitch;

        if (originalQuad.associatedSignal == Signal.EMPTY_SIGNAL)
            this.associatedSignal = null;
        else
            this.associatedSignal = originalQuad.associatedSignal;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static QuadDTO castToDTO(Quad quad) {
        return new QuadDTO ((BaseQuad) quad);
    }

    public static Quad castToQuad(QuadDTO quadDTO) {
        BaseQuad baseQuad = (BaseQuad) QuadFactory.createQuad(quadDTO.x, quadDTO.y, quadDTO.quadType);
        if (quadDTO.firstAssociatedTrack == null)
            baseQuad.firstAssociatedTrack = TrackSection.EMPTY_TRACK_SECTION;
        else
            baseQuad.firstAssociatedTrack = quadDTO.firstAssociatedTrack;

        if (quadDTO.secondAssociatedTrack == null)
            baseQuad.secondAssociatedTrack = TrackSection.EMPTY_TRACK_SECTION;
        else
            baseQuad.secondAssociatedTrack = quadDTO.secondAssociatedTrack;

        if (quadDTO.associatedSwitch == null)
            baseQuad.associatedSwitch = Switch.EMPTY_SWITCH;
        else
            baseQuad.associatedSwitch = quadDTO.associatedSwitch;

        if (quadDTO.associatedSignal == null)
            baseQuad.associatedSignal = Signal.EMPTY_SIGNAL;
        else
            baseQuad.associatedSignal = quadDTO.associatedSignal;

        if (!quadDTO.descriptionShown) {
            baseQuad.showDescription(false);
        }

        return baseQuad;
    }
}

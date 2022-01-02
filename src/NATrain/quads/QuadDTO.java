package NATrain.quads;

import NATrain.quads.configurableInterfaces.Configurable;
import NATrain.quads.custom.ServoQuad;
import NATrain.routes.Trackline;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.utils.QuadFactory;
import java.io.Serializable;

public class QuadDTO implements Serializable {
    static final long serialVersionUID = 1L;
    int x;
    int y;
    private QuadType quadType;
    private final Trackline trackline;
    private final TrackSection firstAssociatedTrack;
    private final TrackSection secondAssociatedTrack;
    private final Switch associatedSwitch;
    private final Signal associatedSignal;
    private boolean descriptionShown = false;
    private boolean borderShown = false;
    private final Object customObject;


    private QuadDTO (BaseQuad originalQuad) {
        this.x = originalQuad.x;
        this.y = originalQuad.y;
        this.quadType = originalQuad.quadType;
        this.customObject = originalQuad.getCustomObject();
        this.borderShown = originalQuad.isBorderShown();

        if (originalQuad.descriptionLabel != null && originalQuad.descriptionLabel.isVisible()) {
            descriptionShown = true;
        }

        if (originalQuad.trackline == Trackline.EMPTY_TRACKLINE) {
            this.trackline = null;
        } else {
            this.trackline = originalQuad.trackline;
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
        if (quadDTO.trackline == null) {
            baseQuad.trackline = Trackline.EMPTY_TRACKLINE;
        } else {
            baseQuad.trackline = quadDTO.trackline;
        }
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

        baseQuad.showDescription(quadDTO.descriptionShown);

        baseQuad.showTrackBorder(quadDTO.borderShown);

        baseQuad.setCustomObject(quadDTO.customObject);

        return baseQuad;
    }
}

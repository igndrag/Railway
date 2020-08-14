package NATrain.quads;

import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import NATrain.utils.QuadFactory;
import java.io.Serializable;

public class QuadDTO implements Serializable {
    int x;
    int y;
    QuadType quadType;
    private final TrackSection firstAssociatedTrack;
    private final TrackSection secondAssociatedTrack;
    private final Switch associatedSwitch;
    private final Signal associatedSignal;

    private QuadDTO (BaseQuad originalQuad) {
        this.x = originalQuad.x;
        this.y = originalQuad.y;
        this.quadType = originalQuad.quadType;
        this.firstAssociatedTrack = originalQuad.firstAssociatedTrack;
        this.secondAssociatedTrack = originalQuad.secondAssociatedTrack;
        this.associatedSwitch = originalQuad.associatedSwitch;
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
        baseQuad.firstAssociatedTrack = quadDTO.firstAssociatedTrack;
        baseQuad.secondAssociatedTrack = quadDTO.secondAssociatedTrack;
        baseQuad.associatedSwitch = quadDTO.associatedSwitch;
        baseQuad.associatedSignal = quadDTO.associatedSignal;
        return baseQuad;
    }
}

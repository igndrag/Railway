package NATrain.quads;

import NATrain.routes.TrackBlockSection;
import NATrain.trackSideObjects.Signal;
import NATrain.utils.QuadFactory;

import java.io.Serializable;

public class TrackQuadDTO implements Serializable {
    //TODO 

    static final long serialVersionUID = 1L;
    int x;
    int y;
    private QuadType quadType;
    private TrackBlockSection firstBlockSection = TrackBlockSection.EMPTY_BLOCK_SECTION;
    private TrackBlockSection secondBlockSection = TrackBlockSection.EMPTY_BLOCK_SECTION;
    private Signal firstSignal = Signal.EMPTY_SIGNAL;
    private Signal secondSignal = Signal.EMPTY_SIGNAL;
    private boolean descriptionShown = false;
    private boolean reversedSignalView = false;

    private TrackQuadDTO(TrackBaseQuad originalQuad) {
        this.x = originalQuad.x;
        this.y = originalQuad.y;
        this.quadType = originalQuad.quadType;

        this.reversedSignalView = originalQuad.reversedSignalView;

        if (originalQuad.blockSectionName != null && originalQuad.blockSectionName.isVisible()) {
            descriptionShown = true;
        }

        if (originalQuad.firstBlockSection == TrackBlockSection.EMPTY_BLOCK_SECTION) {
            this.firstBlockSection = null;
        } else {
            this.firstBlockSection = originalQuad.firstBlockSection;
        }

        if (originalQuad.secondBlockSection == TrackBlockSection.EMPTY_BLOCK_SECTION) {
            this.secondBlockSection = null;
        } else {
            this.secondBlockSection = originalQuad.secondBlockSection;
        }

        if (originalQuad.firstSignal == Signal.EMPTY_SIGNAL) {
            this.firstSignal = null;
        } else {
            this.firstSignal = originalQuad.firstSignal;
        }
        if (originalQuad.secondSignal == Signal.EMPTY_SIGNAL) {
            this.secondSignal = null;
        } else {
            this.secondSignal = originalQuad.secondSignal;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static TrackQuadDTO castToDTO(Quad quad) {
        return new TrackQuadDTO((TrackBaseQuad) quad);
    }

    public static Quad castToQuad(TrackQuadDTO quadDTO) {
        TrackBaseQuad baseQuad = (TrackBaseQuad) QuadFactory.createQuad(quadDTO.x, quadDTO.y, quadDTO.quadType);
        if (quadDTO.descriptionShown) {
            baseQuad.showBlockSectionName(true);
        }

        if (quadDTO.firstBlockSection == null) {
            baseQuad.firstBlockSection = TrackBlockSection.EMPTY_BLOCK_SECTION;
        } else {
            baseQuad.setFirstBlockSection(quadDTO.firstBlockSection);
        }

        if (quadDTO.secondBlockSection == null) {
            baseQuad.secondBlockSection = TrackBlockSection.EMPTY_BLOCK_SECTION;
        } else {
            baseQuad.secondBlockSection = quadDTO.secondBlockSection;
        }

        if (quadDTO.firstSignal == null) {
            baseQuad.firstSignal = Signal.EMPTY_SIGNAL;
        } else {
            baseQuad.firstSignal = quadDTO.firstSignal;
        }

        if (quadDTO.secondSignal == null) {
            baseQuad.secondSignal = Signal.EMPTY_SIGNAL;
        } else {
            baseQuad.secondSignal = quadDTO.secondSignal;
        }

        baseQuad.reversedSignalView = quadDTO.reversedSignalView;
        return baseQuad;
    }
}

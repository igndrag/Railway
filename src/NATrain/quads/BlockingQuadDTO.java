package NATrain.quads;

import NATrain.routes.Trackline;
import NATrain.routes.TracklineBlockSection;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.utils.QuadFactory;

import java.io.Serializable;

public class BlockingQuadDTO implements Serializable {
    //TODO 

    static final long serialVersionUID = 1L;
    int x;
    int y;
    private QuadType quadType;
    private Trackline trackline;
    private TracklineBlockSection firstBlockSection = TracklineBlockSection.EMPTY_BLOCK_SECTION;
    private TracklineBlockSection secondBlockSection = TracklineBlockSection.EMPTY_BLOCK_SECTION;
    private Signal firstSignal = Signal.EMPTY_SIGNAL;
    private Signal secondSignal = Signal.EMPTY_SIGNAL;
    private boolean descriptionShown = false;
    private boolean reversedSignalView = false;

    private BlockingQuadDTO(BlockingBaseQuad originalQuad) {
        this.x = originalQuad.x;
        this.y = originalQuad.y;
        this.quadType = originalQuad.quadType;

        this.reversedSignalView = originalQuad.reversedSignalView;

        if (originalQuad.blockSectionName != null && originalQuad.blockSectionName.isVisible()) {
            descriptionShown = true;
        }

        if (originalQuad.trackline == Trackline.EMPTY_TRACKLINE) {
            this.trackline = null;
        } else {
            this.trackline = originalQuad.trackline;
        }

        if (originalQuad.firstBlockSection == TracklineBlockSection.EMPTY_BLOCK_SECTION) {
            this.firstBlockSection = null;
        } else {
            this.firstBlockSection = originalQuad.firstBlockSection;
        }

        if (originalQuad.secondBlockSection == TracklineBlockSection.EMPTY_BLOCK_SECTION) {
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

    public static BlockingQuadDTO castToDTO(Quad quad) {
        return new BlockingQuadDTO((BlockingBaseQuad) quad);
    }

    public static Quad castToQuad(BlockingQuadDTO quadDTO) {
        BlockingBaseQuad baseQuad = (BlockingBaseQuad) QuadFactory.createQuad(quadDTO.x, quadDTO.y, quadDTO.quadType);

        baseQuad.showBlockSectionName (quadDTO.descriptionShown);


        if (quadDTO.trackline == null) {
            baseQuad.trackline = Trackline.EMPTY_TRACKLINE;
        } else {
            baseQuad.trackline = quadDTO.trackline;
        }

        if (quadDTO.firstBlockSection == null) {
            baseQuad.firstBlockSection = TracklineBlockSection.EMPTY_BLOCK_SECTION;
        } else {
            baseQuad.firstBlockSection = quadDTO.firstBlockSection;
        }

        if (quadDTO.secondBlockSection == null) {
            baseQuad.secondBlockSection = TracklineBlockSection.EMPTY_BLOCK_SECTION;
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

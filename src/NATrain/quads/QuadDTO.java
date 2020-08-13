package NATrain.quads;

import NATrain.trackSideObjects.TrackSideObject;

import java.util.List;

public class QuadDTO {
    int x;
    int y;
    List<TrackSideObject> trackSideObjects;
    QuadType quadType;

    public static QuadDTO getDTO(Quad quad) {
        AbstractQuad originalQuad = (AbstractQuad) quad;
        QuadDTO quadDTO = new QuadDTO();
        quadDTO.x = originalQuad.x;
        quadDTO.y = originalQuad.y;
        return null;
    }
}

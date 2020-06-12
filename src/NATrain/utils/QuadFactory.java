package NATrain.utils;

import NATrain.library.QuadType;
import NATrain.model.Model;
import NATrain.quads.QuadImpl;

public class QuadFactory {
    private static int counter = 0;

    public static QuadImpl createQuad (int x, int y, QuadType quadType) {
        QuadImpl resultQuad = new QuadImpl(x, y);
        resultQuad.setQuadType(quadType);
        String id = quadType.toString() + "_00" + counter;
        resultQuad.setId(id);
        counter++;
        QuadPainter.paintQuadViewForType(resultQuad, quadType);
        Model.getNotEmptyQuads().put(id, resultQuad);
        return resultQuad;
    }


}

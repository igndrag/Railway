package NATrain.utils;

import NATrain.quads.BaseQuad;
import NATrain.quads.QuadType;
import NATrain.quads.STQ.STQ1_1;

public class SimpleTrackQuadFactory {
    public static BaseQuad createQuad (int x, int y, QuadType quadType) {
        switch (quadType) {
            case STQ1_1:
                return new STQ1_1(x, y);
            case STQ1_2:
                return null;
        }
    return null;
    }
}

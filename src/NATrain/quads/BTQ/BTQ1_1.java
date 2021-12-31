package NATrain.quads.BTQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.BlockingTrackQuad;
import NATrain.quads.QuadType;
import NATrain.quads.SimpleTrackQuad;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BTQ1_1 extends BlockingTrackQuad {

    public BTQ1_1(int x, int y) {
        super(x, y);
        quadType = QuadType.BTQ1_1;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(0, 30, 90 , 20);
        firstTrackElement.setFill(BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
        blockSectionName = new Text("");
        blockSectionName.setTextAlignment(TextAlignment.CENTER);
        blockSectionName.setWrappingWidth(90);
        blockSectionName.setX(0);
        blockSectionName.setY(25);
        addToQuadView(blockSectionName);
    }
}

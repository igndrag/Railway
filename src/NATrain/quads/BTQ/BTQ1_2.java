package NATrain.quads.BTQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.BlockingTrackQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BTQ1_2 extends BlockingTrackQuad {

    public BTQ1_2(int x, int y) {
        super(x, y);
        quadType = QuadType.BTQ1_2;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Rectangle(35, 0, 20, 80);
        firstTrackElement.setFill(BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);
        blockSectionName = new Text();
        blockSectionName.setTextAlignment(TextAlignment.LEFT);
        blockSectionName.setWrappingWidth(30);
        blockSectionName.setX(60);
        blockSectionName.setY(40);
        addToQuadView(blockSectionName);
    }
}

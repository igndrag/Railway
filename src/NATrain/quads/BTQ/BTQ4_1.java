package NATrain.quads.BTQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.BlockingTrackQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BTQ4_1 extends BlockingTrackQuad {

    public BTQ4_1(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.BTQ4_1;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                0, 30,
                0, 50,
                20, 50,
                35, 65,
                35, 80,
                55, 80,
                55, 60,
                25, 30,
                0, 30
        );
        firstTrackElement.setFill(BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        blockSectionName = new Text("");
        blockSectionName.setTextAlignment(TextAlignment.LEFT);
        blockSectionName.setWrappingWidth(90);
        blockSectionName.setX(0);
        blockSectionName.setY(20);
        addToQuadView(blockSectionName);
    }
}

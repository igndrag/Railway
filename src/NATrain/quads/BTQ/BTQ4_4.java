package NATrain.quads.BTQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.BlockingTrackQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BTQ4_4 extends BlockingTrackQuad {

    public BTQ4_4(int x, int y) {
        super(x, y);
        paintView();
        quadType = QuadType.BTQ4_4;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                35, 0,
                55, 0,
                55, 15,
                70, 30,
                90, 30,
                90, 50,
                65, 50,
                35, 20,
                35, 0
        );
        firstTrackElement.setFill(BaseQuad.TRACK_UNDEFINED_ELEMENT_COLOR);
        addToQuadView(firstTrackElement);

        blockSectionName = new Text("");
        blockSectionName.setTextAlignment(TextAlignment.RIGHT);
        blockSectionName.setWrappingWidth(80);
        blockSectionName.setX(10);
        blockSectionName.setY(20);
        addToQuadView(blockSectionName);
    }
}

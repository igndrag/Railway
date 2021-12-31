package NATrain.quads.BTQ;

import NATrain.quads.BaseQuad;
import NATrain.quads.BlockingTrackQuad;
import NATrain.quads.QuadType;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BTQ4_2 extends BlockingTrackQuad {

    public BTQ4_2(int x, int y) {
        super(x, y);
        quadType = QuadType.BTQ4_2;
    }

    @Override
    public void paintView() {
        firstTrackElement = new Polygon(
                90, 30,
                90, 50,
                70, 50,
                55, 65,
                55, 80,
                35, 80,
                35, 60,
                65, 30,
                90, 30
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

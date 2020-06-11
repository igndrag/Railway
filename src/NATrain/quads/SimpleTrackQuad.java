package NATrain.quads;


import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


public class SimpleTrackQuad extends AbstractQuad {
    private String quadType;
    private Shape trackElement;

    public SimpleTrackQuad(int x, int y, Boolean bordered) {
        super(x, y, bordered);
    }


    @Override
    public void select() {

    }

    @Override
    public void refresh() {
       // if (associatedTrackSection.getVacancyState().equals(TrackSectionState.FREE))
        trackElement.setFill(Color.GREEN);
    }
}

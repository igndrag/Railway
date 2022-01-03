package NATrain.trackSideObjects.customObjects;

public class RoadCrossing  extends AbstractCustomObject {
    static final long serialVersionUID = 1L;
    public static final String INITIAL_ROAD_CROSSING_NAME = "New road crossing";

    public RoadCrossing(String id) {
        super(id);
        this.type = CustomObjectType.ROAD_CROSSING;
    }


}

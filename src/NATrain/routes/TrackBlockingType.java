package NATrain.routes;

public enum TrackBlockingType {


    SEMI_AUTOMATIC_BLOCKING("Semiautomatic Blocking"),
    AUTOMATIC_THREE_SIGNAL_BLOCKING ("Three Signal Automatic Blocking"),
    ;

    private final String description;

    TrackBlockingType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

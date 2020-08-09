package NATrain.quads.configurableInterfaces;

public interface Configurable {
    boolean hasDescription();
    boolean hasBorder();
    void showDescription(boolean show);
    void showTrackBorder (boolean show);
    boolean isDescriptionShown();
    boolean isBorderShown();
}

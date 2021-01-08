package NATrain.UI.workPlace;
import NATrain.quads.AbstractQuad;
import NATrain.quads.Quad;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Blinker {
    private static Timeline blinker;
    private static final List<Quad> quadsWithBlinkers = new CopyOnWriteArrayList<Quad>();

    static {
        blinker = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            AbstractQuad.blink();
                            quadsWithBlinkers.forEach(Quad::updateSignalView);
                        }
                ));
        blinker.setCycleCount(Timeline.INDEFINITE);
    }

    public static void start() {
        blinker.play();
    }

    public static void stop() {
        quadsWithBlinkers.clear();
        blinker.pause();
    }

    public static void registerQuad(Quad quad) {
        quadsWithBlinkers.add(quad);
    }

    public static void unregisterQuad (Quad quad) {
        quadsWithBlinkers.remove(quad);
    }
}

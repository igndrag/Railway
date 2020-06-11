package NATrain.library;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class QuadLibrary {
    private static Map<SimpleTrackQuadType, Image> SimpleTrackQuadLib;


    static {
        SimpleTrackQuadLib = new HashMap<>();
        EnumSet.allOf(SimpleTrackQuadType.class).forEach(simpleTrackQuadType -> {
            try (FileInputStream inputStream = new FileInputStream(String.format("src/images/track/%s.png", simpleTrackQuadType))){
                SimpleTrackQuadLib.put(simpleTrackQuadType, new Image(inputStream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static Map<SimpleTrackQuadType, Image> getSimpleTrackQuadLib() {
        return SimpleTrackQuadLib;
    }
}

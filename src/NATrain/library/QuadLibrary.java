package NATrain.library;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class QuadLibrary {
    private static SortedMap<QuadType, Image> SimpleTrackQuadImgLib;


    static {
        SimpleTrackQuadImgLib = new TreeMap<>();
        EnumSet.allOf(QuadType.class).forEach(simpleTrackQuadType -> {
            try (FileInputStream inputStream = new FileInputStream(String.format("src/images/track/%s.png", simpleTrackQuadType))){
                SimpleTrackQuadImgLib.put(simpleTrackQuadType, new Image(inputStream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static Map<QuadType, Image> getSimpleTrackQuadImgLib() {
        return SimpleTrackQuadImgLib;
    }
}

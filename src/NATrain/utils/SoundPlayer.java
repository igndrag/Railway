package NATrain.utils;

import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.File;
import java.io.FileInputStream;

public class SoundPlayer {

    public static final String DEFAULT_SWITCH_CHANGE_POSITION_SOUND = "/sounds/strelka.mp3";
    public static final String DEFAULT_DIESEL_LOCOMOTIVE_SOUND = "/sounds/diesel.mp3";


    private static AdvancedPlayer backgroundPlayer;
    private static String backgroundSoundFolder = "/sounds/diesel.mp3";

    public static void playBackgroundSound (String folder) {
        try{
            backgroundPlayer = new AdvancedPlayer(SoundPlayer.class.getResourceAsStream(backgroundSoundFolder));
            backgroundPlayer.play(Integer.MAX_VALUE);
        }
        catch(Exception exc){
            exc.printStackTrace();
            System.out.println("Failed to play the file.");
        }
    }

        public static void playSound(String folder) {
        try{
            Player playMP3 = new Player(SoundPlayer.class.getResourceAsStream("/sounds/horn.mp3"));
            playMP3.play();
        }
        catch(Exception exc){
            exc.printStackTrace();
            System.out.println("Failed to play the file.");
        }
    }
}

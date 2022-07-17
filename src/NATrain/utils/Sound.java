package NATrain.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Objects;


public class Sound {

    private Clip clip;
    private static Clip attention;
    private static Sound testSound;
    private static LineListener testListener;

    public static Sound getTestSound() {
        return testSound;
    }

    static {


        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream("/sounds/attention_sound.wav"));
            // load the sound into memory (a Clip)
            attention = AudioSystem.getClip();
            attention.open(sound);

            testSound = new Sound("/sounds/fake_voice.wav", 0.4f);
            testListener = new SoundCompletedListener(testSound, false);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testSound() {
        attention.addLineListener(testListener);
        attention.setFramePosition(0);
        attention.start();
    }

    public Sound(String fileName, float volume) {

        try {
                BufferedInputStream stream = new BufferedInputStream(Objects.requireNonNull(Sound.class.getResourceAsStream(fileName)));
                AudioInputStream sound = AudioSystem.getAudioInputStream(stream);
                // load the sound into memory (a Clip)
                clip = AudioSystem.getClip();
                clip.open(sound);
                setVolume(volume);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play(){
        if (!clip.isRunning()) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void playAfterAttentionSound() {
        attention.addLineListener(new SoundCompletedListener(this, false));
        attention.setFramePosition(0);
        attention.start();
    }

    public void playAfterAttentionSound(Sound sound) {
        attention.start();
        try {
            clip.wait(attention.getMicrosecondLength());
            clip.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }

    private static class SoundCompletedListener implements LineListener {

        private final Sound sound;
        private final boolean looped;

        public SoundCompletedListener(Sound sound, boolean looped) {
            this.sound = sound;
            this.looped = looped;
        }

        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.STOP) {
                if (looped) {
                    sound.loop();
                } else {
                    sound.play();
                }
                attention.removeLineListener(this);
            } else {
                return;
            }
        }
    }

    public static void createPlaylistAndStart(Sound...sounds) {
        for (int i = 0; i < sounds.length; i++) {
            if (i < sounds.length - 1) {
                sounds[i].clip.addLineListener(new SoundCompletedListener(sounds[i + 1], false));
            }
        }
        sounds[0].play();
    }

    public static void playSoundAndLoop(Sound firstSound, Sound loopedSound) {
        firstSound.clip.addLineListener(new SoundCompletedListener(loopedSound, true));
        firstSound.play();
    }

    public Clip getClip() {
        return clip;
    }

    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}
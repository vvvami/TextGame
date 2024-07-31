package net.vami.game.display.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    public static final String DIRECTORY = "assets/sounds/";

    private final File soundFile;
    private final AudioInputStream audioStream;

    public Sound(String fileName) {
        this.soundFile = new File(DIRECTORY + fileName + ".wav");
        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void play() {
        Clip audioClip = null;

        try {
            audioClip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        try {
            audioClip.open(audioStream);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        audioClip.start();
    }

    public static final Sound ATTACK = new Sound("metal_hit");
}


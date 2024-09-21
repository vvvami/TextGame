package net.vami.game.display.sound;

import net.vami.TextGame;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    private static final String DIRECTORY = "/assets/sounds/";
    private URL soundURL;
    Clip audioClip;
    FloatControl volumeControl;
    float volume;

    public Sound(String fileName) {
            this.soundURL = getClass().getResource(DIRECTORY + fileName + ".wav");
    }

    public Sound get(int volume) {
        AudioInputStream audioStream;
        try {
            audioStream = AudioSystem.getAudioInputStream(soundURL);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            volumeControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            this.setVolume(volume);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public void play() {
        audioClip.start();
    }

    public void stop() {
        audioClip.stop();
    }

    public void loop() {
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void loop(int count) {
        audioClip.loop(count);
    }

    public void setVolume(int volume) {
        float minVolume = -80f;
        float volumeStep = (float) 86 / 100;

        if (volume <= 0) {
            this.volume = -80f;
        }
        else {
            volume = Math.min(100, volume);
            this.volume = (float) Math.min(6, minVolume + (volumeStep * volume * 1.15));
        }
        volumeControl.setValue(this.volume);
    }

    public static final Sound HUDDLED = new Sound("huddled");
    public static final Sound METAL_HIT = new Sound("metal_hit");
    public static final Sound SHARP_DAMAGE = new Sound("sharp_damage");
    public static final Sound BLUNT_DAMAGE = new Sound("blunt_damage");
    public static final Sound FIRE_DAMAGE = new Sound("fire_damage");
    public static final Sound ICE_DAMAGE = new Sound("ice_damage");
    public static final Sound BLEED_DAMAGE = new Sound("bleed_damage");
    public static final Sound HEAL = new Sound("heal");
    public static final Sound ITEM_BREAK = new Sound("item_break");

}


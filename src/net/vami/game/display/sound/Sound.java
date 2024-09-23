package net.vami.game.display.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Sound {
    private static final String DIRECTORY = "/assets/sounds/";
    URL soundURL;
    private Clip audioClip;
    private FloatControl volumeControl;
    private float volumeFloat;
    private SoundType soundType;
    private static ArrayList<Sound> sounds = new ArrayList<>();

    public Sound(String fileName, SoundType type) {
        soundURL = getClass().getResource(DIRECTORY + fileName + ".wav");
        this.soundType = type;
        sounds.add(this);
        AudioInputStream audioStream;
        try {
            audioStream = AudioSystem.getAudioInputStream(soundURL);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            volumeControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public SoundType getSoundType() {
        return soundType;
    }

    private void registerClip() {
        AudioInputStream audioStream;
        try {
            audioStream = AudioSystem.getAudioInputStream(soundURL);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            volumeControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public Clip getClip() {
        return audioClip;
    }

    public static ArrayList<Sound> getSounds() {
        return sounds;
    }

    public void play(int volume) {
        if (audioClip.isActive()) {
            registerClip();
        } else {
            audioClip.setMicrosecondPosition(0);
        }
        this.setVolumeFloat(volume);
        audioClip.start();
    }

    public void stop() {
        audioClip.stop();
        audioClip.flush();
    }

    public void loop() {
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void loop(int count) {
        audioClip.loop(count);
    }

    public void reset() {
        audioClip.setMicrosecondPosition(0);
    }

    public void setVolumeFloat(int volumeFloat) {
        float minVolume = -50f;
        float volumeStep = (float) 56 / 100;

        if (volumeFloat <= 0) {
            this.volumeFloat = -80f;
        }
        else {
            volumeFloat = Math.min(100, volumeFloat);
            this.volumeFloat = (float) Math.min(6, minVolume + (volumeStep * volumeFloat * 1.15));
        }
        volumeControl.setValue(this.volumeFloat);
    }

    public boolean isPlaying() {
        return this.audioClip.isActive();
    }

    public static final Sound HUDDLED = new Sound("huddled", SoundType.MUSIC);
    public static final Sound THINK = new Sound("think", SoundType.MUSIC);

    public static final Sound METAL_HIT = new Sound("metal_hit", SoundType.EFFECT);
    public static final Sound SHARP_DAMAGE = new Sound("sharp_damage", SoundType.EFFECT);
    public static final Sound BLUNT_DAMAGE = new Sound("blunt_damage", SoundType.EFFECT);
    public static final Sound FIRE_DAMAGE = new Sound("fire_damage", SoundType.EFFECT);
    public static final Sound ICE_DAMAGE = new Sound("ice_damage", SoundType.EFFECT);
    public static final Sound BLEED_DAMAGE = new Sound("bleed_damage", SoundType.EFFECT);
    public static final Sound HEAL = new Sound("heal", SoundType.EFFECT);
    public static final Sound ITEM_BREAK = new Sound("item_break", SoundType.EFFECT);
    public static final Sound ITEM_PICKUP = new Sound("item_pickup", SoundType.EFFECT);
    public static final Sound ITEM_DROP = new Sound("item_drop", SoundType.EFFECT);
    public static final Sound ITEM_EQUIP = new Sound("item_equip", SoundType.EFFECT);
    public static final Sound WEREWOLF_DEATH = new Sound("wolf_death", SoundType.EFFECT);

}


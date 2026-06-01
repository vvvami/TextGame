package net.vami.game.display.sound;

import net.vami.game.Game;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.world.Position;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Sound {
    private static final String DIRECTORY = "/assets/sounds/";
    URL soundURL;
    private Clip audioClip;
    private FloatControl volumeControl;
    private float volumeFloat;
    private SoundType soundType;
    private static ArrayList<Sound> sounds = new ArrayList<>();

    public Sound(String fileName, SoundType type) {
        if (hasAvailableAudioOutput()) {
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

    // Credit to this stackoverflow thread for the method:
    // https://stackoverflow.com/questions/43521945/how-do-i-tell-if-the-end-user-has-a-sound-card-in-java
    private static List<Mixer> getAvailableAudioOutputs() {
        final ArrayList<Mixer> available = new ArrayList<>();
        final Mixer.Info[] devices = AudioSystem.getMixerInfo();
        final Line.Info sourceInfo = new Line.Info(SourceDataLine.class);
        for (final Mixer.Info mixerInfo : devices) {
            final Mixer mixer = AudioSystem.getMixer(mixerInfo);
            if (mixer.isLineSupported(sourceInfo)) {
                // the device supports output, add as suitable
                available.add(mixer);
            }
        }
        return available;
    }

    public static boolean hasAvailableAudioOutput() {
        return !getAvailableAudioOutputs().isEmpty();
    }


    public void playSound(Position position, int volume) {
        if (!Sound.hasAvailableAudioOutput()) {
            return;
        }

        if (position == null) {
            this.play(volume);
            return;
        }

        if (position.equals(Game.player.getPos())) {
            this.play(volume);
        }
    }

    public void playMusic(int volume) {
        if (!Sound.hasAvailableAudioOutput()) {
            return;
        }

        for (Sound sound1 : Sound.getSounds()) {
            if (sound1.getClip() != null &&
                    sound1.isPlaying() &&
                    sound1.getSoundType().equals(this.getSoundType())) {
                sound1.stop();
            }
        }
        this.play(volume);
        this.loop();
    }
}


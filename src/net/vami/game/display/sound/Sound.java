package net.vami.game.display.sound;

import net.vami.game.Game;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.world.Position;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Sound {
    private static final String DIRECTORY = "/assets/sounds/";
    URL soundURL;
    private Clip audioClip;
    private FloatControl volumeControl;
    private float volumeFloat;
    private SoundType soundType;
    private static ArrayList<Sound> sounds = new ArrayList<>();

    public static boolean ENABLED = true;

    private static final Queue<SoundRun> soundQueue = new ArrayDeque<>();
    private static final Timer soundTimer = new Timer(Game.GAME_DELAY, e -> flushNextSound());

    private static class SoundRun {
        private final Sound sound;
        private final int volume;

        private SoundRun(Sound sound, int volume) {
            this.sound = sound;
            this.volume = volume;
        }
    }

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
        if (!ENABLED) return;

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

    public void playSound(Interactable source, int volume) {
        if (!Sound.hasAvailableAudioOutput()) {
            return;
        }

        if (source instanceof ItemInstance ||
                source.getNode() == Game.getCurrentNode()) {
            this.play(volume);
        }
    }

    public void playSound(Position position, int volume, int msDelay) {
        if (!Sound.hasAvailableAudioOutput()) {
            return;
        }

        if (audioClip == null) {
            return;
        }

        if (!isAudible(position)) {
            return;
        }

        if (msDelay <= 0) {
            play(volume);
            return;
        }

        Timer timer = new Timer(msDelay, e -> play(volume));
        timer.setRepeats(false);
        timer.start();
    }

    private boolean isAudible(Position position) {
        return position == null || position.equals(Game.player.getPos());
    }

    public void playSound(Position position, int volume) {
        playSound(position, volume, 0);
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

    public void queue(int volume) {
        queue(volume, 0);
    }

    public void queue(int volume, int initialDelayMillis) {
        soundQueue.add(new SoundRun(this, volume));

        if (!soundTimer.isRunning()) {
            soundTimer.setInitialDelay(initialDelayMillis);
            soundTimer.setDelay(Game.GAME_DELAY);
            soundTimer.restart();
        }
    }

    private static void flushNextSound() {
        SoundRun nextSound = soundQueue.poll();

        if (nextSound == null) {
            soundTimer.stop();
            return;
        }

        nextSound.sound.play(nextSound.volume);
    }

    public void playAudible(Position position, int volume) {
        if (!Sound.hasAvailableAudioOutput()) {
            return;
        }

        if (audioClip == null) {
            return;
        }

        if (isAudible(position)) {
            this.play(volume);
        }
    }
}


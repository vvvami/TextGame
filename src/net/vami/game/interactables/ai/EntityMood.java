package net.vami.game.interactables.ai;

public enum EntityMood {
    FRIENDLY(1),
    NEUTRAL(0),
    HOSTILE(-1);

    private final float score;

    EntityMood(float score) {

        this.score = score;
    }

    public float get() {
        return this.score;
    }
}

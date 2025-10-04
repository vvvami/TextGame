package net.vami.game.interactables.ai;

public enum EntityMood {
    FRIENDLY(1),
    NEUTRAL(0),
    HOSTILE(-1);

    private float rating;

    EntityMood(float rating) {

        this.rating = rating;
    }

    public float get() {
        return this.rating;
    }
}

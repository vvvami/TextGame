package net.vami.game.interactables.ai;

public class EntityRating {
    private EntityMood mood;
    private float rating;

    public EntityRating(EntityMood mood, float rating) {
        this.mood = mood;
        this.rating = rating;
    }

    public EntityRating(float rating) {
        this.rating = rating;
        updateMood();
    }

    public EntityMood getMood() {
        return mood;
    }

    public float getRating() {
        return rating;
    }

    public void setMood(EntityMood mood) {
        this.mood = mood;
    }

    public void setRating(float amount) {
        rating = amount;
        updateMood();
    }

    // Automatically updates the mood based on the rating change given
    public void changeRating(float amount) {
        rating += amount;
        rating = Math.min(Math.max(rating, EntityMood.HOSTILE.get()),
                EntityMood.FRIENDLY.get());
        updateMood();
    }

    // Updates the mood based on the mood thresholds
    // FRIENDLY == 1
    // NEUTRAL == 0
    // HOSTILE == -1
    // EntityMood.MOOD.get() should always reflect the current thresholds
    // It should match up here with the switch/case
    private void updateMood() {
        byte roundedRating = (byte) Math.round(rating);

        if (mood == null) {mood = EntityMood.NEUTRAL;}

        switch (mood) {
            case FRIENDLY:
                if (rating <= 0) {mood = EntityMood.NEUTRAL;}

            case HOSTILE:
                if (rating >= 0) {mood = EntityMood.NEUTRAL;}

            case NEUTRAL:
                if (roundedRating == 1) {mood = EntityMood.FRIENDLY;}
                else if (roundedRating == -1) {mood = EntityMood.HOSTILE;}
        }
    }
}

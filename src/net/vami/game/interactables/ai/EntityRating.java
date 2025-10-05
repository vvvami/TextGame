package net.vami.game.interactables.ai;

public class EntityRating {
    private EntityMood mood;
    private float score;

    public EntityRating(EntityMood mood, float score) {
        this.mood = mood;
        this.score = score;
    }

    public EntityRating(float score) {
        this.score = score;
        updateMood();
    }

    public EntityMood getMood() {
        return mood;
    }

    public float getScore() {
        return score;
    }

    public void setMood(EntityMood mood) {
        this.mood = mood;
    }

    public void setScore(float amount) {
        score = amount;
        updateMood();
    }

    // Automatically updates the mood based on the rating change given
    public void changeScore(float amount) {
        score += amount;
        score = Math.min(Math.max(score, EntityMood.HOSTILE.get()),
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
        byte roundedScore = (byte) Math.round(score);

        if (mood == null) {mood = EntityMood.NEUTRAL;}

        switch (mood) {
            case FRIENDLY:
                if (score <= 0) {mood = EntityMood.NEUTRAL;}

            case HOSTILE:
                if (score >= 0) {mood = EntityMood.NEUTRAL;}

            case NEUTRAL:
                if (roundedScore == 1) {mood = EntityMood.FRIENDLY;}
                else if (roundedScore == -1) {mood = EntityMood.HOSTILE;}
        }
    }
}

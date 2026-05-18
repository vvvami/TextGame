package net.vami.game.interactable.entity;

import net.vami.game.display.sound.Sound;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.ai.EntityMood;
import net.vami.game.interactable.ai.tasks.Tasks;
import net.vami.game.interactable.interaction.abilities.Abilities;
import net.vami.game.interactable.interaction.damagetypes.DamageType;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.interaction.statuses.Statuses;
import org.jetbrains.annotations.Nullable;

public class AngelEntity extends Entity {
    public AngelEntity(String name, Attributes attributes) {
        super(name, attributes
                .level(5)
                .damageType(DamageTypes.FIRE)
                .ability(Abilities.PRAY));

        addResistance(DamageTypes.SHARP);
        addResistance(DamageTypes.BLEED);
        addResistance(DamageTypes.FIRE);

        addWeakness(DamageTypes.POISON);

        addImmunity(Statuses.BURNING);
        addImmunity(Statuses.FROZEN);
    }

    public AngelEntity(Attributes attributes) {
        this("Angel",  attributes);
    }

    public AngelEntity() {
        this(new Attributes());
    }

    @Override
    public void initializeBrain() {
        addTask(Tasks.TARGET_ANY_AND_ATTACK, 20);
        addTask(Tasks.SELF_ABILITY, 1);
    }

    @Override
    public void turn() {
        super.turn();
        if (getHealth() <= getMaxHealth() / 2) {
            this.getBrain().getTask(Tasks.TARGET_ANY_AND_ATTACK).setPriority(1);
        } else {
            this.getBrain().getTask(Tasks.TARGET_ANY_AND_ATTACK).setPriority(20);
        }
    }

    @Override
    public void createInteractableRating(Interactable interactable) {
        float rating = 0f;
        if (interactable instanceof AngelEntity) {
            rating = EntityMood.FRIENDLY.get();

        } else if (interactable instanceof PlayerEntity) {
            rating = EntityMood.HOSTILE.get();
        }

        createMoodRating(interactable, rating);
    }

    @Override
    public @Nullable Sound getDeathSound() {
        return Sound.HEAL;
    }

}
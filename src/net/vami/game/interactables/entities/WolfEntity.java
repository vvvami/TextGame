package net.vami.game.interactables.entities;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.ai.*;
import net.vami.game.interactables.ai.tasks.*;
import net.vami.game.interactables.interactions.abilities.Abilities;
import net.vami.game.interactables.interactions.abilities.RageAbility;
import net.vami.game.interactables.interactions.damagetypes.*;
import net.vami.game.interactables.interactions.statuses.FrozenStatus;
import net.vami.game.interactables.interactions.statuses.Statuses;
import org.jetbrains.annotations.Nullable;

public class WolfEntity extends Entity {
    public WolfEntity(String name, Attributes attributes) {
        super(name, attributes
                .level(1)
                .damageType(DamageTypes.SHARP)
                .ability(Abilities.RAGE));

        addResistance(DamageTypes.ICE);
        addWeakness(DamageTypes.FIRE);
        addImmunity(Statuses.FROZEN);
    }

    @Override
    public void initializeBrain() {
        addTask(Tasks.TARGET_ANY_AND_ATTACK, 10);
        addTask(Tasks.TAKE, 5);
    }

    @Override
    public void hurt(Interactable source, float amount, DamageType damageType) {
        super.hurt(source, amount, damageType);
        if (this.getHealth() < (float) this.getMaxHealth() / 2) {
            addTask(Tasks.ABILITY_OR_TARGET, 10);
            removeTask(Tasks.TARGET_ANY_AND_ATTACK);
            addTask(Tasks.TARGET_AND_ATTACK, 10);
            removeTask(Tasks.TAKE);
        }
    }

    @Override
    public void createInteractableRating(Interactable interactable) {
        float rating = 0f;
        if (interactable instanceof WolfEntity) {
            rating = EntityMood.FRIENDLY.get();

        } else if (interactable instanceof PlayerEntity) {
            rating = EntityMood.HOSTILE.get();
        }

        createMoodRating(interactable, rating);
    }

    @Override
    public @Nullable Sound getDeathSound() {
        return Sound.WEREWOLF_DEATH;
    }

}
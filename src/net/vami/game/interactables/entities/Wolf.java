package net.vami.game.interactables.entities;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.ai.*;
import net.vami.game.interactables.ai.tasks.*;
import net.vami.game.interactables.interactions.abilities.RageAbility;
import net.vami.game.interactables.interactions.damagetypes.FireDamage;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.damagetypes.IceDamage;
import net.vami.game.interactables.interactions.damagetypes.SharpDamage;
import net.vami.game.interactables.interactions.statuses.CharmedStatus;
import org.jetbrains.annotations.Nullable;

public class Wolf extends Entity {
    public Wolf(String name, Attributes attributes) {
        super(name, attributes
                .level(1)
                .defaultDamageType(new SharpDamage())
                .ability(new RageAbility()));

        addResistance(new IceDamage());
        addWeakness(new FireDamage());
        addImmunity(new CharmedStatus());
        setEnemy(true);
    }

    @Override
    public Brain getBrain() {
        Brain werewolfBrain = new Brain();
        werewolfBrain.addTask(new TargetAnyAndAttackTask(), 10);
        werewolfBrain.addTask(new ChaseTargetTask(), 6);
        werewolfBrain.addTask(new TakeTask(), 3);
        if (!this.hasTarget()) {
            werewolfBrain.addTask(new MoveTask(), 1);
        }

        if (this.getHealth() < (float) this.getMaxHealth() / 2) {
            werewolfBrain.addTask(new AbilityOrTargetTask(), 10);
            werewolfBrain.removeTask(new TargetAnyAndAttackTask());
            werewolfBrain.addTask(new TargetAndAttackTask(), 10);
            werewolfBrain.removeTask(new TakeTask());
        }

        return werewolfBrain;
    }

    @Override
    public void hurt(Entity source, float amount, DamageType damageType) {
        super.hurt(source, amount, damageType);
        if (Math.random() > 0.5) {
            this.setTarget(source);
        }
    }

    @Override
    public @Nullable Sound getDeathSound() {
        return Sound.WEREWOLF_DEATH;
    }

}
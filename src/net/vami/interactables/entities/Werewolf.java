package net.vami.interactables.entities;
import net.vami.interactables.ai.*;
import net.vami.interactables.ai.tasks.*;
import net.vami.interactables.interactions.abilities.RageAbility;
import net.vami.interactables.interactions.damagetypes.FireDamage;
import net.vami.interactables.interactions.damagetypes.DamageType;
import net.vami.interactables.interactions.damagetypes.IceDamage;
import net.vami.interactables.interactions.damagetypes.SharpDamage;

public class Werewolf extends Entity {
    public Werewolf(String name, Attributes attributes) {
        super(name, attributes
                .level(1)
                .defaultDamageType(new SharpDamage())
                .ability(new RageAbility()));

        addResistance(new IceDamage());
        addWeakness(new FireDamage());
    }

    @Override
    public Brain getBrain() {
        Brain werewolfBrain = new Brain();
        werewolfBrain.addTask(new TargetAnyAndAttackTask(), 20);
        werewolfBrain.addTask(new EquipTask(), 1);

        if (this.getHealth() < (float) this.getMaxHealth() / 2) {
            werewolfBrain.addTask(new SupportAbilityTask(), 20);
            werewolfBrain.removeTask(new TargetAnyAndAttackTask());
            werewolfBrain.addTask(new AttackTask(), 10);
            werewolfBrain.removeTask(new EquipTask());
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
}
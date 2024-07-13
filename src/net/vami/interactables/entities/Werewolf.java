package net.vami.interactables.entities;
import net.vami.interactables.ai.*;
import net.vami.interactables.interactions.DamageType;
import net.vami.interactables.interactions.abilities.FlamesAbility;
import net.vami.interactables.interactions.abilities.RageAbility;

public class Werewolf extends Entity {
    public Werewolf(String name, Attributes attributes) {
        super(name, attributes
                .level(1)
                .defaultDamageType(DamageType.SHARP)
                .ability(new RageAbility()));

        addResistance(DamageType.ICE);
        addWeakness(DamageType.FIRE);
    }

    @Override
    public Brain getBrain() {
        Brain werewolfBrain = new Brain();
        werewolfBrain.addTask(new AttackTask(), 20);
        werewolfBrain.addTask(new TargetTask(), 20);
        werewolfBrain.addTask(new EquipTask(), 1);

        if (this.getHealth() < (float) this.getMaxHealth() / 2) {
            werewolfBrain.addTask(new AbilityTask(), 10);
            werewolfBrain.getTask(new TargetTask()).setPriority(3);
        }

        return werewolfBrain;
    }

}
package net.vami.interactables.entities;
import net.vami.interactables.interactions.Ability;
import net.vami.interactables.interactions.DamageType;
import net.vami.interactables.interactions.abilities.FlamesAbility;

public class Werewolf extends Entity {
    public Werewolf(String name, Attributes attributes) {
        super(name, attributes
                .level(1)
                .defaultDamageType(DamageType.SHARP)
                .ability(FlamesAbility.ABILITY));

        addResistance(DamageType.ICE);
        addWeakness(DamageType.FIRE);
    }

}
package net.vami.interactables.entities;
import net.vami.interactables.interactions.DamageType;

public class Werewolf extends Entity {
    public Werewolf(String name, Attributes attributes) {
        super(name, attributes
                .level(1)
                .defaultDamageType(DamageType.SHARP));

        addResistance(DamageType.ICE);
        addWeakness(DamageType.FIRE);
    }

}
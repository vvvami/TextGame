package net.vami.interactables.entities;
import net.vami.interactables.interactions.DamageType;

public class Werewolf extends Entity {
    public Werewolf(String name, Attributes attributes) {
        super(name, attributes
                .level(1)
                .maxHealth(attributes.levelAttribute * 5)
                .armor(attributes.levelAttribute * 2)
                .baseDamage(attributes.levelAttribute * 1.5f)
                .defaultDamageType(DamageType.SHARP));

        addResistance(DamageType.ICE);
        addWeakness(DamageType.FIRE);
    }

}
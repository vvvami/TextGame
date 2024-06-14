package net.vami.interactables.entities;
import net.vami.game.interactions.Action;
import net.vami.game.interactions.DamageType;
import net.vami.game.world.Position;
import net.vami.interactables.Interactable;

public class Werewolf extends Entity {
    public Werewolf(String name, Attributes attributes) {
        super(name, attributes
                .maxHealth(attributes.levelAttribute * 10)
                .armor(attributes.levelAttribute * 2)
                .baseDamage(attributes.levelAttribute * 1.5f));

        addResistance(DamageType.ICE);
        addWeakness(DamageType.FIRE);
    }

}
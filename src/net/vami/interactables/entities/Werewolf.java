package net.vami.interactables.entities;
import net.vami.game.interactions.Ability;
import net.vami.game.interactions.DamageType;
import net.vami.game.world.Game;
import net.vami.game.world.Position;

public class Werewolf extends Entity {

    public Werewolf(String name, int level) {
        super(name, Game.player.getPosition(), level, 20 * level, 3 * level,
                2 * level, DamageType.SHARP, true, Ability.WOUND);
        addWeakness(DamageType.FIRE);
        addResistance(DamageType.ICE);
    }

}
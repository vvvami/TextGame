package net.vami.game.interactable.interaction.patrons;

import net.vami.game.interactable.entity.PlayerEntity;
import net.vami.game.interactable.interaction.abilities.Abilities;
import net.vami.game.interactable.interaction.abilities.Ability;
import net.vami.game.interactable.interaction.damagetypes.DamageType;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.item.custom.EyeOfArthuurosItem;
import net.vami.game.interactable.item.custom.TearsOfArthuurosItem;

public class Arthuuros implements Patron {
    @Override
    public String name() {
        return "Arthuuros";
    }

    @Override
    public int level() {
        return 2;
    }

    @Override
    public int maxHealth() {
        return 3;
    }

    @Override
    public int armor() {
        return 2;
    }

    @Override
    public int baseDamage() {
        return 5;
    }

    @Override
    public DamageType damageType() {
        return DamageTypes.FIRE;
    }

    @Override
    public Ability ability() {
        return Abilities.RAGE;
    }

    @Override
    public void init(PlayerEntity player) {
        player.addItem(new TearsOfArthuurosItem());
        player.addItem(new EyeOfArthuurosItem());
    }
}

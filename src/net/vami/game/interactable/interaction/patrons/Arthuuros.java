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
        return 1;
    }

    @Override
    public int maxHealth() {
        return 10;
    }

    @Override
    public int armor() {
        return 1;
    }

    @Override
    public int baseDamage() {
        return 1;
    }

    @Override
    public DamageType damageType() {
        return DamageTypes.BLUNT;
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

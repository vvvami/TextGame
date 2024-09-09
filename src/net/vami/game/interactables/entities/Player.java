package net.vami.game.interactables.entities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.ai.Brain;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.interactables.interactions.damagetypes.SharpDamage;
import net.vami.game.interactables.interactions.patrons.Patron;
import net.vami.game.world.Game;

import java.io.*;

public class Player extends Entity implements Serializable {

    private Patron patron;

    public Player(String name, Attributes attributes) {
        super(name, attributes);
        addAvailableAction(Action.SAVE);
        addReceivableAction(Action.SAVE);

//        attributes.levelAttribute = patron.level();
//        attributes.baseDamageAttribute = patron.baseDamage();
//        attributes.armorAttribute = patron.armor();
//        attributes.abilityAttribute = patron.ability();
//        attributes.damageTypeAttribute = patron.damageType();
//        attributes.maxHealthAttribute = patron.maxHealth();
//        this.heal(this, getMaxHealth());
    }

    @Override
    public Brain getBrain() {
        return null;
    }

    @Override
    public boolean receiveSave(Interactable source) {
        Entity entitySource = (Entity) source;
        if (entitySource.hasStatus()) {
            System.out.printf("%s cannot %s while afflicted... %n", entitySource.getName(), Action.SAVE.getSynonyms().stream().findAny());
            return false;
        }
        Game.saveGame();
        return super.receiveSave(source);
    }

    public Patron getPatron() {
        return patron;
    }

}

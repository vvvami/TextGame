package net.vami.game;
import net.vami.interactables.*;

public enum Ability {
    HEAL("Heal"),
    BURN("Burn"),
    FREEZE("Freeze"),
    WOUND("Wound");

    private String name;
    private String manaCost;

    Ability(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}

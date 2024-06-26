package net.vami.interactables.interactions;
import net.vami.interactables.*;
import net.vami.interactables.entities.Entity;

public enum Ability {
    NONE("None"),
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

    public static void useAbility(Interactable target, Entity source, Ability ability) {
        switch (ability) {
            case BURN: {
                StatusInstance burnAbility = new StatusInstance
                        (Status.BURNING, source.getLevel(), source.getLevel(), source);
                target.addStatus(burnAbility);
                break;
            }

            case FREEZE: {
                StatusInstance freezeAbility = new StatusInstance
                        (Status.FROZEN, source.getLevel(), source.getLevel() * 2, source);
                target.addStatus(freezeAbility);
                break;
            }

            case HEAL: {
                target.heal(source, source.getLevel() * 2);
                StatusInstance healAbility = new StatusInstance
                        (Status.BLESSED, source.getLevel(), source.getLevel(), source);
                target.addStatus(healAbility);
                break;
            }

            case WOUND: {
                StatusInstance woundAbility = new StatusInstance
                        (Status.BLEEDING, source.getLevel(), source.getLevel() * 2, source);
                target.addStatus(woundAbility);
                break;
            }
        }
    }

}

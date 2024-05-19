package net.vami.game;

import net.vami.interactables.entities.Entity;

public class AbilityInstance {
    private Entity source;
    private Entity target;
    private Ability ability;

    public AbilityInstance(Ability ability, Entity target, Entity source) {
        this.ability = ability;
        this.source = source;
        this.target = target;
        useAbility();
    }

    public void useAbility() {
        switch (ability) {
            case BURN: {
                StatusInstance burnAbility = new StatusInstance
                        (Status.BURNING, source.getLevel(), source.getLevel(), source);
                target.addStatus(burnAbility);
            }

            case FREEZE: {
                StatusInstance freezeAbility = new StatusInstance
                        (Status.FROZEN, source.getLevel(), source.getLevel() * 2, source);
                target.addStatus(freezeAbility);
            }

            case HEAL: {
                target.heal(target, source.getLevel() * 2);
                StatusInstance healAbility = new StatusInstance
                        (Status.BLESSED, source.getLevel(), source.getLevel(), source);
                target.addStatus(healAbility);
            }

            case WOUND: {
                StatusInstance woundAbility = new StatusInstance
                        (Status.BLEEDING, source.getLevel(), source.getLevel() * 2, source);
                target.addStatus(woundAbility);
            }
        }
    }

    public Entity getAbilitySource() {
        return source;
    }

    public Entity getAbilityTarget() {
        return target;
    }

    public Ability getAbility() {
        return ability;
    }

}

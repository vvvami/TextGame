package net.vami.game.interactable.entity;

import net.vami.game.interactable.ai.tasks.Tasks;
import net.vami.game.interactable.interaction.abilities.Abilities;
import net.vami.game.interactable.interaction.action.Action;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.interaction.statuses.Statuses;

public class KalakuliEntity extends Entity {
    public KalakuliEntity(String name, Attributes attributes) {
        super(name, attributes
                .level(2)
                .damageType(DamageTypes.BLEED)
                .ability(Abilities.HYPNOSIS));
        removeAvailableAction(Action.TAKE);

    }

    @Override
    public void initializeBrain() {
        addTask(Tasks.TARGET_AND_ATTACK, 5);
    }

    @Override
    public void turn() {
        super.turn();
        if (this.hasTarget() && !this.getTarget().hasSpecifiedStatus(Statuses.CHARMED)) {
            addTask(Tasks.ABILITY_OR_TARGET, 2);
        } else {
            removeTask(Tasks.ABILITY_OR_TARGET);
        }
    }
}

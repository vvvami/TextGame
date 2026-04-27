package net.vami.game.interactables.entities;

import net.vami.game.interactables.ai.tasks.AbilityOrTargetTask;
import net.vami.game.interactables.ai.tasks.TargetAndAttackTask;
import net.vami.game.interactables.ai.tasks.Tasks;
import net.vami.game.interactables.interactions.abilities.Abilities;
import net.vami.game.interactables.interactions.action.Action;
import net.vami.game.interactables.interactions.abilities.HypnosisAbility;
import net.vami.game.interactables.interactions.damagetypes.BleedDamage;
import net.vami.game.interactables.interactions.damagetypes.DamageTypes;
import net.vami.game.interactables.interactions.statuses.CharmedStatus;
import net.vami.game.interactables.interactions.statuses.Statuses;

public class KalakuliEntity extends Entity {
    public KalakuliEntity(String name, Attributes attributes) {
        super(name, attributes
                .level(5)
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

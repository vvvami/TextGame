package net.vami.game.interactables.entities;

import net.vami.game.interactables.ai.Brain;
import net.vami.game.interactables.ai.tasks.AbilityOrTargetTask;
import net.vami.game.interactables.ai.tasks.ChaseTargetTask;
import net.vami.game.interactables.ai.tasks.MoveTask;
import net.vami.game.interactables.ai.tasks.TargetAndAttackTask;
import net.vami.game.interactables.interactions.action.Action;
import net.vami.game.interactables.interactions.abilities.HypnosisAbility;
import net.vami.game.interactables.interactions.damagetypes.BleedDamage;
import net.vami.game.interactables.interactions.statuses.CharmedStatus;

public class KalakuliEntity extends Entity {
    public KalakuliEntity(String name, Attributes attributes) {
        super(name, attributes
                .level(5)
                .damageType(BleedDamage.get)
                .ability(HypnosisAbility.get));
        removeAvailableAction(Action.TAKE);

    }

    @Override
    public void initializeBrain() {
        addTask(new MoveTask(), 1);
        addTask(new TargetAndAttackTask(), 5);
        addTask(new ChaseTargetTask(), 10);
    }

    @Override
    public void turn() {
        super.turn();
        if (this.hasTarget() && !this.getTarget().hasSpecifiedStatus(CharmedStatus.get)) {
            addTask(new AbilityOrTargetTask(), 2);
        } else {
            removeTask(new AbilityOrTargetTask());
        }
    }
}

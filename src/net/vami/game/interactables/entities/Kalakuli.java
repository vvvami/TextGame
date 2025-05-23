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

public class Kalakuli extends Entity {
    public Kalakuli(String name, Attributes attributes) {
        super(name, attributes
                .level(5)
                .damageType(new BleedDamage())
                .ability(new HypnosisAbility()));
        removeAvailableAction(Action.TAKE);
    }

    @Override
    public Brain getBrain() {
        Brain brain = new Brain();
        brain.addTask(new MoveTask(), 1);
        if (this.hasTarget() && !this.getTarget().hasSpecifiedStatus(new CharmedStatus())) {
            brain.addTask(new AbilityOrTargetTask(), 2);
        }
        brain.addTask(new TargetAndAttackTask(), 5);
        brain.addTask(new ChaseTargetTask(), 10);
        return brain;
    }
}

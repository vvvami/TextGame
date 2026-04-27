package net.vami.game.interactables.ai.tasks;

public final class Tasks {
    public static final AbilityOrTargetTask ABILITY_OR_TARGET = new AbilityOrTargetTask();
    public static final AbilityTask ABILITY = new AbilityTask();
    public static final AttackOrTargetTask ATTACK_OR_TARGET = new AttackOrTargetTask();
    public static final AttackTask ATTACK = new AttackTask();
    public static final SelfAbilityTask  SELF_ABILITY = new SelfAbilityTask();
    public static final SupportAbilityTask  SUPPORT_ABILITY = new SupportAbilityTask();
    public static final TakeTask TAKE = new TakeTask();
    public static final TargetAndAttackTask TARGET_AND_ATTACK = new TargetAndAttackTask();
    public static final TargetAnyAndAttackTask TARGET_ANY_AND_ATTACK = new TargetAnyAndAttackTask();
    public static final TargetOrAttackTask TARGET_OR_ATTACK = new TargetOrAttackTask();
    public static final TargetTask TARGET = new TargetTask();
}

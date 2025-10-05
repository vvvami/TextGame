package net.vami.util;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.statuses.PoisonedStatus;

public class CalcUtil {
    public static float damage(Interactable source, Interactable target, float amount, DamageType damageType) {
        float finalAmount = amount;

        // Denies damage if the target is already dead
        if (target.isEnded()) {
            return 0;
        }

        if (target instanceof Entity targetEntity) {
            // Increases damage if the damagetype is in the weaknesses arraylist
            for (DamageType weakness : targetEntity.getWeaknesses()) {
                if (weakness.is(damageType)) {
                    finalAmount = finalAmount * 2;
                    break;
                }
            }

            // Reduces damage if the damagetype is in the resistances arraylist
            for (DamageType resistance : targetEntity.getResistances()) {
                if (resistance.is(damageType)) {
                    finalAmount = finalAmount / 2;
                    break;
                }
            }

            if (source.hasSpecifiedStatus(PoisonedStatus.get)) {
                finalAmount *= 0.75f;
            }

            // Armor defense calculation
            if (targetEntity.getArmor() > 0) {
                finalAmount = Math.max(1, finalAmount - targetEntity.getArmor());
            }
        }

        return finalAmount;
    }
}

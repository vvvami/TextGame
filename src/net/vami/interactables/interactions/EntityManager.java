package net.vami.interactables.interactions;

import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.damagetypes.DamageType;
import net.vami.interactables.interactions.statuses.FrenziedStatus;

public class EntityManager {

    public static class Calc {
        public static float damage(Entity target, Entity source, float initialDamage, DamageType damageType) {
            float finalDamage = initialDamage;

            if (target.getWeaknesses().contains(damageType)) {
                finalDamage = finalDamage * 2;
            }

            else if (target.getResistances().contains(damageType)) {
                finalDamage = finalDamage / 2;
            }

            if (source.hasSpecifiedStatus(new FrenziedStatus())) {
                finalDamage = finalDamage +
                        (finalDamage * source.getStatusInstance(new FrenziedStatus()).getAmplifier() / 20);
            }

            // Armor defense is applied after the weakness/resistance calc
            if (target.getArmor() > 0) {
                finalDamage = (float) (finalDamage / Math.sqrt(target.getArmor()));
            }

            return finalDamage;
        }

        public static float heal(Entity target, Entity source, float initialHeal) {
            float finalHeal = initialHeal;

            if (source.hasSpecifiedStatus(new FrenziedStatus())) {
                finalHeal = finalHeal * 0.75f;
            }

            return finalHeal;
        }
    }

}

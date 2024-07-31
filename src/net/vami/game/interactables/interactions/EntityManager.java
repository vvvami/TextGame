package net.vami.game.interactables.interactions;

import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.statuses.FrenziedStatus;

public class EntityManager {

    public static class Stats {
        Entity entity;

        public Stats(Entity entity) {

            this.entity = entity;
        }

        public DamageType outgoingDamageType() {
            if (entity.getHeldItem().getDamageType() != null) {
                return entity.getHeldItem().getDamageType();
            }
            return entity.getDamageType();
        }

        public float incomingHealing() {
            return 0.0f;
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

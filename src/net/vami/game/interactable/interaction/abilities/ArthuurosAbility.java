package net.vami.game.interactable.interaction.abilities;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.Statuses;
import net.vami.game.interactable.item.ItemHoldable;
import net.vami.game.interactable.item.ItemInstance;

public class ArthuurosAbility extends Ability {

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        if (source instanceof Entity sourceEntity) {
            for (ItemInstance instance : sourceEntity.getItems()) {
                if (instance.get() instanceof ItemHoldable) {
                    sourceEntity.setHeldItem(instance);
                    target.hurt(sourceEntity, sourceEntity.getDamage(), sourceEntity.getDamageType());
                    instance.receiveDrop(sourceEntity);
                }
            }
        }

        return true;
    }

    @Override
    public String getName() {
        return "Arthuuros' Wrath";
    }

    @Override
    public boolean isSupport() {
        return false;
    }

}

package net.vami.game.interactable.loot;

import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.interactable.item.attunement.Attunement;

import java.util.*;

public class LootPool implements Rollable {
    private HashMap<Rollable, Integer> pool = new HashMap<>();


    public LootPool() {}

    public Rollable choose() {
        List<Integer> chanceList = pool.values().stream().toList();
        int totalChance = chanceList.stream().reduce(0, Integer::sum);

        int rnd = new Random().nextInt(totalChance);
        int acc = 0;

        for (int i = 0; i < chanceList.size(); i++) {
            acc += chanceList.get(i);
            if (rnd < acc) {
                List<Rollable> choices = pool.keySet().stream().toList();
                Rollable rollable = choices.get(i);
                Rollable chosen = null;

                if (rollable instanceof LootPool lootPool) {
                    chosen = lootPool.choose();
                } else if (rollable instanceof Item lootItem) {
                    chosen = lootItem;
                } else if (rollable instanceof Attunement attunement) {
                    chosen = attunement;
                }

                return chosen;
            }
        }
        // This should never reach here
        return null;
    }

    public LootPool add (Rollable rollable, int chance) {
        pool.put(rollable, chance);
        return this;
    }

    public LootPool remove (Rollable rollable) {
        pool.remove(rollable);
        return this;
    }

    public HashMap<Rollable, Integer> getPool() {
        return pool;
    }

    public ArrayList<Rollable> getContent() {
        return new ArrayList<>(pool.keySet());
    }
}

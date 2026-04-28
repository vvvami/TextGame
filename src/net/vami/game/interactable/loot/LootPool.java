package net.vami.game.interactable.loot;

import net.vami.game.interactable.ai.tasks.Task;
import net.vami.game.interactable.item.Item;

import java.util.*;

public class LootPool {
    private HashMap<Item, Integer> pool = new HashMap<>();


    public LootPool() {}

    public Item pick() {
        List<Integer> chanceList = pool.values().stream().toList();
        int totalChance = chanceList.stream().reduce(0, Integer::sum);

        int rnd = new Random().nextInt(totalChance);
        int acc = 0;

        for (int i = 0; i < chanceList.size(); i++) {
            acc += chanceList.get(i);
            if (rnd < acc) {
                List<Item> items = pool.keySet().stream().toList();
                return items.get(i);
            }
        }
        // This should never reach here
        return null;
    }

    public LootPool add (Item item, int chance) {
        pool.put(item, chance);
        return this;
    }

    public LootPool remove (Item item) {
        pool.remove(item);
        return this;
    }
}

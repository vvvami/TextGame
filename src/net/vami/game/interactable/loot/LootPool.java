package net.vami.game.interactable.loot;

import net.vami.game.interactable.item.Item;

import java.util.*;

public class LootPool implements Rollable {
    private HashMap<Rollable, Integer> pool = new HashMap<>();


    public LootPool() {}

    public Item choose() {
        List<Integer> chanceList = pool.values().stream().toList();
        int totalChance = chanceList.stream().reduce(0, Integer::sum);

        int rnd = new Random().nextInt(totalChance);
        int acc = 0;

        for (int i = 0; i < chanceList.size(); i++) {
            acc += chanceList.get(i);
            if (rnd < acc) {
                List<Rollable> items = pool.keySet().stream().toList();
                Rollable rollable = items.get(i);
                Item item = null;

                if (rollable instanceof LootPool lootPool) {
                    item = lootPool.choose();
                } else if (rollable instanceof Item lootItem) {
                    item = lootItem;
                }

                return item;
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

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        for (Rollable rollable : pool.keySet()) {
            if (rollable instanceof Item item) {
                items.add(item);
            }
        }
        return items;
    }
}

package net.vami.game.interactables.interactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Action {
    ATTACK(attackSynonyms()),
    MOVEMENT(moveSynonyms()),
    USE(useSynonyms()),
    TAKE(takeSynonyms()),
    EQUIP(equipSynonyms()),
    SAVE(saveSynonyms()),
    ABILITY(abilitySynonyms()),
    RESIST(resistSynonyms());
    public static Map<String, Action> synonymToAction = new HashMap<>();
    private List<String> synonyms;

    Action(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getSynonyms(){

        return synonyms;
    }

    private static List<String> attackSynonyms() {
        List<String> synonymList = new ArrayList<>();
        synonymList.add("attack");
        synonymList.add("strike");
        synonymList.add("kick");
        synonymList.add("punch");
        synonymList.add("hurt");
        synonymList.add("hit");
        synonymList.add("kill");
        return synonymList;
    }

    private static List<String> moveSynonyms() {
        List<String> synonymList = new ArrayList<>();
        synonymList.add("walk");
        synonymList.add("run");
        synonymList.add("escape");
        synonymList.add("flee");
        synonymList.add("go");
        synonymList.add("move");
        return synonymList;
    }

    private static List<String> resistSynonyms() {
        List<String> synonymList = new ArrayList<>();
        synonymList.add("resist");
        synonymList.add("endure");
        synonymList.add("suffer");
        synonymList.add("withstand");
        synonymList.add("outlast");
        synonymList.add("repel");
        return synonymList;
    }

    private static List<String> useSynonyms() {
        List<String> synonymList = new ArrayList<>();
        synonymList.add("use");
        synonymList.add("utilize");
        synonymList.add("activate");
        synonymList.add("drink");
        synonymList.add("eat");
        return synonymList;
    }

    private static List<String> saveSynonyms() {
        List<String> synonymList = new ArrayList<>();
        synonymList.add("save");
        synonymList.add("pray");
        synonymList.add("meditate");
        synonymList.add("determination");
        synonymList.add("cry");
        return synonymList;
    }

    private static List<String> abilitySynonyms() {
        List<String> synonymList = new ArrayList<>();
        synonymList.add("cast");
        synonymList.add("draw");
        synonymList.add("conjure");
        return synonymList;
    }

    private static List<String> takeSynonyms() {
        List<String> synonymList = new ArrayList<>();
        synonymList.add("take");
        synonymList.add("steal");
        synonymList.add("grab");
        synonymList.add("pocket");
        return synonymList;
    }

    private static List<String> equipSynonyms() {
        List<String> synonymList = new ArrayList<>();
        synonymList.add("equip");
        synonymList.add("wear");
        synonymList.add("adorn");
        synonymList.add("hold");
        return synonymList;
    }

    public static void registerActionSynonyms() {
        for (Action action : Action.values()) {
            for (String synonym : action.synonyms) {
                synonymToAction.put(synonym, action);
            }
        }
    }


}

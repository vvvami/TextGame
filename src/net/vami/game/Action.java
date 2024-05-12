package net.vami.game;
import net.vami.interactables.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum Action {
    ATTACK(attackSynonyms()),
    MOVEMENT(moveSynonyms()),
    USE(useSynonyms()),
    TAKE(takeSynonyms()),
    EQUIP(equipSynonyms()),
    ABILITY(abilitySynonyms());
    private List<String> synonyms;
    public static HashMap<String, Action> synonymToAction = new HashMap<>();

    Action(List<String> synonyms) {
        this.synonyms = new ArrayList<>();
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


    public static void attackAction(Entity target, Entity source) {
    }

    public static void abilityAction(Entity target, Entity source) {
        System.out.println(source.getDisplayName() + " casts " + source.getAbility().getName()
                + " on " + target.getDisplayName() + "!");
        new AbilityInstance(source.getAbility(), target, source);
    }

}

import java.util.ArrayList;
import java.util.List;

public class Action {
    private List<String> synonyms;
    private static List<Action> actions = new ArrayList();

    public Action() {
        this.synonyms = new ArrayList<>();
        actions.add(this);
    }

    public List<String> getSynonyms(){
        return synonyms;
    }

    public void addSynonym(String synonym) {
        synonyms.add(synonym);
    }

    public static List<Action> getActions() {
        return actions;
    }

    public static final Action attack = new Action();
    public static final Action move = new Action();
    public static final Action use = new Action();
    public static final Action equip = new Action();

    public static void actionSynonymInitializer() {
        Action.attackActionInitiliazer();
        Action.moveActionInitiliazer();
        Action.useActionInitiliazer();
    }

    private static void attackActionInitiliazer() {
        attack.addSynonym("attack");
        attack.addSynonym("strike");
        attack.addSynonym("kick");
        attack.addSynonym("punch");
        attack.addSynonym("hurt");
        attack.addSynonym("hit");

    }

    private static void moveActionInitiliazer() {
        attack.addSynonym("move");
        attack.addSynonym("walk");
        attack.addSynonym("run");
        attack.addSynonym("escape");
        attack.addSynonym("flee");
        attack.addSynonym("go");

    }

    private static void useActionInitiliazer() {
        attack.addSynonym("use");
        attack.addSynonym("utilize");
        attack.addSynonym("activate");
        attack.addSynonym("drink");

    }

}

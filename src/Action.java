import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Action {
    private List<String> synonyms;
    public static List<Action> actions = new ArrayList<>();
    public static HashMap<String, Action> synonymToAction = new HashMap<>();

    public Action() {
        this.synonyms = new ArrayList<>();
        actions.add(this);
    }

    public List<String> getSynonyms(){
        return synonyms;
    }

    public void addSynonym(String synonym) throws Exception {

        if (synonymToAction.put(synonym, this) != null) {
            throw new Exception("Duplicate synonym: " + synonym);
        }

        synonyms.add(synonym);
    }

    public static final Action attack = new Action();
    public static final Action movement = new Action();
    public static final Action use = new Action();
    public static final Action take = new Action();
    public static final Action equip = new Action();
    public static final Action ability = new Action();

    public static void actionSynonymInitializer() throws Exception {

        Action.attackSynonymInitializer();
        Action.moveSynonymInitializer();
        Action.useSynonymInitializer();
        Action.abilitySynonymInitializer();
        Action.takeSynonymInitializer();

    }

    private static void attackSynonymInitializer() throws Exception {
        attack.addSynonym("attack");
        attack.addSynonym("strike");
        attack.addSynonym("kick");
        attack.addSynonym("punch");
        attack.addSynonym("hurt");
        attack.addSynonym("hit");
        attack.addSynonym("kill");
    }

    private static void moveSynonymInitializer() throws Exception {
        movement.addSynonym("walk");
        movement.addSynonym("run");
        movement.addSynonym("escape");
        movement.addSynonym("flee");
        movement.addSynonym("go");
    }

    private static void useSynonymInitializer() throws Exception {
        use.addSynonym("use");
        use.addSynonym("utilize");
        use.addSynonym("activate");
        use.addSynonym("drink");
        use.addSynonym("eat");
    }

    private static void abilitySynonymInitializer() throws Exception {
        ability.addSynonym("ability");
        ability.addSynonym("cast");
        ability.addSynonym("draw");
        ability.addSynonym("conjure");
    }

    private static void takeSynonymInitializer() throws Exception {
        take.addSynonym("take");
        take.addSynonym("steal");
        take.addSynonym("grab");
        take.addSynonym("pocket");
    }



    public static void entityAction(Entity target, Entity source) {
        if (source.getAction() == Action.attack) {
            attackAction(target, source);
        }
        else if (source.getAction() == Action.ability) {
            abilityAction(target, source);
        }
    }

    public static void entityAction(Object objectTarget, Entity source) {
        if (source.getAction() == Action.use) {

        }
        else if (source.getAction() == Action.equip) {

        }
    }

    public static void entityAction(Entity source) {
        if (source.getAction() == Action.movement) {

        }
    }

    public static void attackAction(Entity target, Entity source) {
        float damage = source.getBaseDamage();
        DamageType type = source.getDefaultDamageType();

        if (source.hasEquippedItem()) {
            damage += source.getEquippedItem().getDamageAmount();
            type = source.getEquippedItem().getDamageType();
        }

        System.out.printf("%s was hit by %s! %n", target.getDisplayName(), source.getDisplayName());
        target.hurt(source, damage, type);
    }

    public static void abilityAction(Entity target, Entity source) {
        System.out.println(source.getDisplayName() + " casts " + source.getAbility().getName()
                + " on " + target.getDisplayName() + "!");
        new AbilityInstance(source.getAbility(), target, source);
    }

}

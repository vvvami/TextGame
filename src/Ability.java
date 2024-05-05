public enum Ability {
    HEAL("Heal"),
    BURN("Burn"),
    FREEZE("Freeze"),
    WOUND("Wound");

    private String name;
    private String manaCost;

    Ability(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}

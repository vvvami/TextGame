public enum Status {

    CRIPPLED("Crippled",false, true,true),
    BURNING("Burning",true, true,true),
    FROZEN("Frozen",false,true,true, Status.BURNING),
    PARALYZED("Paralyzed",false, true, true),
    BLESSED("Blessed",true, true, false),
    IMMUNE("Immune",false, true, false),
    INSPIRED("Inspired",true, false, false, Status.CRIPPLED),
    BLEEDING("Bleeding",true,true,true, Status.BLESSED);

        private String name;
        private boolean harmful;
        private boolean stacksDuration;
        private boolean stacksAmplifier;
        private Status counterStatus;

        Status(String name, boolean stacksAmplifier, boolean stacksDuration, boolean harmful) {
            this.name = name;
            this.stacksAmplifier = stacksAmplifier;
            this.stacksDuration = stacksDuration;
            this.harmful = harmful;

        }

        Status(String name, boolean stacksAmplifier, boolean stacksDuration, boolean harmful, Status counterStatus) {
        this.name = name;
        this.stacksAmplifier = stacksAmplifier;
        this.stacksDuration = stacksDuration;
        this.harmful = harmful;
        this.counterStatus = counterStatus;
    }

        public String getName() {
            return name;
        }

        public boolean stacksAmplifier() {
            return stacksAmplifier;
        }

        public boolean stacksDuration() {
            return stacksDuration;
        }

        public boolean isHarmful() {
            return harmful;
        }

    public Status getCounterStatus() {
        return counterStatus;
    }
}

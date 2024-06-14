package net.vami.game.interactions;

import net.vami.game.Main;

public enum Status {

    CRIPPLED("Crippled",false, true,true),
    BURNING("Burning",true, true,true),
    FROZEN("Frozen",false,true,true),
    PARALYZED("Paralyzed",false, true, true),
    BLESSED("Blessed",true, true, false),
    IMMUNE("Immune",false, true, false),
    INSPIRED("Inspired",true, false, false),
    BLEEDING("Bleeding",true,true,true);

        private String name;
        private boolean harmful;
        private boolean stacksDuration;
        private boolean stacksAmplifier;
        private Status counterStatus;

        Status(String name, boolean stacksAmplifier, boolean stacksDuration, boolean harmful) {
            this.stacksAmplifier = stacksAmplifier;
            this.stacksDuration = stacksDuration;
            this.harmful = harmful;
            if (harmful) {
                this.name = Main.ANSI_RED + name + Main.ANSI_RESET;
            }
            else {
                this.name = Main.ANSI_GREEN + name + Main.ANSI_RESET;
            }

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

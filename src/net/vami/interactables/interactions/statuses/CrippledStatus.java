package net.vami.interactables.interactions.statuses;

import net.vami.interactables.interactions.Status;

public class CrippledStatus extends Status {
    public static final CrippledStatus STATUS = new CrippledStatus("Crippled", false, true, true);

    protected CrippledStatus(String name, boolean stacksAmplifier, boolean stacksDuration, boolean harmful) {
        super(name, stacksAmplifier, stacksDuration, harmful);
    }
}

public class Status {

    private boolean harmful;
    private boolean stackDuration;
    private boolean stackAmplifier;
    public static final Status CRIPPLED = new Status(false, true,true);
    public static final Status BURNING = new Status(true, true,true);
    public static final Status FROZEN = new Status(false,true,true);
    public static final Status PARALYZED = new Status(false, true, true);
    public static final Status BLESSED = new Status(true, true, false);
    public static final Status IMMUNE = new Status(false, true, false);
    public static final Status INSPIRED = new Status(true, false, false);

    public Status() {
    }

    public Status(boolean stackAmplifier, boolean stackDuration, boolean harmful) {
        this.stackAmplifier = stackAmplifier;
        this.stackDuration = stackDuration;
        this.harmful = harmful;
    }

    public boolean stacksAmplifier() {
        return stackAmplifier;
    }

    public boolean stacksDuration() {
        return stackDuration;
    }

    public boolean isHarmful() {
        return harmful;
    }



}

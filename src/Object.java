import java.text.DecimalFormat;

public class Object {
    private static int identifier;
    private final String ID = new DecimalFormat("##").format(identifier);
    private String name;
    private String description;

    public Object(String name, String description) {
        this.name = name;
        this.description = description;
        identifier++;
    }

    Object test = new Object("Book","an old and dusty book");

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }
}

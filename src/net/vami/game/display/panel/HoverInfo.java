package net.vami.game.display.panel;

public class HoverInfo {
    private String title;
    private String description;

    public HoverInfo(String description) {
        this.description = description;
    }

    public HoverInfo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

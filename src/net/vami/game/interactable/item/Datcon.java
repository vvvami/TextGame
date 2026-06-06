package net.vami.game.interactable.item;

public class Datcon<T> {
    private T data;

    public Datcon(T data) {
        this.data = data;
    }

    public T get() {
        return data;
    }

    public void set(T data) {
        this.data = data;
    }

    public boolean asBool() {
        return data instanceof Boolean;
    }

    public int asInt() {
        return data instanceof Integer ? (int) data : 0;
    }

    public float asFloat() {
        return data instanceof Float ? (float) data : 0;
    }

    public double asDouble() {
        return data instanceof Double ? (double) data : 0;
    }

    public String asString() {
        return data instanceof String ? (String) data : "";
    }

    public Item asItem() {
        return data instanceof Item ? (Item) data : null;
    }

    public ItemInstance asItemInstance() {
        return data instanceof ItemInstance ? (ItemInstance) data : null;
    }
}

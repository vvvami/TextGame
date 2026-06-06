package net.vami.game.interactable.item;

public class Datcon<T> {
    T data;

    public Datcon(T data) {
        this.data = data;
    }

    public T get() {
        return data;
    }

    public void set(T data) {
        this.data = data;
    }
}

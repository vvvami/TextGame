package net.vami.game.world;

import java.util.Objects;

public final class Position {
    private int x;
    private int y;
    private int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y && z == position.z;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y, z);
    }

    public int x() {
        return this.x;
    }

    public int y() {

        return this.y;
    }

    public int z() {

        return this.z;
    }

    public Position setX(int x) {

        return new Position(x, y, z);
    }

    public Position setY(int y) {

        return new Position(x, y, z);
    }

    public Position setZ(int z) {

        return new Position(x, y, z);
    }

    @Override
    public String toString() {return "(" + x + ", " + y + ", " + z + ")";}
}

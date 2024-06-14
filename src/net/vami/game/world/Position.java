package net.vami.game.world;

import java.util.Objects;

public final class Position {
    private int xPos;
    private int yPos;
    private int zPos;

    public Position(int xPos, int yPos, int zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return xPos == position.xPos && yPos == position.yPos && zPos == position.zPos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xPos, yPos, zPos);
    }

    public int getPosX() {
        return this.xPos;
    }

    public int getPosY() {
        return this.yPos;
    }

    public int getPosZ() {
        return this.zPos;
    }

    public Position setX(int x) {
        return new Position(x, yPos, zPos);
    }

    public Position setY(int y) {
        return new Position(xPos, y, zPos);
    }

    public Position setZ(int z) {
        return new Position(xPos, yPos, z);
    }


}

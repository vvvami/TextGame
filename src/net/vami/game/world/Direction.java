package net.vami.game.world;

public enum Direction {
    UP(new Position(0,1,0)),
    DOWN(new Position(0,-1,0)),
    NORTH(new Position(1,0,0)),
    SOUTH(new Position(-1,0,0)),
    EAST(new Position(0,0,1)),
    WEST(new Position(0,0,-1));

    Position pos;

    Direction(Position pos) {
        this.pos = pos;
    }

    public Position getPos() {
        return this.pos;
    }

    public static Direction getDirectionFromString(String input) {
        input = input.toLowerCase();
        return switch (input) {
            case "up", "above" -> Direction.UP;
            case "down", "below" -> Direction.DOWN;
            case "north" -> Direction.NORTH;
            case "south" -> Direction.SOUTH;
            case "east" -> Direction.EAST;
            case "west" -> Direction.WEST;
            default -> null;
        };
    }
}

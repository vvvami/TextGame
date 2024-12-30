package net.vami.game.world;

public enum Direction {
    UP(new Position(0,1,0)),
    DOWN(new Position(0,-1,0)),
    NORTH(new Position(1,0,0)),
    SOUTH(new Position(-1,0,0)),
    EAST(new Position(0,0,1)),
    WEST(new Position(0,0,-1));

    public final Position pos;

    Direction(Position pos) {
        this.pos = pos;
    }


    public Direction getOpposite() {
        return switch (this) {
            case Direction.UP -> Direction.DOWN;
            case Direction.DOWN -> Direction.UP;
            case Direction.NORTH -> Direction.SOUTH;
            case Direction.SOUTH -> Direction.NORTH;
            case Direction.EAST -> Direction.WEST;
            case Direction.WEST -> Direction.EAST;
        };
    }

    public static Direction getDirectionFromString(String input) {
        for (Direction direction : Direction.values()) {
            String directionString = direction.name();
            if (input.equalsIgnoreCase(directionString)
                    || input.equalsIgnoreCase(directionString + "ward")) {
                return direction;
            }
        }
        return null;
    }
}

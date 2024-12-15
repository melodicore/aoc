package me.datafox.aoc;

/**
 * @author datafox
 */
public enum Direction {
    RIGHT,
    DOWN,
    LEFT,
    UP;

    public Direction rotateRight() {
        return switch(this) {
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            case LEFT -> UP;
            case UP -> RIGHT;
        };
    }

    public Direction rotateLeft() {
        return switch(this) {
            case RIGHT -> UP;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
            case UP -> LEFT;
        };
    }

    public Direction opposite() {
        return switch(this) {
            case RIGHT -> LEFT;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case UP -> DOWN;
        };
    }
}

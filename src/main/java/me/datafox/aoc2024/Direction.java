package me.datafox.aoc2024;

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
}

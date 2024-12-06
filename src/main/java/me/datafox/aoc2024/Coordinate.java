package me.datafox.aoc2024;

/**
 * @author datafox
 */
public record Coordinate(int x, int y) {
    public Coordinate move(int x, int y) {
        return new Coordinate(this.x + x, this.y + y);
    }
    public Coordinate move(Direction dir, int steps) {
        if(dir == null) {
            return this;
        }
        return switch(dir) {
            case RIGHT -> move(steps, 0);
            case DOWN -> move(0, steps);
            case LEFT -> move(-steps, 0);
            case UP -> move(0, -steps);
        };
    }

    public boolean isWithinBounds(int x1, int y1, int x2, int y2) {
        return x >= x1 && y >= y1 && x <= x2 && y <= y2;
    }
}

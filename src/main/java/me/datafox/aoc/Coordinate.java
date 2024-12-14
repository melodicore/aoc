package me.datafox.aoc;

/**
 * @author datafox
 */
public record Coordinate(int x, int y) {
    public Coordinate move(Coordinate coord) {
        return new Coordinate(x + coord.x, y + coord.y);
    }

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

    public Coordinate diff(Coordinate coord) {
        return move(-coord.x, -coord.y);
    }

    public boolean isWithinBounds(int x1, int y1, int x2, int y2) {
        return x >= x1 && y >= y1 && x <= x2 && y <= y2;
    }

    public int distance(Coordinate coord) {
        return Math.abs(x - coord.x) + Math.abs(y - coord.y);
    }

    public Coordinate multiply(int n) {
        return new Coordinate(x * n, y * n);
    }

    public Coordinate modulo(int x, int y) {
        int tx = this.x;
        int ty = this.y;
        if(tx < 0) {
            tx += (Math.abs(tx) / x + 1) * x;
        }
        if(ty < 0) {
            ty += (Math.abs(ty) / y + 1) * y;
        }
        return new Coordinate(tx % x, ty % y);
    }
}

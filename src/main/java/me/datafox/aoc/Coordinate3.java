package me.datafox.aoc;

/**
 * @author datafox
 */
public record Coordinate3(int x, int y, int z) {
    public Coordinate3 move(Coordinate3 coord) {
        return new Coordinate3(x + coord.x, y + coord.y, z + coord.z);
    }

    public Coordinate3 move(int x, int y, int z) {
        return new Coordinate3(this.x + x, this.y + y, this.z + z);
    }

    public Coordinate3 diff(Coordinate3 coord) {
        return move(-coord.x, -coord.y, -coord.z);
    }

    public boolean isWithinBounds(int x1, int y1, int z1, int x2, int y2, int z2) {
        return x >= x1 && y >= y1 && x <= x2 && y <= y2 && z >= z1 && z <= z2;
    }

    public int distance(Coordinate3 coord) {
        return Math.abs(x - coord.x) + Math.abs(y - coord.y) + Math.abs(z - coord.z);
    }

    public Coordinate3 multiply(int n) {
        return new Coordinate3(x * n, y * n, z * n);
    }

    public Coordinate3 modulo(int x, int y, int z) {
        int tx = this.x;
        int ty = this.y;
        int tz = this.z;
        if(tx < 0) {
            tx += (Math.abs(tx) / x + 1) * x;
        }
        if(ty < 0) {
            ty += (Math.abs(ty) / y + 1) * y;
        }
        if(tz < 0) {
            tz += (Math.abs(tz) / z + 1) * z;
        }
        return new Coordinate3(tx % x, ty % y, tz % z);
    }
}

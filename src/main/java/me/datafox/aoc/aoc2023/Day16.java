package me.datafox.aoc.aoc2023;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Advent of Code 2023 day 16 solutions.
 *
 * @author datafox
 */
public class Day16 {
    private static int width, height;

    public static long solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        height = map.length;
        width = map[0].length;
        Map<Coordinate,Mirror> mirrors = parseMirrors(map);
        Map<Direction,Set<Coordinate>> visited = Arrays.stream(Direction.values())
                .collect(Collectors.toMap(
                        Function.identity(),
                        d -> new HashSet<>()));
        Coordinate coord = new Coordinate(-1, 0);
        Direction dir = Direction.RIGHT;
        moveLight(coord, dir, mirrors, visited);
        return visited.values().stream().flatMap(Set::stream).distinct().count();
    }

    public static long solve2(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        height = map.length;
        width = map[0].length;
        Map<Coordinate,Mirror> mirrors = parseMirrors(map);
        Map<Direction,Set<Coordinate>> visited = Arrays.stream(Direction.values())
                .collect(Collectors.toMap(
                        Function.identity(),
                        d -> new HashSet<>()));
        long max = 0;
        for(int i = 0; i < width; i++) {
            moveLight(new Coordinate(i, -1), Direction.DOWN, mirrors, visited);
            max = Math.max(max, visited.values().stream().flatMap(Set::stream).distinct().count());
            visited.values().forEach(Set::clear);
            moveLight(new Coordinate(i, height), Direction.UP, mirrors, visited);
            max = Math.max(max, visited.values().stream().flatMap(Set::stream).distinct().count());
            visited.values().forEach(Set::clear);
        }
        for(int i = 0; i < height; i++) {
            moveLight(new Coordinate(-1, i), Direction.RIGHT, mirrors, visited);
            max = Math.max(max, visited.values().stream().flatMap(Set::stream).distinct().count());
            visited.values().forEach(Set::clear);
            moveLight(new Coordinate(width, i), Direction.LEFT, mirrors, visited);
            max = Math.max(max, visited.values().stream().flatMap(Set::stream).distinct().count());
            visited.values().forEach(Set::clear);
        }
        Coordinate coord = new Coordinate(-1, 0);
        Direction dir = Direction.RIGHT;
        moveLight(coord, dir, mirrors, visited);
        return max;
    }

    private static void moveLight(Coordinate coord, Direction dir, Map<Coordinate,Mirror> mirrors, Map<Direction,Set<Coordinate>> visited) {
        coord = coord.move(dir, 1);
        if(visited.get(dir).contains(coord)) {
            return;
        }
        if(!coord.isWithinBounds(0, 0, width - 1, height - 1)) {
            return;
        }
        visited.get(dir).add(coord);
        if(mirrors.containsKey(coord)) {
            final boolean vertical = dir.equals(Direction.UP) || dir.equals(Direction.DOWN);
            switch(mirrors.get(coord)) {
                case HOR -> {
                    if(vertical) {
                        moveLight(coord, Direction.LEFT, mirrors, visited);
                        moveLight(coord, Direction.RIGHT, mirrors, visited);
                        return;
                    }
                }
                case VER -> {
                    if(!vertical) {
                        moveLight(coord, Direction.UP, mirrors, visited);
                        moveLight(coord, Direction.DOWN, mirrors, visited);
                        return;
                    }
                }
                case UL_DR -> {
                    if(vertical) {
                        moveLight(coord, dir.rotateLeft(), mirrors, visited);
                    } else {
                        moveLight(coord, dir.rotateRight(), mirrors, visited);
                    }
                    return;
                }
                case DL_UR -> {
                    if(vertical) {
                        moveLight(coord, dir.rotateRight(), mirrors, visited);
                    } else {
                        moveLight(coord, dir.rotateLeft(), mirrors, visited);
                    }
                    return;
                }
            }
        }
        moveLight(coord, dir, mirrors, visited);
    }

    private static Map<Coordinate,Mirror> parseMirrors(char[][] map) {
        Map<Coordinate,Mirror> mirrors = new HashMap<>();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                char c = map[i][j];
                if(c == '.') {
                    continue;
                }
                Coordinate coord = new Coordinate(j, i);
                Mirror mirror = Mirror.forChar(c);
                if(mirror == null) {
                    continue;
                }
                mirrors.put(coord, mirror);
            }
        }
        return mirrors;
    }

    private enum Mirror {
        HOR,
        VER,
        UL_DR,
        DL_UR;

        public static Mirror forChar(char c) {
            return switch(c) {
                case '-' -> HOR;
                case '|' -> VER;
                case '\\' -> UL_DR;
                case '/' -> DL_UR;
                default -> null;
            };
        }
    }
}

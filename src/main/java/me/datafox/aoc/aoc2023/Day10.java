package me.datafox.aoc.aoc2023;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;

/**
 * Advent of Code 2023 day 10 solutions.
 *
 * @author datafox
 */
public class Day10 {
    public static int solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        LinkedHashMap<Coordinate,Set<Direction>> pipes = parsePipes(map);
        Coordinate start = pipes.lastEntry().getKey();
        removeDisconnected(pipes, start);
        return pipes.size() / 2;
    }

    public static int solve2(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        LinkedHashMap<Coordinate,Set<Direction>> pipes = parsePipes(map);
        Coordinate start = pipes.lastEntry().getKey();
        removeDisconnected(pipes, start);
        return calculateInside(pipes, map[0].length, map.length);
    }

    private static int calculateInside(LinkedHashMap<Coordinate,Set<Direction>> pipes, int width, int height) {
        Set<Direction> horizontal = EnumSet.of(Direction.LEFT, Direction.RIGHT);
        Direction lastCornerHorizontal = null;
        int count = 0;
        for(int x = 0; x < width; x++) {
            boolean inside = false;
            for(int y = 0; y < height; y++) {
                Coordinate coord = new Coordinate(x, y);
                if(!pipes.containsKey(coord)) {
                    if(inside) {
                        count++;
                    }
                } else if(pipes.get(coord).equals(horizontal)) {
                    inside = !inside;
                } else {
                    Set<Direction> pipe = pipes.get(coord);
                    if(pipe.contains(Direction.LEFT)) {
                        if(lastCornerHorizontal == null) {
                            lastCornerHorizontal = Direction.LEFT;
                        } else if(lastCornerHorizontal == Direction.RIGHT) {
                            inside = !inside;
                            lastCornerHorizontal = null;
                        } else {
                            lastCornerHorizontal = null;
                        }
                    } else if(pipe.contains(Direction.RIGHT)) {
                        if(lastCornerHorizontal == null) {
                            lastCornerHorizontal = Direction.RIGHT;
                        } else if(lastCornerHorizontal == Direction.LEFT) {
                            inside = !inside;
                            lastCornerHorizontal = null;
                        } else {
                            lastCornerHorizontal = null;
                        }
                    }
                }
            }
        }
        return count;
    }

    private static LinkedHashMap<Coordinate,Set<Direction>> parsePipes(char[][] map) {
        LinkedHashMap<Coordinate,Set<Direction>> pipes = new LinkedHashMap<>();
        Coordinate start = null;
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                if(map[i][j] == '.') {
                    continue;
                }
                Coordinate coord = new Coordinate(j, i);
                if(map[i][j] == 'S') {
                    start = coord;
                } else {
                    pipes.put(coord, getPipe(map[i][j]));
                }
            }
        }
        assert start != null;
        Set<Direction> set = EnumSet.noneOf(Direction.class);
        for(Direction dir : Direction.values()) {
            Coordinate coord = start.move(dir, 1);
            if(pipes.containsKey(coord) && pipes.get(coord).contains(dir.opposite())) {
                set.add(dir);
            }
        }
        assert set.size() == 2;
        pipes.put(start, set);
        return pipes;
    }

    private static Set<Direction> getPipe(char c) {
        return switch(c) {
            case '|' -> EnumSet.of(Direction.DOWN, Direction.UP);
            case '-' -> EnumSet.of(Direction.RIGHT, Direction.LEFT);
            case 'L' -> EnumSet.of(Direction.RIGHT, Direction.UP);
            case 'J' -> EnumSet.of(Direction.LEFT, Direction.UP);
            case '7' -> EnumSet.of(Direction.DOWN, Direction.LEFT);
            case 'F' -> EnumSet.of(Direction.RIGHT, Direction.DOWN);
            default -> null;
        };
    }

    private static void removeDisconnected(LinkedHashMap<Coordinate,Set<Direction>> pipes, Coordinate start) {
        Set<Coordinate> visited = new HashSet<>();
        Coordinate coord = start;
        Set<Direction> pipe = pipes.get(start);
        visited.add(coord);
        loop: while(true) {
            for(Direction dir : pipe) {
                Coordinate next = coord.move(dir, 1);
                if(!visited.contains(next)) {
                    coord = next;
                    pipe = pipes.get(next);
                    visited.add(next);
                    continue loop;
                }
            }
            break;
        }
        Set<Coordinate> coords = new HashSet<>(pipes.keySet());
        coords.removeAll(visited);
        coords.forEach(pipes::remove);
    }
}

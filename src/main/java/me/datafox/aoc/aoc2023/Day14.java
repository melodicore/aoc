package me.datafox.aoc.aoc2023;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Advent of Code 2023 day 14 solutions.
 *
 * @author datafox
 */
public class Day14 {
    private static int WIDTH, HEIGHT;

    public static int solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        WIDTH = map[0].length;
        HEIGHT = map.length;
        Map<Coordinate,Rock> rocks = parseRocks(map);
        Map<Coordinate,Rock> next;
        do {
            next = rocks;
            rocks = moveRocks(next);
        } while(!rocks.equals(next));
        return rocks.values().stream().mapToInt(Rock::calculateLoad).sum();
    }

    public static int solve2(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        WIDTH = map[0].length;
        HEIGHT = map.length;
        Map<Coordinate,Rock> rocks = parseRocks(map);
        Map<Map<Coordinate,Rock>,Integer> history = new HashMap<>();
        for(int i = 0; i < 1000000000; i++) {
            Map<Coordinate,Rock> next;
            for(Direction dir : List.of(Direction.UP, Direction.LEFT, Direction.DOWN, Direction.RIGHT)) {
                do {
                    next = rocks;
                    rocks = moveRocks(next, dir);
                } while(!rocks.equals(next));
            }
            if(history.containsKey(rocks)) {
                int left = 1000000000 - i;
                int last = history.get(rocks);
                int diff = i - last;
                int target = last + left % diff - 1;
                rocks = history.entrySet().stream()
                        .filter(e -> target == e.getValue())
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElseThrow();
                break;
            }
            history.put(rocks, i);
        }
        return rocks.values().stream().mapToInt(Rock::calculateLoad).sum();
    }

    private static Map<Coordinate,Rock> parseRocks(char[][] map) {
        Map<Coordinate,Rock> rocks = new HashMap<>();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map.length; j++) {
                char c = map[i][j];
                if(c == '.') {
                    continue;
                }
                Coordinate coord = new Coordinate(j, i);
                rocks.put(coord, new Rock(coord, c == 'O'));
            }
        }
        return rocks;
    }
    private static Map<Coordinate,Rock> moveRocks(Map<Coordinate,Rock> rocks) {
        return moveRocks(rocks, Direction.UP);
    }

    private static Map<Coordinate,Rock> moveRocks(Map<Coordinate,Rock> rocks, Direction dir) {
        Map<Coordinate,Rock> next = new HashMap<>(rocks.values().stream()
                .filter(Predicate.not(Rock::round))
                .collect(Collectors.toMap(
                        Rock::coord,
                        Function.identity())));
        rocks.values().stream()
                .filter(Rock::round)
                .sorted(dirComparator(dir))
                .map(r -> r.move(dir, next.keySet()))
                .forEach(r -> next.put(r.coord(), r));
        return next;
    }

    private static Comparator<Rock> dirComparator(Direction dir) {
        return switch(dir) {
            case RIGHT -> Comparator.comparingInt(r -> -r.coord().x());
            case DOWN -> Comparator.comparingInt(r -> -r.coord().y());
            case LEFT -> Comparator.comparingInt(r -> r.coord().x());
            case UP -> Comparator.comparingInt(r -> r.coord().y());
        };
    }

    private record Rock(Coordinate coord, boolean round) {
        public Rock move(Direction dir, Set<Coordinate> obstacles) {
            if(!round) {
                return this;
            }
            if(switch(dir) {
                case RIGHT -> coord.x() == WIDTH - 1;
                case DOWN -> coord.y() == HEIGHT - 1;
                case LEFT -> coord.x() == 0;
                case UP -> coord.y() == 0; }) {
                return this;
            }
            Coordinate next = coord.move(dir, 1);
            if(obstacles.contains(next)) {
                return this;
            }
            return new Rock(next, true);
        }

        public int calculateLoad() {
            if(!round) {
                return 0;
            }
            return HEIGHT - coord.y();
        }
    }
}

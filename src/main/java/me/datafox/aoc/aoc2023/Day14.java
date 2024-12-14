package me.datafox.aoc.aoc2023;

import me.datafox.aoc.Coordinate;
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
    public static int solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        Map<Coordinate,Rock> rocks = parseRocks(map);
        Map<Coordinate,Rock> next = rocks;
        do {
            rocks = next;
            next = moveRocks(rocks);
        } while(!rocks.equals(next));
        return rocks.values().stream().mapToInt(r -> r.calculateLoad(map.length)).sum();
    }

    public static int solve2(URL url) {
        return 0;
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
        Map<Coordinate,Rock> next = new HashMap<>(rocks.values().stream()
                .filter(Predicate.not(Rock::round))
                .collect(Collectors.toMap(
                        Rock::coord,
                        Function.identity())));
        rocks.values().stream()
                .filter(Rock::round)
                .sorted(Comparator.comparingInt(r -> r.coord().y()))
                .map(r -> r.moveUp(next.keySet()))
                .forEach(r -> next.put(r.coord(), r));
        return next;
    }

    private record Rock(Coordinate coord, boolean round) {
        public Rock moveUp(Set<Coordinate> obstacles) {
            if(!round || coord.y() == 0) {
                return this;
            }
            Coordinate next = coord.move(0, -1);
            if(obstacles.contains(next)) {
                return this;
            }
            return new Rock(next, true);
        }

        public int calculateLoad(int max) {
            if(!round) {
                return 0;
            }
            return max - coord.y();
        }
    }
}

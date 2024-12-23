package me.datafox.aoc.aoc2023;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;

/**
 * Advent of Code 2023 day 21 solutions.
 *
 * @author datafox
 */
public class Day21 {
    public static long solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        Set<Coordinate> plots = parsePlots(map);
        Coordinate start = plots.stream().filter(c -> map[c.y()][c.x()] == 'S').findAny().orElseThrow();
        Map<Coordinate,Integer> reachable = getReachable(plots, start, 64);
        return reachable.values().stream().filter(i -> i % 2 == 0).count();
    }

    public static long solve2(URL url) {
        return 0;
    }

    private static Set<Coordinate> parsePlots(char[][] map) {
        Set<Coordinate> plots = new HashSet<>();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                if(map[i][j] != '#') {
                    plots.add(new Coordinate(j, i));
                }
            }
        }
        return plots;
    }

    private static Map<Coordinate,Integer> getReachable(Set<Coordinate> plots, Coordinate start, int steps) {
        Map<Coordinate,Integer> reachable = new HashMap<>();
        Deque<Coordinate> current = new ArrayDeque<>();
        current.add(start);
        for(int i = 0; i < steps; i++) {
            int size = current.size();
            for(int j = 0; j < size; j++) {
                Coordinate coord = current.poll();
                for(Direction dir : Direction.values()) {
                    Coordinate next = coord.move(dir, 1);
                    if(!plots.contains(next)) {
                        continue;
                    }
                    if(reachable.containsKey(next)) {
                        continue;
                    }
                    current.add(next);
                    reachable.put(next, i + 1);
                }
            }
        }
        return reachable;
    }
}

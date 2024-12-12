package me.datafox.aoc.aoc2024;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Advent of Code 2024 day 11 solutions.
 *
 * @author datafox
 */
public class Day12 {
    public static int solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        Set<Set<Coordinate>> regions = parseRegions(map);
        return regions.stream().mapToInt(Day12::calculateCost).sum();
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static Set<Set<Coordinate>> parseRegions(char[][] map) {
        Set<Set<Coordinate>> regions = new HashSet<>();
        Set<Coordinate> visited = new HashSet<>();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                Coordinate coord = new Coordinate(j, i);
                if(visited.contains(coord)) {
                    continue;
                }
                Set<Coordinate> region = parseRegionRecursive(map, coord);
                visited.addAll(region);
                regions.add(region);
            }
        }
        return regions;
    }

    private static Set<Coordinate> parseRegionRecursive(char[][] map, Coordinate coord) {
        Set<Coordinate> region = new HashSet<>();
        region.add(coord);
        char c = map[coord.y()][coord.x()];
        parseRegionRecursive(map, c, region, coord);
        return region;
    }

    private static void parseRegionRecursive(char[][] map, char c, Set<Coordinate> region, Coordinate coord) {
        for(Direction dir : Direction.values()) {
            Coordinate next = coord.move(dir, 1);
            if(region.contains(next)) {
                continue;
            }
            if(!next.isWithinBounds(0, 0, map[0].length - 1, map.length - 1)) {
                continue;
            }
            if(map[next.y()][next.x()] == c) {
                region.add(next);
                parseRegionRecursive(map, c, region, next);
            }
        }
    }

    private static int calculateCost(Set<Coordinate> region) {
        int boundary = 0;
        for(Coordinate coord : region) {
            for(Direction dir : Direction.values()) {
                Coordinate next = coord.move(dir, 1);
                if(!region.contains(next)) {
                    boundary++;
                }
            }
        }
        return boundary * region.size();
    }
}

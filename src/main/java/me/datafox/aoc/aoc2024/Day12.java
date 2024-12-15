package me.datafox.aoc.aoc2024;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;

/**
 * Advent of Code 2024 day 12 solutions.
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
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        Set<Set<Coordinate>> regions = parseRegions(map);
        return regions.stream().mapToInt(Day12::calculateCostPart2).sum();
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

    private static int calculateCostPart2(Set<Coordinate> region) {
        if(region.isEmpty()) {
            return 0;
        }
        int minX = region.stream().mapToInt(Coordinate::x).min().getAsInt();
        int maxX = region.stream().mapToInt(Coordinate::x).max().getAsInt();
        int minY = region.stream().mapToInt(Coordinate::y).min().getAsInt();
        int maxY = region.stream().mapToInt(Coordinate::y).max().getAsInt();
        int edges = 0;
        Map<Direction,Set<Coordinate>> visited = new HashMap<>();
        Arrays.stream(Direction.values()).forEach(dir -> visited.put(dir, new HashSet<>()));
        for(Coordinate coord : region) {
            for(Direction dir : Direction.values()) {
                Coordinate next = coord.move(dir, 1);
                if(region.contains(next) || visited.get(dir).contains(next)) {
                    continue;
                }
                boolean horizontal = dir.equals(Direction.UP) || dir.equals(Direction.DOWN);
                Coordinate check = next;
                do {
                    visited.get(dir).add(check);
                    if(horizontal) {
                        check = check.move(-1, 0);
                    } else {
                        check = check.move(0, -1);
                    }
                } while(!region.contains(check) &&
                        (horizontal ? check.x() : check.y()) >= (horizontal ? minX : minY) &&
                        region.contains(check.move(dir.opposite(), 1)));
                check = next;
                do {
                    visited.get(dir).add(check);
                    if(horizontal) {
                        check = check.move(1, 0);
                    } else {
                        check = check.move(0, 1);
                    }
                } while(!region.contains(check) && (horizontal ? check.x() : check.y()) <= (horizontal ? maxX : maxY) &&
                        region.contains(check.move(dir.opposite(), 1)));
                edges++;
            }
        }
        return edges * region.size();
    }
}

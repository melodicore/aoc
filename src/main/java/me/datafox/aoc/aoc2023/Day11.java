package me.datafox.aoc.aoc2023;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;

/**
 * Advent of Code 2023 day 11 solutions.
 *
 * @author datafox
 */
public class Day11 {
    public static int solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        Set<Coordinate> galaxies = getGalaxies(map);
        galaxies = expandSpace(galaxies);
        List<Coordinate[]> pairs = getPairs(galaxies);
        return pairs.stream().mapToInt(Day11::calculateDistance).sum();
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static Set<Coordinate> getGalaxies(char[][] map) {
        Set<Coordinate> set = new HashSet<>();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                if(map[i][j] == '#') {
                    set.add(new Coordinate(j, i));
                }
            }
        }
        return set;
    }

    private static Set<Coordinate> expandSpace(Set<Coordinate> galaxies) {
        int maxX = galaxies.stream().mapToInt(Coordinate::x).max().orElse(0);
        int maxY = galaxies.stream().mapToInt(Coordinate::y).max().orElse(0);
        int offset = 0;
        Set<Coordinate> result = new HashSet<>();
        for(int x = 0; x <= maxX; x++) {
            boolean empty = true;
            for(int y = 0; y <= maxY; y++) {
                Coordinate coord = new Coordinate(x, y);
                if(galaxies.contains(coord)) {
                    result.add(coord.move(offset, 0));
                    empty = false;
                }
            }
            if(empty) {
                offset++;
            }
        }
        galaxies = result;
        maxX += offset;
        offset = 0;
        result = new HashSet<>();
        for(int y = 0; y <= maxY; y++) {
            boolean empty = true;
            for(int x = 0; x <= maxX; x++) {
                Coordinate coord = new Coordinate(x, y);
                if(galaxies.contains(coord)) {
                    result.add(coord.move(0, offset));
                    empty = false;
                }
            }
            if(empty) {
                offset++;
            }
        }
        return result;
    }

    private static List<Coordinate[]> getPairs(Set<Coordinate> galaxies) {
        Coordinate[] arr = galaxies.toArray(Coordinate[]::new);
        List<Coordinate[]> pairs = new ArrayList<>();
        for(int i = 0; i < arr.length; i++) {
            for(int j = i + 1; j < arr.length; j++) {
                pairs.add(new Coordinate[] { arr[i], arr[j] });
            }
        }
        return pairs;
    }

    private static int calculateDistance(Coordinate[] pair) {
        return pair[0].distance(pair[1]);
    }
}

package me.datafox.aoc2024;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Advent of Code 2024 day 8 solutions.
 *
 * @author datafox
 */
public class Day8 {
    public static long solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        Map<Character,Set<Coordinate>> antennas = getAntennas(map);
        return antennas.values()
                .stream()
                .flatMap(coords -> getAntinodes(coords, map[0].length, map.length))
                .distinct()
                .count();
    }

    public static long solve2(URL url) {
        return 0;
    }

    private static Map<Character,Set<Coordinate>> getAntennas(char[][] map) {
        Map<Character,Set<Coordinate>> antennas = new HashMap<>();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                char c = map[i][j];
                if(c == '.') {
                    continue;
                }
                if(!antennas.containsKey(c)) {
                    antennas.put(c, new HashSet<>());
                }
                antennas.get(c).add(new Coordinate(j, i));
            }
        }
        return antennas;
    }

    private static Stream<Coordinate> getAntinodes(Set<Coordinate> coords, int width, int height) {
        Set<Coordinate> antinodes = new HashSet<>();
        for(Coordinate c1 : coords) {
            for(Coordinate c2 : coords) {
                if(c1.equals(c2)) {
                    continue;
                }
                Coordinate a = c1.move(c1.diff(c2));
                if(a.isWithinBounds(0, 0, width - 1, height - 1)) {
                    antinodes.add(a);
                }
            }
        }
        return antinodes.stream();
    }
}

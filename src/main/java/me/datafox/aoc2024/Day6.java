package me.datafox.aoc2024;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Advent of Code 2024 day 6 solutions.
 *
 * @author datafox
 */
public class Day6 {
    public static int solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        int width = map[0].length;
        int height = map.length;
        Set<Coordinate> obstacles = getOccurrences(map, '#');
        Set<Coordinate> visited = new HashSet<>();
        Direction dir = Direction.UP;
        Coordinate current = getOccurrences(map, '^').iterator().next();
        visited.add(current);
        while(true) {
            Coordinate next = current.move(dir, 1);
            if(obstacles.contains(next)) {
                dir = dir.rotateRight();
                continue;
            }
            if(!next.isWithinBounds(0, 0, width - 1, height - 1)) {
                break;
            }
            current = next;
            visited.add(current);
        }
        return visited.size();
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static Set<Coordinate> getOccurrences(char[][] map, char c) {
        Set<Coordinate> set = new HashSet<>();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                if(map[i][j] == c) {
                    set.add(new Coordinate(j, i));
                }
            }
        }
        return set;
    }
}

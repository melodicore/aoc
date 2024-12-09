package me.datafox.aoc.aoc2024;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

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
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        int width = map[0].length;
        int height = map.length;
        Set<Coordinate> obstacles = getOccurrences(map, '#');
        Set<Coordinate> extras = new HashSet<>();
        Direction dir = Direction.UP;
        Coordinate current = getOccurrences(map, '^').iterator().next();
        Coordinate start = current;
        while(true) {
            Coordinate next = current.move(dir, 1);
            if(!extras.contains(next) && tryPlacement(obstacles, next, start, Direction.UP, width, height)) {
                extras.add(next);
            }
            if(obstacles.contains(next)) {
                dir = dir.rotateRight();
                continue;
            }
            if(!next.isWithinBounds(0, 0, width - 1, height - 1)) {
                break;
            }
            current = next;
        }
        return extras.size();
    }

    private static boolean tryPlacement(Set<Coordinate> obstacles, Coordinate placement, Coordinate current, Direction dir, int width, int height) {
        Set<Move> turns = new HashSet<>();
        while(true) {
            Coordinate next = current.move(dir, 1);
            if(placement.equals(next) || obstacles.contains(next)) {
                dir = dir.rotateRight();
                if(!turns.add(new Move(current, dir))) {
                    return true;
                }
                continue;
            }
            if(!next.isWithinBounds(0, 0, width - 1, height - 1)) {
                return false;
            }
            current = next;
        }
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

    private record Move(Coordinate coord, Direction dir) {}
}

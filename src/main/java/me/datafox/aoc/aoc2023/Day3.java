package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Advent of Code 2023 day 3 solutions.
 *
 * @author datafox
 */
public class Day3 {
    public static int solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        return getParts(map).sum();
    }

    public static int solve2(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        return getGears(map)
                .filter(i -> i.length == 2)
                .mapToInt(i -> i[0] * i[1])
                .sum();
    }

    private static IntStream getParts(char[][] map) {
        List<String> parts = new ArrayList<>();
        for(int i = 0; i < map.length; i++) {
            StringBuilder sb = new StringBuilder();
            boolean adjacent = false;
            for(int j = 0; j < map[i].length; j++) {
                char c = map[i][j];
                if(Character.isDigit(c)) {
                    sb.append(c);
                    if(!adjacent && checkAdjacent(map, i, j)) {
                        adjacent = true;
                    }
                } else {
                    if(!sb.isEmpty()) {
                        if(adjacent) {
                            parts.add(sb.toString());
                            adjacent = false;
                        }
                        sb = new StringBuilder();
                    }
                }
            }
            if(adjacent && !sb.isEmpty()) {
                parts.add(sb.toString());
            }
        }
        return parts.stream().mapToInt(Integer::parseInt);
    }

    private static boolean checkAdjacent(char[][] map, int i, int j) {
        for(int i1 = i - 1; i1 <= i + 1; i1++) {
            for(int j1 = j - 1; j1 <= j + 1; j1++) {
                if(i1 < 0 || i1 >= map.length || j1 < 0 || j1 >= map[i1].length) {
                    continue;
                }
                char c = map[i1][j1];
                if(c != '.' && !Character.isDigit(c)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Stream<int[]> getGears(char[][] map) {
        List<int[]> gears = new ArrayList<>();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                if(map[i][j] == '*') {
                    gears.add(getAdjacent(map, i, j));
                }
            }
        }
        return gears.stream();
    }

    private static int[] getAdjacent(char[][] map, int i, int j) {
        Set<String> numbers = new HashSet<>();
        for(int i1 = i - 1; i1 <= i + 1; i1++) {
            for(int j1 = j - 1; j1 <= j + 1; j1++) {
                if(i1 < 0 || i1 >= map.length || j1 < 0 || j1 >= map[i1].length) {
                    continue;
                }
                char c = map[i1][j1];
                if(Character.isDigit(c)) {
                    numbers.add(getNumber(map[i1], j1));
                }
            }
        }
        return numbers.stream().mapToInt(Integer::parseInt).toArray();
    }

    private static String getNumber(char[] chars, int index) {
        do {
            index--;
        } while(index >= 0 && Character.isDigit(chars[index]));
        index++;
        StringBuilder sb = new StringBuilder();
        while(true) {
            if(index >= chars.length || !Character.isDigit(chars[index])) {
                break;
            }
            sb.append(chars[index]);
            index++;
        }
        return sb.toString();
    }
}

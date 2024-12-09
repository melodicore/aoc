package me.datafox.aoc.aoc2024;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.function.IntFunction;

/**
 * Advent of Code 2024 day 4 solutions.
 *
 * @author datafox
 */
public class Day4 {
    public static int solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        return countXMAS(map);
    }

    public static int solve2(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        return countMAS(map);
    }

    private static int countXMAS(char[][] map) {
        assert map.length == map[0].length;
        int count = 0;
        for(int i = 0; i < map.length; i++) {
            int temp = i;
            count += countXMAS(j -> map[temp][j], map.length);
            count += countXMAS(j -> map[temp][map.length - j - 1], map.length);

            count += countXMAS(j -> map[j][temp], map.length);
            count += countXMAS(j -> map[map.length - j - 1][temp], map.length);
            if(i == 0) {
                count += countXMAS(j -> map[j][j], map.length);
                count += countXMAS(j -> map[j][map.length - j - 1], map.length);
                count += countXMAS(j -> map[map.length - j - 1][j], map.length);
                count += countXMAS(j -> map[map.length - j - 1][map.length - j - 1], map.length);
            } else {
                count += countXMAS(j -> map[j][j + temp], map.length - i);
                count += countXMAS(j -> map[j + temp][j], map.length - i);
                count += countXMAS(j -> map[j][map.length - j - 1 - temp], map.length - i);
                count += countXMAS(j -> map[j + temp][map.length - j - 1], map.length - i);
                count += countXMAS(j -> map[map.length - j - 1][j + temp], map.length - i);
                count += countXMAS(j -> map[map.length - j - 1 - temp][j], map.length - i);
                count += countXMAS(j -> map[map.length - j - 1][map.length - j - 1 - temp], map.length - i);
                count += countXMAS(j -> map[map.length - j - 1 - temp][map.length - j - 1], map.length - i);
            }
        }
        return count;
    }

    private static int countXMAS(IntFunction<Character> fn, int length) {
        int count = 0;
        char lastValid = ' ';
        for(int i = 0; i < length; i++) {
            char c = fn.apply(i);
            switch(c) {
                case 'X' -> lastValid = c;
                case 'M' -> {
                    if(lastValid == 'X') {
                        lastValid = c;
                    } else {
                        lastValid = ' ';
                    }
                }
                case 'A' -> {
                    if(lastValid == 'M') {
                        lastValid = c;
                    } else {
                        lastValid = ' ';
                    }
                }
                case 'S' -> {
                    if(lastValid == 'A') {
                        count++;
                        lastValid = c;
                    } else {
                        lastValid = ' ';
                    }
                }
                default -> lastValid = ' ';
            }
        }
        return count;
    }

    private static int countMAS(char[][] map) {
        assert map.length == map[0].length;
        int count = 0;
        for(int i = 1; i < map.length - 1; i++) {
            for(int j = 1; j < map.length - 1; j++) {
                if(isMAS(map, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isMAS(char[][] map, int i, int j) {
        if(map[i][j] != 'A') {
            return false;
        }
        char c1 = map[i - 1][j - 1];
        char c2 = map[i + 1][j + 1];
        char c3 = map[i - 1][j + 1];
        char c4 = map[i + 1][j - 1];
        if(c1 == 'M') {
            if(c2 != 'S') {
                return false;
            }
        } else if(c1 == 'S') {
            if(c2 != 'M') {
                return false;
            }
        } else {
            return false;
        }
        if(c3 == 'M') {
            if(c4 != 'S') {
                return false;
            }
        } else if(c3 == 'S') {
            if(c4 != 'M') {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}

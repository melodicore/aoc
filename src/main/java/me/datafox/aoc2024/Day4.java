package me.datafox.aoc2024;

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

    public static int solve2(URL url) {
        return 0;
    }
}

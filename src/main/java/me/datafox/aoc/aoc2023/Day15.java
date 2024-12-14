package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.Arrays;

/**
 * Advent of Code 2023 day 15 solutions.
 *
 * @author datafox
 */
public class Day15 {
    public static int solve1(URL url) {
        return Arrays.stream(FileUtils.string(url).split(","))
                .mapToInt(Day15::hash)
                .sum();
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static int hash(String s) {
        int hash = 0;
        for(int i : s.toCharArray()) {
            hash += i;
            hash *= 17;
            hash %= 256;
        }
        return hash;
    }
}

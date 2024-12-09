package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;
import me.datafox.aoc.StringUtils;

import java.net.URL;

/**
 * Advent of Code 2023 day 1 solutions.
 *
 * @author datafox
 */
public class Day1 {
    public static int solve1(URL url) {
        return FileUtils.linesAsStream(url)
                .mapToInt(Day1::getNumber)
                .sum();
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static int getNumber(String str) {
        int first = str.chars()
                .filter(Character::isDigit)
                .boxed()
                .findFirst()
                .map(Character::toString)
                .map(Integer::parseInt)
                .orElse(0);
        int last = StringUtils.reverse(str)
                .chars()
                .filter(Character::isDigit)
                .boxed()
                .findFirst()
                .map(Character::toString)
                .map(Integer::parseInt)
                .orElse(0);
        return first * 10 + last;
    }
}

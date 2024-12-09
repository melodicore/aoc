package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;
import me.datafox.aoc.StringUtils;

import java.net.URL;
import java.util.List;

/**
 * Advent of Code 2023 day 1 solutions.
 *
 * @author datafox
 */
public class Day1 {
    private static final List<String> NUMBERS = List.of(
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

    public static int solve1(URL url) {
        return FileUtils.linesAsStream(url)
                .mapToInt(Day1::getNumber)
                .sum();
    }

    public static int solve2(URL url) {
        return FileUtils.linesAsStream(url)
                .map(Day1::numberToDigit)
                .mapToInt(Day1::getNumber)
                .sum();
    }

    private static String numberToDigit(String s) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            int temp = i;
            NUMBERS.stream()
                    .filter(n -> s.startsWith(n, temp))
                    .findFirst()
                    .map(NUMBERS::indexOf)
                    .map(n -> n + 1)
                    .ifPresent(sb::append);
            sb.append(s.charAt(i));
        }
        return sb.toString();
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

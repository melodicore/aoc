package me.datafox.aoc2024;

import java.net.URL;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Advent of Code 2024 day 3 solutions.
 *
 * @author datafox
 */
public class Day3 {
    public static int solve1(URL url) {
        return FileUtils.linesAsStream(url)
                .map(s -> s.split("mul\\("))
                .flatMap(Arrays::stream)
                .flatMapToInt(Day3::multiplyIfValid)
                .sum();
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static IntStream multiplyIfValid(String str) {
        if(str.isBlank()) {
            return IntStream.empty();
        }
        if(!Character.isDigit(str.charAt(0))) {
            return IntStream.empty();
        }
        String[] split = str.split(",", 2);
        if(split.length < 2) {
            return IntStream.empty();
        }
        int x;
        try {
            x = Integer.parseInt(split[0]);
        } catch(NumberFormatException ignored) {
            return IntStream.empty();
        }
        split = split[1].split("\\)", 2);
        int y;
        try {
            y = Integer.parseInt(split[0]);
        } catch(NumberFormatException ignored) {
            return IntStream.empty();
        }
        return IntStream.of(x * y);
    }
}

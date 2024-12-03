package me.datafox.aoc2024;

import java.net.URL;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Advent of Code 2024 day 3 solutions.
 *
 * @author datafox
 */
public class Day3 {
    public static int solve1(URL url) {
        return FileUtils.linesAsStream(url)
                .flatMap(StreamUtils.split("mul\\("))
                .flatMapToInt(Day3::multiplyIfValid)
                .sum();
    }

    public static int solve2(URL url) {
        return Stream.of(FileUtils.linesAsStream(url)
                        .collect(Collectors.joining("")))
                .map(Day3::removeDonts)
                .flatMap(StreamUtils.split("mul\\("))
                .flatMapToInt(Day3::multiplyIfValid)
                .sum();
    }

    private static String removeDonts(String str) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while(true) {
            int j = str.indexOf("don't()", i);
            if(j == -1) {
                sb.append(str, i, str.length());
                break;
            }
            sb.append(str, i, j);
            j = str.indexOf("do()", j);
            if(j == -1) {
                break;
            }
            i = j;
        }
        return sb.toString();
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

package me.datafox.aoc.aoc2024;

import me.datafox.aoc.FileUtils;
import me.datafox.aoc.StreamUtils;

import java.net.URL;
import java.util.Optional;
import java.util.stream.IntStream;

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
        return Optional.of(FileUtils.string(url))
                .map(Day3::removeDonts)
                .stream()
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

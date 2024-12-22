package me.datafox.aoc.aoc2024;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.stream.LongStream;

/**
 * Advent of Code 2024 day 22 solutions.
 *
 * @author datafox
 */
public class Day22 {
    public static long solve1(URL url) {
        long[] numbers = FileUtils.linesAsStream(url)
                .mapToLong(Long::parseLong)
                .toArray();
        for(int i = 0; i < 2000; i++) {
            for(int j = 0; j < numbers.length; j++) {
                numbers[j] ^= numbers[j] * 64;
                numbers[j] %= 16777216;
                numbers[j] ^= numbers[j] / 32;
                numbers[j] %= 16777216;
                numbers[j] ^= numbers[j] * 2048;
                numbers[j] %= 16777216;
            }
        }
        return LongStream.of(numbers).sum();
    }

    public static int solve2(URL url) {
        return 0;
    }
}

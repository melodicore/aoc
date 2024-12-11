package me.datafox.aoc.aoc2024;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.stream.LongStream;

/**
 * Advent of Code 2024 day 11 solutions.
 *
 * @author datafox
 */
public class Day11 {
    public static long solve1(URL url) {
        LongStream stream = Arrays.stream(FileUtils.string(url).split(" "))
                .mapToLong(Long::parseLong);
        for(int i = 0; i < 25; i++) {
            stream = stream.flatMap(Day11::blink);
        }
        return stream.count();
    }

    public static long solve2(URL url) {
        return 0;
    }

    private static LongStream blink(long l) {
        if(l == 0) {
            return LongStream.of(1);
        }
        String s = Long.toString(l);
        if((s.length() & 1) == 0) {
            return LongStream.of(Long.parseLong(s.substring(0, s.length() / 2)),
                    Long.parseLong(s.substring(s.length() / 2)));
        }
        return LongStream.of(l * 2024);
    }
}

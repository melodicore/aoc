package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.Arrays;

/**
 * Advent of Code 2023 day 6 solutions.
 *
 * @author datafox
 */
public class Day6 {
    public static long solve1(URL url) {
        String[] split = FileUtils.linesAsStream(url)
                .map(s -> s.split(".+: +", 2)[1])
                .toArray(String[]::new);
        assert split.length == 2;
        long[] times = Arrays.stream(split[0].split(" +"))
                .mapToLong(Long::parseLong)
                .toArray();
        long[] distances = Arrays.stream(split[1].split(" +"))
                .mapToLong(Long::parseLong)
                .toArray();
        assert times.length == distances.length;
        long solution = 1;
        for(int i = 0; i < times.length; i++) {
            solution *= getPossibilities(times[i], distances[i]);
        }
        return solution;
    }

    public static long solve2(URL url) {
        return 0;
    }

    private static long getPossibilities(long time, long distance) {
        long possibilities = 0;
        for(long i = 1; i < time; i++) {
            if(i * (time - i) > distance) {
                possibilities++;
            }
        }
        return possibilities;
    }
}

package me.datafox.aoc2024;

import java.net.URL;
import java.util.Arrays;

/**
 * Advent of Code 2024 day 7 solutions.
 *
 * @author datafox
 */
public class Day7 {
    public static long solve1(URL url) {
        return FileUtils.linesAsStream(url)
                .mapToLong(Day7::solveEquation)
                .sum();
    }

    public static long solve2(URL url) {
        return 0;
    }

    private static long solveEquation(String str) {
        String[] split = str.split(": ", 2);
        long answer = Long.parseLong(split[0]);
        long[] params = Arrays.stream(split[1].split(" "))
                .mapToLong(Long::parseLong)
                .toArray();
        int iterations = 2;
        for(int i = 0; i < params.length - 2; i++) {
            iterations *= 2;
        }
        for(int i = 0; i < iterations; i++) {
            long l = params[0];
            for(int j = 0; j < params.length - 1; j++) {
                if((i & (1 << j)) >> j == 0) {
                    l += params[j + 1];
                } else {
                    l *= params[j + 1];
                }
            }
            if(l == answer) {
                return answer;
            }
        }
        return 0;
    }
}

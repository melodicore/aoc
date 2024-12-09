package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.stream.LongStream;

/**
 * Advent of Code 2023 day 5 solutions.
 *
 * @author datafox
 */
public class Day5 {
    public static long solve1(URL url) {
        String[] maps = FileUtils.string(url).split("seeds: |\\n\\n.+:\\n");
        long[] seeds = Arrays.stream(maps[1].split(" "))
                .mapToLong(Long::parseLong)
                .toArray();
        Range[][] ranges = new Range[maps.length - 2][];
        for(int i = 0; i < maps.length - 2; i++) {
            String str = maps[i + 2];
            ranges[i] = str.lines()
                    .map(Range::parse)
                    .toArray(Range[]::new);
        }
        return LongStream.of(seeds)
                .map(s -> calculatePosition(s, ranges))
                .min()
                .orElse(0);
    }

    public static long solve2(URL url) {
        return 0;
    }

    private static long calculatePosition(long seed, Range[][] ranges) {
        for(Range[] arr : ranges) {
            for(Range range : arr) {
                long temp = range.convert(seed);
                if(temp != seed) {
                    seed = temp;
                    break;
                }
            }
        }
        return seed;
    }

    private record Range(long dest, long source, long range) {
        public static Range parse(String str) {
            long[] arr = Arrays.stream(str.split(" "))
                    .mapToLong(Long::parseLong)
                    .toArray();
            assert arr.length == 3;
            return new Range(arr[0], arr[1], arr[2]);
        }

        public long convert(long input) {
            if(input >= source && input < source + range) {
                return dest + (input - source);
            }
            return input;
        }
    }
}

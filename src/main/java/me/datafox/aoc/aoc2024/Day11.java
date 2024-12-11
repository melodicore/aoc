package me.datafox.aoc.aoc2024;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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
        Map<Long,Long> rocks = new LinkedHashMap<>(Arrays
                .stream(FileUtils.string(url).split(" "))
                .map(Long::parseLong)
                .collect(Collectors.toMap(Function.identity(), l -> 1L)));
        for(int i = 0; i < 75; i++) {
            Map<Long,Long> copy = new LinkedHashMap<>(rocks);
            rocks.clear();
            for(Map.Entry<Long,Long> e : copy.entrySet()) {
                if(e.getKey() == 0) {
                    rocks.put(1L, rocks.getOrDefault(1L, 0L) + e.getValue());
                } else {
                    String s = Long.toString(e.getKey());
                    if((s.length() & 1) == 0) {
                        Long l = Long.parseLong(s.substring(0, s.length() / 2));
                        rocks.put(l, rocks.getOrDefault(l, 0L) + e.getValue());
                        l = Long.parseLong(s.substring(s.length() / 2));
                        rocks.put(l, rocks.getOrDefault(l, 0L) + e.getValue());
                    } else {
                        Long l = e.getKey() * 2024;
                        rocks.put(l, rocks.getOrDefault(l, 0L) + e.getValue());
                    }
                }
            }
        }
        return rocks.values().stream().mapToLong(Long::longValue).sum();
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

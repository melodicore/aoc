package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Advent of Code 2023 day 8 solutions.
 *
 * @author datafox
 */
public class Day8 {
    public static int solve1(URL url) {
        String[] split = FileUtils.string(url).split("\n\n", 2);
        char[] rule = split[0].toCharArray();
        Map<String,String[]> map = split[1].lines()
                .map(Day8::parseMap)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
        int steps = 0;
        String current = "AAA";
        do {
            char c = rule[steps % rule.length];
            current = map.get(current)[c == 'L' ? 0 : 1];
            steps++;
        } while(!current.equals("ZZZ"));
        return steps;
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static Map.Entry<String,String[]> parseMap(String str) {
        String[] split = str.split(" = \\(", 2);
        return new AbstractMap.SimpleEntry<>(split[0], Arrays.copyOf(split[1].split(", |\\)"), 2));
    }
}

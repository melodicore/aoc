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
        return getSteps(rule, map, "AAA");
    }

    public static long solve2(URL url) {
        String[] split = FileUtils.string(url).split("\n\n", 2);
        char[] rule = split[0].toCharArray();
        Map<String,String[]> map = split[1].lines()
                .map(Day8::parseMap)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
        int[] loops = getLoops(rule, map);
        return leastCommonMultiple(loops);
    }

    private static Map.Entry<String,String[]> parseMap(String str) {
        String[] split = str.split(" = \\(", 2);
        return new AbstractMap.SimpleEntry<>(split[0], Arrays.copyOf(split[1].split(", |\\)"), 2));
    }

    private static int[] getLoops(char[] rule, Map<String,String[]> map) {
        return map.keySet()
                .stream()
                .filter(s -> s.endsWith("A"))
                .mapToInt(s -> getSteps(rule, map, s))
                .toArray();
    }

    private static int getSteps(char[] rule, Map<String,String[]> map, String str) {
        int steps = 0;
        do {
            char c = rule[steps % rule.length];
            str = map.get(str)[c == 'L' ? 0 : 1];
            steps++;
        } while(!str.endsWith("Z"));
        return steps;
    }

    private static long leastCommonMultiple(int[] ints) {
        long lcm = 1;
        for(int i = 1; i < ints.length; i++) {
            if(i == 1) {
                lcm = leastCommonMultiple(ints[0], ints[1]);
            } else {
                lcm = leastCommonMultiple(lcm, ints[i]);
            }
        }
        return lcm;
    }

    private static long leastCommonMultiple(long l1, long l2) {
        if(l1 == l2) {
            return l1;
        }
        if(l1 < l2) {
            long temp = l1;
            l1 = l2;
            l2 = temp;
        }
        long lcm = l1;
        while(lcm % l2 != 0) {
            lcm += l1;
        }
        return lcm;
    }
}

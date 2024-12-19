package me.datafox.aoc.aoc2024;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;

/**
 * Advent of Code 2024 day 19 solutions.
 *
 * @author datafox
 */
public class Day19 {
    public static long solve1(URL url) {
        String[] split = FileUtils.string(url).split("\n\n");
        Map<Integer,Set<String>> patterns = Arrays
                .stream(split[0].split(", "))
                .reduce(new TreeMap<>(Comparator.reverseOrder()),
                        Day19::accumulatePatterns,
                        Day19::combinePatterns);
        return split[1].lines().filter(d -> isValidDesign(d, patterns)).count();
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static Map<Integer,Set<String>> accumulatePatterns(Map<Integer,Set<String>> map, String pattern) {
        if(!map.containsKey(pattern.length())) {
            map.put(pattern.length(), new HashSet<>());
        }
        map.get(pattern.length()).add(pattern);
        return map;
    }

    private static Map<Integer,Set<String>> combinePatterns(Map<Integer,Set<String>> map, Map<Integer,Set<String>> other) {
        for(Map.Entry<Integer,Set<String>> e : other.entrySet()) {
            if(map.containsKey(e.getKey())) {
                map.get(e.getKey()).addAll(e.getValue());
            } else {
                map.put(e.getKey(), e.getValue());
            }
        }
        return map;
    }

    private static boolean isValidDesign(String design, Map<Integer,Set<String>> patterns) {
        return isValidDesign("", design, new HashSet<>(), patterns);
    }

    private static boolean isValidDesign(String current, String design, Set<String> fails, Map<Integer,Set<String>> patterns) {
        if(fails.stream().noneMatch(current::startsWith) && patterns.entrySet().stream().anyMatch(entry -> {
            int length = current.length() + entry.getKey();
            if(length > design.length()) {
                return false;
            }
            String check = design.substring(current.length(), length);
            return entry.getValue().contains(check) &&
                    (length == design.length() || isValidDesign(current + check, design, fails, patterns));
        })) {
            return true;
        }
        fails.add(current);
        return false;
    }
}

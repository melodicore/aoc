package me.datafox.aoc.aoc2024;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;

/**
 * Advent of Code 2024 day 23 solutions.
 *
 * @author datafox
 */
public class Day23 {
    public static long solve1(URL url) {
        Map<String,Set<String>> connections = FileUtils.linesAsStream(url)
                .map(s -> s.split("-"))
                .reduce(new HashMap<>(), Day23::accumulate, Day23::combine);
        Set<Set<String>> groups = getGroups(connections, 3);
        return groups.stream().filter(g -> g.stream().anyMatch(s -> s.startsWith("t"))).count();
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static Map<String,Set<String>> accumulate(Map<String,Set<String>> map, String[] connection) {
        if(!map.containsKey(connection[0])) {
            map.put(connection[0], new HashSet<>());
        }
        if(!map.containsKey(connection[1])) {
            map.put(connection[1], new HashSet<>());
        }
        map.get(connection[0]).add(connection[1]);
        map.get(connection[1]).add(connection[0]);
        return map;
    }

    private static Map<String,Set<String>> combine(Map<String,Set<String>> map1, Map<String,Set<String>> map2) {
        for(Map.Entry<String,Set<String>> entry : map2.entrySet()) {
            if(!map1.containsKey(entry.getKey())) {
                map1.put(entry.getKey(), new HashSet<>());
            }
            map1.get(entry.getKey()).addAll(entry.getValue());
        }
        return map1;
    }

    private static Set<Set<String>> getGroups(Map<String,Set<String>> connections, int count) {
        Set<Set<String>> groups = new HashSet<>();
        for(Map.Entry<String,Set<String>> entry : connections.entrySet()) {
            String s = entry.getKey();
            Set<String> con = entry.getValue();
            if(con.size() < count - 1) {
                continue;
            }
            groups.addAll(getGroups(connections, Set.of(s), con, count));
        }
        return groups;
    }

    private static Set<Set<String>> getGroups(Map<String,Set<String>> connections, Set<String> current, Set<String> con, int count) {
        Set<Set<String>> groups = new HashSet<>();
        for(String s : con) {
            if(current.contains(s)) {
                continue;
            }
            if(!connections.get(s).containsAll(current)) {
                continue;
            }
            Set<String> next = new HashSet<>(current);
            next.add(s);
            if(next.size() == count) {
                groups.add(next);
            } else {
                groups.addAll(getGroups(connections, next, con, count));
            }
        }
        return groups;
    }
}

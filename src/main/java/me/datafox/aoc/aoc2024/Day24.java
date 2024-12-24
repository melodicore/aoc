package me.datafox.aoc.aoc2024;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Advent of Code 2024 day 24 solutions.
 *
 * @author datafox
 */
public class Day24 {
    public static long solve1(URL url) {
        String[] split = FileUtils.string(url).split("\n\n");
        Map<String,Boolean> states = split[0].lines()
                .map(s -> s.split(": "))
                .collect(Collectors.toMap(
                        s -> s[0],
                        s -> s[1].equals("1")));
        Set<Operation> operations = parseOperations(split[1]);
        while(!operations.isEmpty()) {
            for(Operation op : new ArrayList<>(operations)) {
                if(!states.containsKey(op.a()) || !states.containsKey(op.b())) {
                    continue;
                }
                states.put(op.out(), op.gate().get(states.get(op.a()), states.get(op.b())));
                operations.remove(op);
            }
        }
        String out = states.entrySet().stream()
                .filter(e -> e.getKey().startsWith("z"))
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .map(Map.Entry::getValue)
                .map(b -> b ? "1" : "0")
                .collect(Collectors.joining());
        return Long.parseLong(out, 2);
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static Set<Operation> parseOperations(String str) {
        Set<Operation> operations = new LinkedHashSet<>();
        for(String s : str.split("\n")) {
            String[] split = s.split(" AND | OR | XOR | -> ");
            Gate gate = Arrays.stream(Gate.values())
                    .filter(g -> s.contains(" " + g.name()))
                    .findAny()
                    .orElseThrow();
            operations.add(new Operation(split[0], split[1], gate, split[2]));
        }
        return operations;
    }

    private enum Gate {
        AND,
        OR,
        XOR;

        public boolean get(boolean a, boolean b) {
            return switch(this) {
                case AND -> a && b;
                case OR -> a || b;
                case XOR -> a ^ b;
            };
        }
    }

    private record Operation(String a, String b, Gate gate, String out) {

    }
}

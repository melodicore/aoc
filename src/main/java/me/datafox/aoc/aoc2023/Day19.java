package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Advent of Code 2023 day 18 solutions.
 *
 * @author datafox
 */
public class Day19 {
    public static int solve1(URL url) {
        String[] split = FileUtils.string(url).split("\n\n");
        Map<String,Workflow> workflows = split[0].lines()
                .parallel()
                .map(Workflow::parse)
                .collect(Collectors.toMap(
                        Workflow::id,
                        Function.identity()));
        return split[1].lines()
                .parallel()
                .map(Day19::parsePart)
                .filter(p -> isAccepted(p, workflows))
                .map(EnumMap::values)
                .flatMap(Collection::stream)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static EnumMap<Cat,Integer> parsePart(String str) {
        EnumMap<Cat,Integer> part = new EnumMap<>(Cat.class);
        for(String cat : str.substring(1, str.length() - 1).split(",")) {
            String[] split = cat.split("=");
            part.put(Cat.valueOf(split[0]), Integer.parseInt(split[1]));
        }
        return part;
    }

    private static boolean isAccepted(EnumMap<Cat,Integer> part, Map<String,Workflow> workflows) {
        Workflow current = workflows.get("in");
        assert current != null;
        loop: while(true) {
            for(Rule rule : current.rules()) {
                int value = part.get(rule.cat);
                if(rule.greaterThan() ? (value > rule.value()) : (value < rule.value())) {
                    if(rule.target().equals("A")) {
                        return true;
                    }
                    if(rule.target().equals("R")) {
                        return false;
                    }
                    current = workflows.get(rule.target());
                    assert current != null;
                    continue loop;
                }
            }
            if(current.target().equals("A")) {
                return true;
            }
            if(current.target().equals("R")) {
                return false;
            }
            current = workflows.get(current.target());
            assert current != null;
        }
    }

    public static int solve2(URL url) {
        return 0;
    }

    private enum Cat {
        x,
        m,
        a,
        s
    }

    private record Rule(Cat cat, boolean greaterThan, int value, String target) {
        public static Rule parse(String str) {
            String[] split = str.split("[<>:]");
            assert split.length == 3;
            Cat cat = Cat.valueOf(split[0]);
            boolean greaterThan = str.contains(">");
            int value = Integer.parseInt(split[1]);
            return new Rule(cat, greaterThan, value, split[2]);
        }
    }

    private record Workflow(String id, Rule[] rules, String target) {
        @Override
        public String toString() {
            return "Workflow{" +
                    "id='" + id + '\'' +
                    ", rules=" + Arrays.toString(rules) +
                    ", target='" + target + '\'' +
                    '}';
        }

        public static Workflow parse(String str) {
            String[] split = str.split("\\{");
            assert split.length == 2;
            String id = split[0];
            split = split[1].substring(0, split[1].length() - 1).split(",");
            Rule[] rules = new Rule[split.length - 1];
            String target = null;
            for(int i = 0; i < split.length; i++) {
                if(i < split.length - 1) {
                    rules[i] = Rule.parse(split[i]);
                } else {
                    target = split[i];
                }
            }
            assert target != null;
            return new Workflow(id, rules, target);
        }
    }
}

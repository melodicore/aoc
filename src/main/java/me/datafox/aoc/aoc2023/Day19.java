package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Advent of Code 2023 day 19 solutions.
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

    public static long solve2(URL url) {
        Map<String,Workflow> workflows = FileUtils.string(url)
                .split("\n\n")[0]
                .lines()
                .parallel()
                .map(Workflow::parse)
                .collect(Collectors.toMap(
                        Workflow::id,
                        Function.identity()));
        return getAccepted(workflows).stream().mapToLong(PartRange::count).sum();
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

    private static List<PartRange> getAccepted(Map<String,Workflow> workflows) {
        SequencedMap<PartRange,String> ranges = new LinkedHashMap<>();
        ranges.put(new PartRange(), "in");
        List<PartRange> accepted = new ArrayList<>();
        loop: while(!ranges.isEmpty()) {
            Map.Entry<PartRange,String> entry = ranges.pollFirstEntry();
            PartRange part = entry.getKey();
            Workflow current = workflows.get(entry.getValue());
            assert current != null : entry.getValue();
            for(Rule rule : current.rules()) {
                PartRange[] split = part.split(rule.cat(), rule.greaterThan() ? rule.value() + 1 : rule.value());
                if(split.length == 1) {
                    if(rule.greaterThan() ? (part.ends().get(rule.cat()) > rule.value()) : (part.starts().get(rule.cat()) < rule.value())) {
                        if(rule.target().equals("A")) {
                            accepted.add(part);
                            continue loop;
                        }
                        if(rule.target().equals("R")) {
                            continue loop;
                        }
                        ranges.put(part, rule.target());
                    }
                    continue;
                }
                assert split.length == 2;
                PartRange success;
                if(rule.greaterThan()) {
                    part = split[0];
                    success = split[1];
                } else {
                    success = split[0];
                    part = split[1];
                }
                if(rule.target().equals("A")) {
                    accepted.add(success);
                    continue;
                }
                if(rule.target().equals("R")) {
                    continue;
                }
                ranges.put(success, rule.target());
            }
            if(current.target().equals("A")) {
                accepted.add(part);
                continue;
            }
            if(current.target().equals("R")) {
                continue;
            }
            ranges.put(part, current.target());
        }

        return accepted;
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

    private record PartRange(EnumMap<Cat,Integer> starts, EnumMap<Cat,Integer> ends, long count) {
        public PartRange() {
            this(new EnumMap<>(Arrays.stream(Cat.values())
                            .collect(Collectors.toMap(
                                    Function.identity(),
                                    c -> 1))),
                    new EnumMap<>(Arrays.stream(Cat.values())
                            .collect(Collectors.toMap(
                                    Function.identity(),
                                    c -> 4000))),
                    4000L * 4000L * 4000L * 4000L);
        }

        public PartRange[] split(Cat cat, int value) {
            int start = starts.get(cat);
            int end = ends.get(cat);
            if(value <= start || value > end) {
                return new PartRange[] { this };
            }
            int divisor = end - start + 1;
            long dividedCount = count / divisor;
            assert dividedCount * divisor == count;
            EnumMap<Cat,Integer> otherStarts = new EnumMap<>(starts);
            EnumMap<Cat,Integer> otherEnds = new EnumMap<>(ends);
            ends.put(cat, value - 1);
            otherStarts.put(cat, value);
            long newCount = dividedCount * (value - start);
            long otherCount = dividedCount * (end - value + 1);
            assert newCount + otherCount == count;
            return new PartRange[] { new PartRange(starts, ends, newCount), new PartRange(otherStarts, otherEnds, otherCount) };
        }
    }
}

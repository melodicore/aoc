package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Advent of Code 2023 day 20 solutions.
 *
 * @author datafox
 */
public class Day20 {
    public static int solve1(URL url) {
        Map<String,String[]> templates = FileUtils.linesAsStream(url)
                .map(s -> s.split(" -> "))
                .collect(Collectors.toMap(
                        s -> s[0],
                        s -> Arrays.stream(s[1].split(", "))
                                .toArray(String[]::new)));
        Map<String,Mod> modules = parseModules(templates);
        int lows = 0;
        int highs = 0;
        Deque<Map<String,Boolean>> presses = new ArrayDeque<>();
        Map<String,Boolean> button = Map.of("broadcaster|button", false);
        for(int i = 0; i < 1000; i++) {
            presses.add(button);
            while(!presses.isEmpty()) {
                Map<String,Boolean> next = presses.poll();
                for(Map.Entry<String,Boolean> e : next.entrySet()) {
                    if(e.getValue()) {
                        highs++;
                    } else {
                        lows++;
                    }
                    String[] split = e.getKey().split("\\|");
                    if(modules.containsKey(split[0])) {
                        presses.add(modules.get(split[0]).acceptPulse(split[1], e.getValue()));
                    }
                }
            }
        }
        return lows * highs;
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static Map<String,Mod> parseModules(Map<String,String[]> templates) {
        Map<String,Set<String>> reverse = getReverse(templates);
        Map<String,Mod> modules = new HashMap<>();
        for(Map.Entry<String,String[]> e : templates.entrySet()) {
            String name = e.getKey();
            String[] targets = e.getValue();
            if(name.equals("broadcaster")) {
                modules.put(name, new Broadcaster(appendName(name, targets)));
            } else if(name.charAt(0) == '%') {
                name = name.substring(1);
                modules.put(name, new Flip(appendName(name, targets)));
            } else if(name.charAt(0) == '&') {
                name = name.substring(1);
                modules.put(name, new Con(reverse.get(name), appendName(name, targets)));
            } else {
                assert false : name + ": " + Arrays.toString(targets);
            }
        }
        return modules;
    }

    private static String[] appendName(String name, String[] targets) {
        for(int i = 0; i < targets.length; i++) {
            targets[i] += "|" + name;
        }
        return targets;
    }

    private static Map<String,Set<String>> getReverse(Map<String,String[]> templates) {
        Map<String,Set<String>> reverse = new HashMap<>();
        for(Map.Entry<String,String[]> e : templates.entrySet()) {
            for(String s : e.getValue()) {
                if(!reverse.containsKey(s)) {
                    reverse.put(s, new HashSet<>());
                }
                reverse.get(s).add(e.getKey().equals("broadcaster") ? e.getKey() : e.getKey().substring(1));
            }
        }
        return reverse;
    }

    private interface Mod {
        Map<String,Boolean> acceptPulse(String from, boolean high);
    }

    private static class Broadcaster implements Mod {
        private final String[] targets;

        private Broadcaster(String[] targets) {
            this.targets = targets;
        }

        @Override
        public Map<String,Boolean> acceptPulse(String from, boolean high) {
            return Arrays.stream(targets)
                    .collect(Collectors.toMap(
                            Function.identity(),
                            s -> false));
        }

        @Override
        public String toString() {
            return "Broadcaster{" +
                    "targets=" + Arrays.toString(targets) +
                    '}';
        }
    }

    private static class Flip implements Mod {
        private final String[] targets;

        private boolean state;

        private Flip(String[] targets) {
            this.targets = targets;
            state = false;
        }

        @Override
        public Map<String,Boolean> acceptPulse(String from, boolean high) {
            if(high) {
                return Map.of();
            }
            state = !state;
            return Arrays.stream(targets)
                    .collect(Collectors.toMap(
                            Function.identity(),
                            s -> state));
        }

        @Override
        public String toString() {
            return "Flip{" +
                    "targets=" + Arrays.toString(targets) +
                    ", state=" + state +
                    '}';
        }
    }

    private static class Con implements Mod {
        private final String[] targets;

        private final Map<String,Boolean> states;

        private Con(Set<String> sources, String[] targets) {
            this.targets = targets;
            states = new HashMap<>(sources.stream()
                    .collect(Collectors.toMap(
                            Function.identity(),
                            s -> false)));
        }

        @Override
        public Map<String,Boolean> acceptPulse(String from, boolean high) {
            assert states.containsKey(from);
            states.put(from, high);
            boolean send = !states.values().stream().allMatch(b -> b);
            return Arrays.stream(targets)
                    .collect(Collectors.toMap(
                            Function.identity(),
                            s -> send));
        }

        @Override
        public String toString() {
            return "Con{" +
                    "targets=" + Arrays.toString(targets) +
                    ", states=" + states +
                    '}';
        }
    }
}

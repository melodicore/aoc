package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Advent of Code 2023 day 12 solutions.
 *
 * @author datafox
 */
public class Day13 {
    public static int solve1(URL url) {
        return Arrays.stream(FileUtils.string(url).split("\n\n"))
                .map(s -> s.lines().toList())
                .map(Map::new)
                .mapToInt(Map::getReflectionScore)
                .sum();
    }

    public static int solve2(URL url) {
        return 0;
    }

    private record Map(List<String> horizontal, List<String> vertical) {
        public Map(List<String> horizontal) {
            this(horizontal, getVertical(horizontal));
        }

        public int getReflectionScore() {
            int score = getReflectionScore(vertical);
            if(score == 0) {
                score = getReflectionScore(horizontal) * 100;
            }
            if(score == 0) {
                throw new RuntimeException();
            }
            return score;
        }

        private int getReflectionScore(List<String> list) {
            int i;
            loop: for(i = 1; i < list.size(); i++) {
                List<String> first = list.subList(0, i).reversed();
                List<String> second = list.subList(i, list.size());
                for(int j = 0; j < Math.min(first.size(), second.size()); j++) {
                    if(!first.get(j).equals(second.get(j))) {
                        continue loop;
                    }
                }
                return i;
            }
            return 0;
        }

        private static List<String> getVertical(List<String> horizontal) {
            if(horizontal.isEmpty()) {
                return List.of();
            }
            if(horizontal.size() == 1) {
                List<String> vertical = new ArrayList<>();
                for(char c : horizontal.getFirst().toCharArray()) {
                    vertical.add(String.valueOf(c));
                }
                return vertical;
            }
            List<StringBuilder> sbs = new ArrayList<>();
            for(String s : horizontal) {
                for(int i = 0; i < s.length(); i++) {
                    if(i == sbs.size()) {
                        sbs.add(new StringBuilder());
                    }
                    sbs.get(i).append(s.charAt(i));
                }
            }
            return sbs.stream().map(StringBuilder::toString).toList();
        }
    }
}

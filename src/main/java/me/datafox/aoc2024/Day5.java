package me.datafox.aoc2024;

import java.net.URL;
import java.util.Arrays;

/**
 * Advent of Code 2024 day 4 solutions.
 *
 * @author datafox
 */
public class Day5 {
    public static int solve1(URL url) {
        String[] split = FileUtils.string(url).split("\n\n");
        assert split.length == 2;
        int[][] rules = parseRules(split[0]);
        int[][] updates = parseUpdates(split[1]);
        return Arrays.stream(updates)
                .filter(u -> isValidUpdate(u, rules))
                .mapToInt(Day5::getMiddlePage)
                .sum();
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static int[][] parseRules(String str) {
        String[] split = str.split("\n");
        int[][] rules = new int[split.length][2];
        for(int i = 0; i < split.length; i++) {
            String[] rule = split[i].split("\\|");
            assert rule.length == 2;
            rules[i][0] = Integer.parseInt(rule[0]);
            rules[i][1] = Integer.parseInt(rule[1]);
        }
        return rules;
    }

    private static int[][] parseUpdates(String str) {
        String[] split = str.split("\n");
        int[][] updates = new int[split.length][];
        for(int i = 0; i < split.length; i++) {
            String[] update = split[i].split(",");
            updates[i] = new int[update.length];
            for(int j = 0; j < update.length; j++) {
                updates[i][j] = Integer.parseInt(update[j]);
            }
        }
        return updates;
    }

    private static boolean isValidUpdate(int[] pages, int[][] rules) {
        for(int i = 1; i < pages.length; i++) {
            for(int[] rule : rules) {
                if(isRuleBroken(pages, rule, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isRuleBroken(int[] pages, int[] rule, int index) {
        if(pages[index] != rule[0]) {
            return false;
        }
        for(int i = 0; i < index; i++) {
            if(pages[i] == rule[1]) {
                return true;
            }
        }
        return false;
    }

    private static int getMiddlePage(int[] pages) {
        assert pages.length % 2 == 1;
        return pages[pages.length / 2];
    }
}

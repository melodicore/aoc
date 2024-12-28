package me.datafox.aoc.aoc2024;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Advent of Code 2024 day 25 solutions.
 *
 * @author datafox
 */
public class Day25 {
    public static int solve1(URL url) {
        String[] split = FileUtils.string(url).split("\n\n");
        List<int[]> locks = new ArrayList<>();
        List<int[]> keys = new ArrayList<>();
        for(String template : split) {
            boolean lock = template.startsWith("#");
            assert lock || template.startsWith(".");
            int[] arr = new int[5];
            Arrays.fill(arr, -1);
            for(String row : template.split("\n")) {
                for(int i = 0; i < arr.length; i++) {
                    if(row.charAt(i) == '#') {
                        arr[i]++;
                    }
                }
            }
            (lock ? locks : keys).add(arr);
        }
        int count = 0;
        for(int[] lock : locks) {
            loop: for(int[] key : keys) {
                for(int i = 0; i < lock.length; i++) {
                    if(lock[i] + key[i] > 5) {
                        continue loop;
                    }
                }
                count++;
            }
        }
        return count;
    }

    public static int solve2(URL url) {
        return 0;
    }
}

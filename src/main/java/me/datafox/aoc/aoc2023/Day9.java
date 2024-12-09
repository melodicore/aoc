package me.datafox.aoc.aoc2023;

import me.datafox.aoc.ArrUtils;
import me.datafox.aoc.FileUtils;

import java.net.URL;

/**
 * Advent of Code 2023 day 9 solutions.
 *
 * @author datafox
 */
public class Day9 {
    public static int solve1(URL url) {
        return FileUtils.linesAsStream(url)
                .map(ArrUtils::splitRow)
                .mapToInt(Day9::extrapolate)
                .sum();
    }

    public static int solve2(URL url) {
        return FileUtils.linesAsStream(url)
                .map(ArrUtils::splitRow)
                .mapToInt(Day9::extrapolateBack)
                .sum();
    }

    private static int extrapolate(int[] arr) {
        int[] diff = new int[arr.length - 1];
        int last = 0;
        boolean finished = true;
        for(int i = 0; i < diff.length; i++) {
            diff[i] = arr[i + 1] - arr[i];
            if(i != 0) {
                if(last != diff[i]) {
                    finished = false;
                }
            }
            last = diff[i];
        }
        if(finished) {
            return arr[arr.length - 1] + last;
        } else {
            return arr[arr.length - 1] + extrapolate(diff);
        }
    }

    private static int extrapolateBack(int[] arr) {
        int[] diff = new int[arr.length - 1];
        int last = 0;
        boolean finished = true;
        for(int i = 0; i < diff.length; i++) {
            diff[i] = arr[i + 1] - arr[i];
            if(i != 0) {
                if(last != diff[i]) {
                    finished = false;
                }
            }
            last = diff[i];
        }
        if(finished) {
            return arr[0] - last;
        } else {
            return arr[0] - extrapolateBack(diff);
        }
    }
}

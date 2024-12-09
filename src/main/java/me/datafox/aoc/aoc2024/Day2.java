package me.datafox.aoc.aoc2024;

import me.datafox.aoc.ArrUtils;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.Arrays;

/**
 * Advent of Code 2024 day 2 solutions.
 *
 * @author datafox
 */
public class Day2 {
    public static long solve1(URL url) {
        return FileUtils.linesAsStream(url)
                .map(ArrUtils::splitRow)
                .filter(Day2::isAscendingOrDescending)
                .count();
    }

    public static long solve2(URL url) {
        return FileUtils.linesAsStream(url)
                .map(ArrUtils::splitRow)
                .map(Day2::getPermutations)
                .filter(Day2::isAnyAscendingOrDescending)
                .count();
    }

    public static long solve2efficient(URL url) {
        return FileUtils.linesAsStream(url)
                .map(ArrUtils::splitRow)
                .filter(Day2::isAscendingOrDescendingPart2)
                .count();
    }

    private static boolean isAnyAscendingOrDescending(int[][] arr) {
        return Arrays.stream(arr).anyMatch(Day2::isAscendingOrDescending);
    }

    private static boolean isAscendingOrDescending(int[] arr) {
        assert arr.length > 1;
        boolean ascending;
        int compare = Integer.compare(arr[0], arr[1]);
        if(compare < 0) {
            ascending = true;
        } else if(compare > 0) {
            ascending = false;
        } else {
            return false;
        }
        int last = -1;
        for(int i : arr) {
            if(last == -1) {
                last = i;
                continue;
            }
            int diff = last - i;
            if(ascending) {
                diff = -diff;
            }
            if(diff < 1 || diff > 3) {
                return false;
            }
            last = i;
        }
        return true;
    }

    private static int[][] getPermutations(int[] arr) {
        int[][] result = new int[arr.length + 1][arr.length - 1];
        result[0] = arr;
        for(int i = 0; i < arr.length; i++) {
            int k = 0;
            for(int j = 0; j < arr.length - 1; j++) {
                if(k == i) {
                    k++;
                }
                result[i + 1][j] = arr[k];
                k++;
            }
        }
        return result;
    }

    private static boolean isAscendingOrDescendingPart2(int[] arr) {
        assert arr.length > 2;
        boolean removed = false;
        boolean ascending;
        int compare = Integer.compare(arr[0], arr[1]);
        if(compare == 0) {
            compare = Integer.compare(arr[1], arr[2]);
        }
        if(compare < 0) {
            ascending = true;
        } else if(compare > 0) {
            ascending = false;
        } else {
            return false;
        }
        int last = -1;
        for(int i : arr) {
            if(last == -1) {
                last = i;
                continue;
            }
            int diff = last - i;
            if(ascending) {
                diff = -diff;
            }
            if(diff < 1 || diff > 3) {
                if(!removed) {
                    removed = true;
                } else {
                    return false;
                }
            }
            last = i;
        }
        return true;
    }
}

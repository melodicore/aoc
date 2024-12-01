package me.datafox.aoc2025;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author datafox
 */
public class Day1 {
    public static int solve1(URL url) {
        int[][] arr = FileUtils.resourceAsStream(url)
                .map(ArrUtils::splitRow)
                .toArray(int[][]::new);
        arr = ArrUtils.rotate(arr);
        assert arr.length == 2;
        Arrays.sort(arr[0]);
        Arrays.sort(arr[1]);
        arr = ArrUtils.rotate(arr);
        return Arrays.stream(arr).mapToInt(Day1::absoluteDifference).sum();
    }

    public static int solve2(URL url) {
        int[][] arr = FileUtils.resourceAsStream(url)
                .map(ArrUtils::splitRow)
                .toArray(int[][]::new);
        arr = ArrUtils.rotate(arr);
        assert arr.length == 2;
        int max = IntStream.concat(
                        Arrays.stream(arr[0]),
                        Arrays.stream(arr[1]))
                .max()
                .orElseThrow();
        int[] occ = new int[max + 1];
        Arrays.stream(arr[1]).forEach(i -> occ[i]++);
        return Arrays.stream(arr[0]).map(i -> i * occ[i]).sum();
    }

    private static int absoluteDifference(int[] arr) {
        assert arr.length == 2;
        return Math.abs(arr[0] - arr[1]);
    }
}

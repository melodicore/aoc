package me.datafox.aoc2025;

import java.net.URL;
import java.util.Arrays;

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

    private static int absoluteDifference(int[] arr) {
        assert arr.length == 2;
        return Math.abs(arr[0] - arr[1]);
    }
}

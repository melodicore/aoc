package me.datafox.aoc2024;

import java.util.Arrays;

/**
 * @author datafox
 */
public class ArrUtils {
    public static int[] splitRow(String row) {
        String[] split = row.split(" +");
        if(split[split.length - 1].isBlank()) {
            split = Arrays.copyOf(split, split.length - 1);
        }
        int[] arr = new int[split.length];
        for(int i = 0; i < split.length; i++) {
            arr[i] = Integer.parseInt(split[i]);
        }
        return arr;
    }

    public static int[][] rotate(int[][] input) {
        int max = 0;
        for(int[] arr : input) {
            max = Math.max(max, arr.length);
        }
        int[][] arr = new int[max][input.length];
        for(int i = 0; i < input.length; i++) {
            for(int j = 0; j < max; j++) {
                if(j >= input[i].length) {
                    continue;
                }
                arr[j][i] = input[i][j];
            }
        }
        return arr;
    }
}

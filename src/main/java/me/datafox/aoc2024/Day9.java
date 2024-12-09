package me.datafox.aoc2024;

import java.net.URL;
import java.util.stream.IntStream;

/**
 * Advent of Code 2024 day 9 solutions.
 *
 * @author datafox
 */
public class Day9 {
    public static long solve1(URL url) {
        int[] input = FileUtils.string(url)
                .chars()
                .map(cp -> Character.digit(cp, 10))
                .toArray();
        return getChecksum(compactData(parseData(input)));
    }

    public static long solve2(URL url) {
        return 0;
    }

    private static int[] parseData(int[] input) {
        int length = IntStream.of(input).sum();
        int[] data = new int[length];
        int pos = 0;
        for(int i = 0; i < input.length; i++) {
            for(int j = 0; j < input[i]; j++) {
                data[pos] = (i % 2 == 0) ? i / 2 : -1;
                pos++;
            }
        }
        return data;
    }

    private static int[] compactData(int[] data) {
        loop: for(int i = data.length - 1; i >= 0; i--) {
            if(data[i] == -1) {
                continue;
            }
            int j = 0;
            while(true) {
                if(data[j] == -1) {
                    data[j] = data[i];
                    data[i] = -1;
                    continue loop;
                }
                j++;
                if(j == i) {
                    break loop;
                }
            }
        }
        return data;
    }

    private static long getChecksum(int[] data) {
        long checksum = 0;
        for(int i = 0; i < data.length; i++) {
            if(data[i] == -1) {
                continue;
            }
            checksum += (long) data[i] * i;
        }
        return checksum;
    }
}

package me.datafox.aoc.aoc2024;

import me.datafox.aoc.FileUtils;

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
        int[] input = FileUtils.string(url)
                .chars()
                .map(cp -> Character.digit(cp, 10))
                .toArray();
        return getChecksum(compactFiles(parseData(input)));
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

    private static int[] compactFiles(int[] data) {
        int length = 0;
        for(int i = data.length - 1; i > 0; i--) {
            length++;
            if(data[i - 1] != data[i]) {
                if(data[i] != -1) {
                    moveFile(data, i, length);
                }
                length = 0;
            }
        }
        return data;
    }

    private static void moveFile(int[] data, int index, int length) {
        int file = data[index];
        int start = 0;
        int freeLength = 0;
        for(int i = 0; i < index; i++) {
            freeLength++;
            if(data[i + 1] != data[i]) {
                if(data[i] == -1 && length <= freeLength) {
                    for(int j = 0; j < length; j++) {
                        data[j + start] = file;
                        data[j + index] = -1;
                    }
                    return;
                }
                freeLength = 0;
                start = i + 1;
            }
        }
    }
}

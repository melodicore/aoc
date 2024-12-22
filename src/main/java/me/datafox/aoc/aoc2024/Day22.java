package me.datafox.aoc.aoc2024;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Advent of Code 2024 day 22 solutions.
 *
 * @author datafox
 */
public class Day22 {
    public static long solve1(URL url) {
        long[] numbers = FileUtils.linesAsStream(url)
                .mapToLong(Long::parseLong)
                .toArray();
        for(int i = 0; i < 2000; i++) {
            for(int j = 0; j < numbers.length; j++) {
                numbers[j] ^= numbers[j] * 64;
                numbers[j] %= 16777216;
                numbers[j] ^= numbers[j] / 32;
                numbers[j] %= 16777216;
                numbers[j] ^= numbers[j] * 2048;
                numbers[j] %= 16777216;
            }
        }
        return LongStream.of(numbers).sum();
    }

    public static long solve2(URL url) {
        long[] numbers = FileUtils.linesAsStream(url)
                .mapToLong(Long::parseLong)
                .toArray();
        int[] choices = IntStream.rangeClosed(-9, 9).toArray();
        int iters = 19 * 19 * 19 * 19;
        return IntStream.range(0, iters).parallel().map(i -> calculateBananas(i, Arrays.copyOf(numbers, numbers.length), choices)).max().orElse(-1);
    }

    public static int calculateBananas(int c, long[] numbers, int[] choices) {
        int[] current = new int[4];
        int t = c;
        for(int i = 0; i < 4; i++) {
            current[i] = choices[t % 19];
            t /= 19;
        }
        int[] bananas = new int[numbers.length];
        for(int j = 0; j < numbers.length; j++) {
            int last = (int) numbers[j] % 10;
            int[] check = new int[4];
            for(int i = 0; i < 2000; i++) {
                numbers[j] ^= numbers[j] * 64;
                numbers[j] %= 16777216;
                numbers[j] ^= numbers[j] / 32;
                numbers[j] %= 16777216;
                numbers[j] ^= numbers[j] * 2048;
                numbers[j] %= 16777216;
                assert numbers[j] < Integer.MAX_VALUE;
                int next = (int) numbers[j] % 10;
                if(i < 4) {
                    check[i] = next - last;
                    if(i == 3 && Arrays.equals(current, check)) {
                        bananas[j] = next;
                        break;
                    }
                } else {
                    for(int x = 0; x < check.length - 1; x++) {
                        check[x] = check[x + 1];
                    }
                    check[3] = next - last;
                    if(Arrays.equals(current, check)) {
                        bananas[j] = next;
                        break;
                    }
                }
                last = next;
            }
        }
        return IntStream.of(bananas).sum();
    }
}

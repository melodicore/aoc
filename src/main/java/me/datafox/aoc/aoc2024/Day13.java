package me.datafox.aoc.aoc2024;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.Arrays;

/**
 * Advent of Code 2024 day 11 solutions.
 *
 * @author datafox
 */
public class Day13 {
    public static int solve1(URL url) {
        return Arrays.stream(FileUtils.string(url).split("\n\n"))
                .map(Day13::parseMachine)
                .mapToInt(Machine::minPriceForPrize)
                .sum();
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static Machine parseMachine(String str) {
        String[] split = str.split("\n");
        assert split.length == 3;
        String ba = split[0].split("Button A: X\\+", 2)[1];
        String[] ap = ba.split(", Y\\+", 2);
        String bb = split[1].split("Button B: X\\+", 2)[1];
        String[] bp = bb.split(", Y\\+", 2);
        String p = split[2].split("Prize: X=", 2)[1];
        String[] pp = p.split(", Y=", 2);
        return new Machine(Integer.parseInt(ap[0]),
                Integer.parseInt(ap[1]),
                Integer.parseInt(bp[0]),
                Integer.parseInt(bp[1]),
                Integer.parseInt(pp[0]),
                Integer.parseInt(pp[1]));
    }

    private record Machine(int ac, int ax, int ay, int bc, int bx, int by, int px, int py) {
        public Machine(int ax, int ay, int bx, int by, int px, int py) {
            this(3, ax, ay, 1, bx, by, px, py);
        }

        public int minPriceForPrize() {
            int minPrice = Integer.MAX_VALUE;
            int maxA = maxA();
            for(int a = 0; a <= maxA; a++) {
                for(int b = 0; b <= maxBForA(a); b++) {
                    if(a * ax + b * bx == px && a * ay + b * by == py) {
                        minPrice = Math.min(minPrice, a * ac + b * bc);
                    }
                }
            }
            return minPrice == Integer.MAX_VALUE ? 0 : minPrice;
        }

        private int maxA() {
            return Math.min(px / ax, py / ay);
        }

        private int maxBForA(int a) {
            return Math.min((px - a * ax) / bx, (py - a * ay) / by);
        }
    }
}

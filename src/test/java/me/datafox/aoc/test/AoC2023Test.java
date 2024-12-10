package me.datafox.aoc.test;

import me.datafox.aoc.aoc2023.*;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * "Tests" for Advent of Code 2023 solutions. They just print the solution to console, asserting that nothing is thrown.
 *
 * @author datafox
 */
public class AoC2023Test {
    @Test
    public void day1part1() {
        System.out.println(assertDoesNotThrow(() -> Day1.solve1(res(1))));
    }

    @Test
    public void day1part2() {
        System.out.println(assertDoesNotThrow(() -> Day1.solve2(res(1))));
    }

    @Test
    public void day2part1() {
        System.out.println(assertDoesNotThrow(() -> Day2.solve1(res(2))));
    }

    @Test
    public void day2part2() {
        System.out.println(assertDoesNotThrow(() -> Day2.solve2(res(2))));
    }

    @Test
    public void day3part1() {
        System.out.println(assertDoesNotThrow(() -> Day3.solve1(res(3))));
    }

    @Test
    public void day3part2() {
        System.out.println(assertDoesNotThrow(() -> Day3.solve2(res(3))));
    }

    @Test
    public void day4part1() {
        System.out.println(assertDoesNotThrow(() -> Day4.solve1(res(4))));
    }

    @Test
    public void day4part2() {
        System.out.println(assertDoesNotThrow(() -> Day4.solve2(res(4))));
    }

    @Test
    public void day5part1() {
        System.out.println(assertDoesNotThrow(() -> Day5.solve1(res(5))));
    }

    @Test
    public void day5part2() {
        System.out.println(assertDoesNotThrow(() -> Day5.solve2(res(5))));
    }

    @Test
    public void day6part1() {
        System.out.println(assertDoesNotThrow(() -> Day6.solve1(res(6))));
    }

    @Test
    public void day6part2() {
        System.out.println(assertDoesNotThrow(() -> Day6.solve2(res(6))));
    }

    @Test
    public void day7part1() {
        System.out.println(assertDoesNotThrow(() -> Day7.solve1(res(7))));
    }

    @Test
    public void day7part2() {
        System.out.println(assertDoesNotThrow(() -> Day7.solve2(res(7))));
    }

    @Test
    public void day8part1() {
        System.out.println(assertDoesNotThrow(() -> Day8.solve1(res(8))));
    }

    @Test
    public void day8part2() {
        System.out.println(assertDoesNotThrow(() -> Day8.solve2(res(8))));
    }

    @Test
    public void day9part1() {
        System.out.println(assertDoesNotThrow(() -> Day9.solve1(res(9))));
    }

    @Test
    public void day9part2() {
        System.out.println(assertDoesNotThrow(() -> Day9.solve2(res(9))));
    }

    @Test
    public void day10part1() {
        System.out.println(assertDoesNotThrow(() -> Day10.solve1(res(10))));
    }

    @Test
    public void day10part2() {
        System.out.println(assertDoesNotThrow(() -> Day10.solve2(res(10))));
    }

    @Test
    public void day11part1() {
        System.out.println(assertDoesNotThrow(() -> Day11.solve1(res(11))));
    }

    @Test
    public void day11part2() {
        System.out.println(assertDoesNotThrow(() -> Day11.solve2(res(11))));
    }

    @Test
    public void day11part2efficient() {
        System.out.println(assertDoesNotThrow(() -> Day11.solve2efficient(res(11))));
    }

    private URL res(int day) {
        return getClass().getResource(String.format("/2023/day%s.txt", day));
    }
}

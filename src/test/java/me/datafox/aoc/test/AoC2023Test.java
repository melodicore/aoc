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

    @Test
    public void day12part1() {
        System.out.println(assertDoesNotThrow(() -> Day12.solve1(res(12))));
    }

    @Test
    public void day12part2() {
        System.out.println(assertDoesNotThrow(() -> Day12.solve2(res(12))));
    }

    @Test
    public void day13part1() {
        System.out.println(assertDoesNotThrow(() -> Day13.solve1(res(13))));
    }

    @Test
    public void day13part2() {
        System.out.println(assertDoesNotThrow(() -> Day13.solve2(res(13))));
    }

    @Test
    public void day14part1() {
        System.out.println(assertDoesNotThrow(() -> Day14.solve1(res(14))));
    }

    @Test
    public void day14part2() {
        System.out.println(assertDoesNotThrow(() -> Day14.solve2(res(14))));
    }

    @Test
    public void day15part1() {
        System.out.println(assertDoesNotThrow(() -> Day15.solve1(res(15))));
    }

    @Test
    public void day15part2() {
        System.out.println(assertDoesNotThrow(() -> Day15.solve2(res(15))));
    }

    @Test
    public void day15part2Cheeky() {
        System.out.println(assertDoesNotThrow(() -> Day15.solve2Cheeky(res(15))));
    }

    @Test
    public void day16part1() {
        System.out.println(assertDoesNotThrow(() -> Day16.solve1(res(16))));
    }

    @Test
    public void day16part2() {
        System.out.println(assertDoesNotThrow(() -> Day16.solve2(res(16))));
    }

    private URL res(int day) {
        return getClass().getResource(String.format("/2023/day%s.txt", day));
    }
}

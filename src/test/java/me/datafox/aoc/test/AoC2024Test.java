package me.datafox.aoc.test;

import org.junit.jupiter.api.Test;

/**
 * "Tests" for Advent of Code 2024 solutions. They just print the solution to console, asserting that nothing is thrown.
 *
 * @author datafox
 */
public class AoC2024Test {
    @Test
    public void day1part1() {
        test(1, "1");
    }

    @Test
    public void day1part2() {
        test(1, "2");
    }

    @Test
    public void day2part1() {
        test(4, "1");
    }

    @Test
    public void day2part2() {
        test(2, "2");
    }

    @Test
    public void day2part2efficient() {
        test(2, "2efficient");
    }

    @Test
    public void day3part1() {
        test(3, "1");
    }

    @Test
    public void day3part2() {
        test(3, "2");
    }

    @Test
    public void day4part1() {
        test(4, "1");
    }

    @Test
    public void day4part2() {
        test(4, "2");
    }

    @Test
    public void day5part1() {
        test(5, "1");
    }

    @Test
    public void day5part1ruleMap() {
        test(5, "1ruleMap");
    }

    @Test
    public void day5part2() {
        test(5, "2");
    }

    @Test
    public void day5part2ruleMap() {
        test(5, "2ruleMap");
    }

    @Test
    public void day6part1() {
        test(6, "1");
    }

    @Test
    public void day6part2() {
        test(6, "2");
    }

    @Test
    public void day7part1() {
        test(7, "1");
    }

    @Test
    public void day7part2() {
        test(7, "2");
    }

    @Test
    public void day8part1() {
        test(8, "1");
    }

    @Test
    public void day8part2() {
        test(8, "2");
    }

    @Test
    public void day9part1() {
        test(9, "1");
    }

    @Test
    public void day9part2() {
        test(9, "2");
    }

    @Test
    public void day10part1() {
        test(10, "1");
    }

    @Test
    public void day10part2() {
        test(10, "2");
    }

    @Test
    public void day11part1() {
        test(11, "1");
    }

    @Test
    public void day11part2() {
        test(11, "2");
    }

    @Test
    public void day12part1() {
        test(12, "1");
    }

    @Test
    public void day12part2() {
        test(12, "2");
    }

    @Test
    public void day13part1() {
        test(13, "1");
    }

    @Test
    public void day13part2() {
        test(13, "2");
    }

    @Test
    public void day14part1() {
        test(14, "1");
    }

    @Test
    public void day14part2() {
        test(14, "2");
    }

    @Test
    public void day15part1() {
        test(15, "1");
    }

    @Test
    public void day15part2() {
        test(15, "2");
    }

    @Test
    public void day16part1() {
        test(16, "1");
    }

    @Test
    public void day16part2() {
        test(16, "2");
    }

    @Test
    public void day17part1() {
        test(17, "1");
    }

    @Test
    public void day17part2() {
        test(17, "2");
    }

    @Test
    public void day18part1() {
        test(18, "1");
    }

    @Test
    public void day18part2() {
        test(18, "2");
    }

    @Test
    public void day19part1() {
        test(19, "1");
    }

    @Test
    public void day19part2() {
        test(19, "2");
    }

    @Test
    public void day20part1() {
        test(20, "1");
    }

    @Test
    public void day20part2() {
        test(20, "2");
    }

    @Test
    public void day21part1() {
        test(21, "1");
    }

    @Test
    public void day21part2() {
        test(21, "2");
    }

    @Test
    public void day22part1() {
        test(22, "1");
    }

    @Test
    public void day22part2() {
        test(22, "2");
    }

    @Test
    public void day23part1() {
        test(23, "1");
    }

    @Test
    public void day23part2() {
        test(23, "2");
    }

    private void test(int day, String part) {
        TestUtils.test(2024, day, part);
    }
}

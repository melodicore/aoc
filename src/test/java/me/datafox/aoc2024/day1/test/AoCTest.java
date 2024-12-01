package me.datafox.aoc2024.day1.test;

import me.datafox.aoc2024.Day1;
import org.junit.jupiter.api.Test;

/**
 * @author datafox
 */
public class AoCTest {
    @Test
    public void day1part1() {
        System.out.println(Day1.solve1(getClass().getResource("/day1.txt")));
    }

    @Test
    public void day1part2() {
        System.out.println(Day1.solve2(getClass().getResource("/day1.txt")));
    }
}

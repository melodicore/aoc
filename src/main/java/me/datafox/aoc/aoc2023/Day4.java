package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Advent of Code 2023 day 4 solutions.
 *
 * @author datafox
 */
public class Day4 {
    public static int solve1(URL url) {
        return FileUtils.linesAsStream(url)
                .map(s -> s.split(": +", 2)[1])
                .map(Day4::parseCard)
                .mapToInt(Card::calculateScore)
                .sum();
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static Card parseCard(String str) {
        String[] split = str.split(" +\\| +", 2);
        return new Card(
                Arrays.stream(split[0].split(" +"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toSet()),
                Arrays.stream(split[1].split(" +"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toSet()));
    }

    private record Card(Set<Integer> numbers, Set<Integer> results) {
        public int calculateScore() {
            int score = 0;
            for(int i : numbers) {
                if(results.contains(i)) {
                    if(score == 0) {
                        score = 1;
                    } else {
                        score *= 2;
                    }
                }
            }
            return score;
        }
    }
}

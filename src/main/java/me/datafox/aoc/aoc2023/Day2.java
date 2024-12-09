package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;

/**
 * Advent of Code 2023 day 2 solutions.
 *
 * @author datafox
 */
public class Day2 {
    public static int solve1(URL url) {
        return FileUtils.linesAsStream(url)
                .map(Day2::parseGame)
                .filter(Game::isPossible)
                .mapToInt(Game::id)
                .sum();
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static Game parseGame(String str) {
        assert str.startsWith("Game ");
        int id = Integer.parseInt(str.substring(5).split(":", 2)[0]);
        int red = 0;
        int green = 0;
        int blue = 0;
        for(String round : str.split(": |; ")) {
            if(round.startsWith("Game ")) {
                continue;
            }
            for(String dice : round.split(", ")) {
                if(dice.contains(" red")) {
                    red = Math.max(red, Integer.parseInt(dice.split(" red")[0]));
                }
                if(dice.contains(" green")) {
                    green = Math.max(green, Integer.parseInt(dice.split(" green")[0]));
                }
                if(dice.contains(" blue")) {
                    blue = Math.max(blue, Integer.parseInt(dice.split(" blue")[0]));
                }
            }
        }
        return new Game(id, red, green, blue);
    }

    private record Game(int id, int red, int green, int blue) {
        public boolean isPossible() {
            return red <= 12 && green <= 13 && blue <= 14;
        }
    }
}

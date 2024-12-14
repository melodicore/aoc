package me.datafox.aoc.aoc2024;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Advent of Code 2024 day 11 solutions.
 *
 * @author datafox
 */
public class Day14 {
    private static final int WIDTH = 101;
    private static final int HEIGHT = 103;

    public static int solve1(URL url) {
        Set<Robot> robots = FileUtils.linesAsStream(url)
                .map(Robot::parse)
                .map(r -> r.move(100))
                .map(r -> r.wrap(WIDTH, HEIGHT))
                .collect(Collectors.toSet());
        return splitQuadrants(robots, WIDTH, HEIGHT).stream().mapToInt(Set::size).reduce(1, (i, j) -> i * j);
    }

    private static List<Set<Robot>> splitQuadrants(Set<Robot> robots, int width, int height) {
        Set<Robot> ul = new HashSet<>();
        Set<Robot> ur = new HashSet<>();
        Set<Robot> dl = new HashSet<>();
        Set<Robot> dr = new HashSet<>();
        int hw = width / 2;
        int hh = height / 2;
        boolean ow = width % 2 == 1;
        boolean oh = height % 2 == 1;
        robots.forEach(r -> {
            Coordinate c = r.coord();
            if(c.x() < hw) {
                if(c.y() < hh) {
                    ur.add(r);
                } else if(!oh || c.y() > hh) {
                    ul.add(r);
                }
            } else if(!ow || c.x() > hw) {
                if(c.y() < hh) {
                    dr.add(r);
                } else if(!ow || c.y() > hh) {
                    dl.add(r);
                }
            }
        });
        return List.of(ul, ur, dl, dr);
    }

    public static int solve2(URL url) {
        return 0;
    }

    private record Robot(Coordinate coord, Coordinate speed) {
        public static Robot parse(String str) {
            String[] split = str.substring(2).split(",| v=", 4);
            assert split.length == 4;
            return new Robot(new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1])),
                    new Coordinate(Integer.parseInt(split[2]), Integer.parseInt(split[3])));
        }

        public Robot move(int steps) {
            return new Robot(coord.move(speed.multiply(steps)), speed);
        }

        public Robot wrap(int width, int height) {
            return new Robot(coord.modulo(width, height), speed);
        }
    }
}
